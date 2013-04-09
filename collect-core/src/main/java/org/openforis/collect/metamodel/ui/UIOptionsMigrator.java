package org.openforis.collect.metamodel.ui;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.openforis.collect.metamodel.ui.UIOptions.Layout;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * 
 * @author S. Ricci
 *
 */
@SuppressWarnings("deprecation")
public class UIOptionsMigrator {
	
	public UIOptions migrate(UIOptions oldUIOptions) {
		CollectSurvey survey = oldUIOptions.getSurvey();
		UIOptions result = new UIOptions(survey);
		List<UITabSet> tabSets = oldUIOptions.getTabSets();
		for (UITabSet tabSet : tabSets) {
			FormSet formSet;
			EntityDefinition associatedRootEntity = findAssociatedRootEntity(tabSet);
			if ( associatedRootEntity != null ) {
				formSet = result.createFormSet();
				formSet.setEntityId(associatedRootEntity.getId());
			} else {
				throw new IllegalStateException("Cannot find associated root entity. Tab set: " + tabSet.getName());
			}
			List<UITab> tabs = tabSet.getTabs();
			for (UITab tab : tabs) {
				Form form = formSet.createForm();
				FormSection mainFormSection = form.createFormSection();
				List<NodeDefinition> nodes = oldUIOptions.getNodesPerTab(tab, false);
				for (NodeDefinition nodeDefn : nodes) {
					if ( nodeDefn instanceof AttributeDefinition ) {
						Field field = mainFormSection.createField();
						mainFormSection.addComponent(field);
					} else if ( nodeDefn instanceof EntityDefinition ) {
						EntityDefinition entityDefn = (EntityDefinition) nodeDefn;
						if ( nodeDefn.isMultiple() ) {
							if ( oldUIOptions.getLayout(entityDefn) == Layout.TABLE ) {
								Table table = createTable(mainFormSection, entityDefn);
								mainFormSection.addComponent(table);
							}
						} else {
							createFormSection(form, entityDefn);
						}
					}
				}
			}
		}
		return result;
	}

	protected FormSection createFormSection(Form form, EntityDefinition entityDefn) {
		FormSection formSection = form.createFormSection();
		List<NodeDefinition> childDefns = entityDefn.getChildDefinitions();
		for (NodeDefinition childDefn : childDefns) {
			Component component;
			if ( childDefn instanceof AttributeDefinition ) {
				component = formSection.createField();
				((Field) component).setAttributeId(childDefn.getId());
			} else if ( childDefn instanceof EntityDefinition ) {
				EntityDefinition childEntityDefn = (EntityDefinition) childDefn;
				if ( childDefn.isMultiple() ) {
					component = createTable(formSection, childEntityDefn);
				} else {
					throw new IllegalStateException("Nested form sections are not supported");
				}
				formSection.addComponent(component);
			}
		}
		return formSection;
	}

	protected Table createTable(FormSection mainFormSection, EntityDefinition entityDefn) {
		Table table = mainFormSection.createTable();
		List<NodeDefinition> childDefinitions = entityDefn.getChildDefinitions();
		for (NodeDefinition childDefn : childDefinitions) {
			TableHeadingComponent component = createTableHeadingComponent(table, childDefn);
			table.addHeadingComponent(component);
		}
		return table;
	}
	
	protected TableHeadingComponent createTableHeadingComponent(Table table, NodeDefinition childDefn) {
		TableHeadingComponent component;
		if ( childDefn instanceof EntityDefinition ) {
			if ( childDefn.isMultiple() ) {
				throw new IllegalStateException("Nested multiple entity inside table layout entity is not supported");
			}
			component = table.createColumnGroup();
			List<NodeDefinition> innerChildDefns = ((EntityDefinition) childDefn).getChildDefinitions();
			for (NodeDefinition innerChildDefn : innerChildDefns) {
				TableHeadingComponent innerComponent = createTableHeadingComponent(table, innerChildDefn);
				((ColumnGroup) component).addHeadingComponent(innerComponent);
			}
			//TODO copy labels from EntityDefinition to ColumnGroup
		} else {
			component = table.createColumn();
			((Column) component).setAttributeId(childDefn.getId());
		}
		return component;
	}

	protected EntityDefinition findAssociatedRootEntity(UITabSet tabSet) {
		UIOptions uiOptions = tabSet.getUIOptions();
		CollectSurvey survey = uiOptions.getSurvey();
		Schema schema = survey.getSchema();
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition rootEntityDefn : rootEntityDefinitions) {
			UITabSet assignedRootTabSet = uiOptions.getAssignedRootTabSet(rootEntityDefn);
			if ( ObjectUtils.equals(assignedRootTabSet, tabSet) ) {
				return rootEntityDefn;
			}
		}
		return null;
	}
	
}

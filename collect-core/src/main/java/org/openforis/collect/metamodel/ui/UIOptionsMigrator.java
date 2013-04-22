package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.openforis.collect.metamodel.ui.UIOptions.Layout;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel;
import org.openforis.idm.metamodel.NodeLabel.Type;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.TextAttributeDefinition;

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
				result.addFormSet(formSet);
			} else {
				throw new IllegalStateException("Cannot find associated root entity. Tab set: " + tabSet.getName());
			}
			List<UITab> tabs = tabSet.getTabs();
			for (UITab tab : tabs) {
				createForm(formSet, tab);
			}
		}
		return result;
	}

	protected void createForm(FormContainer parent, UITab tab) {
		UIOptions oldUIOptions = tab.getUIOptions();
		Form form = parent.createForm();
		EntityDefinition tabAssignedEntityDefn = findAssignedEntityDefinition(tab);
		if ( tabAssignedEntityDefn != null && 
				oldUIOptions.getLayout(tabAssignedEntityDefn) == Layout.FORM) {
			form.setEntityId(tabAssignedEntityDefn.getId());
			form.setMultiple(tabAssignedEntityDefn.isMultiple());
		} else {
			form.setEntityId(parent.getEntityId());
		}
		copyLabels(tab, form);
		createMainFormSection(tab, form);
		createInnerForms(tab, form);
		parent.addForm(form);
	}

	protected void createMainFormSection(UITab tab, Form form) {
		UIOptions oldUIOptions = tab.getUIOptions();
		List<NodeDefinition> nodes = oldUIOptions.getNodesPerTab(tab, false);
		createMainFormSection(form, oldUIOptions, nodes);
	}

	protected void createMainFormSection(Form form, UIOptions oldUIOptions,
			List<NodeDefinition> nodes) {
		FormSection mainFormSection = form.createFormSection();
		for (NodeDefinition nodeDefn : nodes) {
			createFormSectionComponent(oldUIOptions, mainFormSection, nodeDefn);
		}
		form.addFormSection(mainFormSection);
	}

	protected void createInnerForms(UITab tab, Form parent) {
//		UIOptions oldUIOptions = tab.getUIOptions();
//		List<NodeDefinition> nodesPerTab = oldUIOptions.getNodesPerTab(tab, false);
//		for (NodeDefinition nodeDefn : nodesPerTab) {
//			if ( nodeDefn instanceof EntityDefinition && nodeDefn.isMultiple() && 
//					oldUIOptions.getLayout((EntityDefinition) nodeDefn) == Layout.FORM ) {
//				UITab assignedTab = oldUIOptions.getAssignedTab(nodeDefn);
//				if ( assignedTab == tab ) {
//					createFormFromFormEntity(assignedTab, parent, (EntityDefinition) nodeDefn);
//				}
//			}
//		}
		List<UITab> innerTabs = tab.getTabs();
		for (UITab innerTab : innerTabs) {
			createForm(parent, innerTab);
		}
	}

//	protected void createFormFromFormEntity(UITab parentTab, Form parent, EntityDefinition entityDefn) {
//		Form form = parent.createForm();
//		copyLabels(entityDefn, form);
//		form.setEntityId(entityDefn.getId());
//		form.setMultiple(true);
//		createMainFormSection(form, parentTab.getUIOptions(), entityDefn.getChildDefinitions());
//		createInnerForms(parentTab, form);
//		parent.addForm(form);
//	}

	protected EntityDefinition findAssignedEntityDefinition(UITab tab) {
		UIOptions oldUIOptions = tab.getUIOptions();
		List<NodeDefinition> nodesPerTab = oldUIOptions.getNodesPerTab(tab, false);
		if ( nodesPerTab.size() == 1 ) {
			NodeDefinition firstNode = nodesPerTab.get(0);
			if ( firstNode instanceof EntityDefinition ) {
				return (EntityDefinition) firstNode;
			}
		}
		return null;
	}

	protected void createFormSectionComponent(UIOptions oldUIOptions,
			FormSection parent, NodeDefinition nodeDefn) {
		if ( nodeDefn instanceof AttributeDefinition ) {
			Field field = createField(parent, nodeDefn);
			parent.addChild(field);
		} else if ( nodeDefn instanceof EntityDefinition ) {
			EntityDefinition entityDefn = (EntityDefinition) nodeDefn;
			if ( entityDefn.isMultiple() ) {
				if ( oldUIOptions.getLayout(entityDefn) == Layout.TABLE ) {
					Table table = createTable(parent, entityDefn);
					parent.addChild(table);
				}
			} else {
				FormSection formSection = createFormSectionBySingleEntity(parent, entityDefn);
				parent.addChild(formSection);
			}
		}
	}

	protected Field createField(FormSection parent, NodeDefinition nodeDefn) {
		Field field = parent.createField();
		field.setAttributeId(nodeDefn.getId());
		if ( nodeDefn instanceof TextAttributeDefinition ) {
			UIOptions uiOptions = parent.getUIOptions();
			String autoCompleteGroup = uiOptions.getAutoCompleteGroup((TextAttributeDefinition) nodeDefn);
			field.setAutoCompleteGroup(autoCompleteGroup);
		}
		return field;
	}

	protected FormSection createFormSectionBySingleEntity(FormSectionContainer parent, EntityDefinition entityDefn) {
		FormSection formSection = parent.createFormSection();
		copyLabels(entityDefn, formSection);
		List<NodeDefinition> childDefns = entityDefn.getChildDefinitions();
		for (NodeDefinition childDefn : childDefns) {
			FormSectionComponent childComponent;
			if ( childDefn instanceof AttributeDefinition ) {
				childComponent = createField(formSection, childDefn);
			} else if ( childDefn instanceof EntityDefinition ) {
				EntityDefinition childEntityDefn = (EntityDefinition) childDefn;
				if ( childDefn.isMultiple() ) {
					childComponent = createTable(formSection, childEntityDefn);
				} else {
					childComponent = createFormSectionBySingleEntity(formSection, childEntityDefn);
				}
			} else {
				throw new IllegalArgumentException("Unsupported child definition class: " + childDefn.getClass().getSimpleName());
			}
			formSection.addChild(childComponent);
		}
		return formSection;
	}

	protected Table createTable(FormSection parent, EntityDefinition entityDefn) {
		Table table = parent.createTable();
		table.setEntityId(entityDefn.getId());
		UIOptions uiOptions = parent.getUIOptions();
		table.setCountInSummaryList(uiOptions.getCountInSumamryListValue(entityDefn));
		table.setDirection(uiOptions.getDirection(entityDefn));
		table.setShowRowNumbers(uiOptions.getShowRowNumbersValue(entityDefn));
		copyLabels(entityDefn, table);
		List<NodeDefinition> childDefinitions = entityDefn.getChildDefinitions();
		for (NodeDefinition childDefn : childDefinitions) {
			TableHeadingComponent component = createTableHeadingComponent(table, childDefn);
			table.addHeadingComponent(component);
		}
		return table;
	}
	
	protected TableHeadingComponent createTableHeadingComponent(Table table, NodeDefinition nodeDefn) {
		TableHeadingComponent component;
		if ( nodeDefn instanceof EntityDefinition ) {
			if ( nodeDefn.isMultiple() ) {
				throw new IllegalStateException("Nested multiple entity inside table layout entity is not supported: " + nodeDefn.getPath());
			}
			component = table.createColumnGroup();
			EntityDefinition entityDefn = (EntityDefinition) nodeDefn;
			copyLabels(entityDefn, (ColumnGroup) component);
			List<NodeDefinition> innerChildDefns = entityDefn.getChildDefinitions();
			for (NodeDefinition innerChildDefn : innerChildDefns) {
				TableHeadingComponent innerComponent = createTableHeadingComponent(table, innerChildDefn);
				((ColumnGroup) component).addHeadingComponent(innerComponent);
			}
		} else {
			component = table.createColumn();
			((Column) component).setAttributeId(nodeDefn.getId());
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

	protected void copyLabels(UITab tab, Form form) {
		List<LanguageSpecificText> labels = tab.getLabels();
		for (LanguageSpecificText lst : labels) {
			form.setLabel(lst.getLanguage(), lst.getText());
		}
	}

	protected void copyLabels(EntityDefinition entityDefn, Form form) {
		List<NodeLabel> labels = getLabelsByType(entityDefn, Type.HEADING);
		for (LanguageSpecificText lst : labels) {
			form.setLabel(lst.getLanguage(), lst.getText());
		}
	}

	protected void copyLabels(EntityDefinition entityDefn, FormSection formSection) {
		List<NodeLabel> labels = getLabelsByType(entityDefn, Type.HEADING);
		for (NodeLabel label : labels) {
			formSection.setLabel(label.getLanguage(), label.getText());
		}
		if ( formSection.getLabels().isEmpty() ) {
			labels = getLabelsByType(entityDefn, Type.INSTANCE);
			for (NodeLabel label : labels) {
				formSection.setLabel(label.getLanguage(), label.getText());
			}
		}
	}

	protected void copyLabels(EntityDefinition entityDefn, Table table) {
		List<NodeLabel> labels = entityDefn.getLabels();
		for (NodeLabel label : labels) {
			if ( label.getType() == NodeLabel.Type.HEADING ) {
				table.setLabel(label.getLanguage(), label.getText());
			}
		}
	}

	protected void copyLabels(EntityDefinition entityDefn, ColumnGroup columnGroup) {
		List<NodeLabel> labels = getLabelsByType(entityDefn, Type.HEADING);
		for (NodeLabel label : labels) {
			columnGroup.setLabel(label.getLanguage(), label.getText());
		}
		if ( columnGroup.getLabels().isEmpty() ) {
			labels = getLabelsByType(entityDefn, Type.INSTANCE);
			for (NodeLabel label : labels) {
				columnGroup.setLabel(label.getLanguage(), label.getText());
			}
		}
	}
	
	protected List<NodeLabel> getLabelsByType(NodeDefinition nodeDefn, NodeLabel.Type type) {
		List<NodeLabel> result = new ArrayList<NodeLabel>();
		List<NodeLabel> labels = nodeDefn.getLabels();
		for (NodeLabel label : labels) {
			if ( label.getType() == type ) {
				result.add(label);
			}
		}
		return result;
	}

}

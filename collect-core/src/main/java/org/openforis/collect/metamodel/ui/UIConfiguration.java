package org.openforis.collect.metamodel.ui;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.UI_CONFIGURATION_TYPE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.openforis.collect.model.CollectSurvey;
import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.ApplicationOptions;

/**
 * 
 * @author S. Ricci
 *
 */
public class UIConfiguration implements ApplicationOptions, Serializable {

	private static final long serialVersionUID = 1L;
	
	private CollectSurvey survey;
	private List<FormSet> formSets;
	private Map<Integer, UIModelObject> modelObjectsById;
	private int lastId;
	
	public UIConfiguration() {
		this.modelObjectsById = new HashMap<Integer, UIModelObject>();
	}
	
	public UIConfiguration(CollectSurvey survey) {
		this();
		this.survey = survey;
	}
	
	@Override
	public String getType() {
		return UI_CONFIGURATION_TYPE;
	}
	
	public CollectSurvey getSurvey() {
		return survey;
	}

	public void setSurvey(CollectSurvey survey) {
		this.survey = survey;
	}
	
	synchronized 
	protected int nextId() {
		return ++lastId;
	}
	
	public FormSet createFormSet() {
		return createFormSet(nextId());
	}
	
	public FormSet createFormSet(int id) {
		return new FormSet(this, id);
	}

	public List<FormSet> getFormSets() {
		return CollectionUtils.unmodifiableList(formSets);
	}
	
	public FormSet getFormSetByRootEntityId(int entityId) {
		if ( formSets != null ) {
			for (FormSet formSet : formSets) {
				if ( formSet.getEntityId() == entityId ) {
					return formSet;
				}
			}
		}
		return null;
	}
	
	public void addFormSet(FormSet formSet) {
		if ( formSets == null ) {
			formSets = new ArrayList<FormSet>();
		}
		formSets.add(formSet);
		attachItem(formSet);
	}
	
	public void removeFormSet(FormSet formSet) {
		formSets.remove(formSet);
		detachItem(formSet);
	}
	
	public UIModelObject findModelObjectById(int id) {
		return modelObjectsById.get(id);
	}
	
	// Pre-order depth-first traversal from here down
	public void traverse(UIModelObjectVisitor visitor) {
		Stack<UIModelObject> stack = new Stack<UIModelObject>();
		
		// Initialize stack with form sets
		stack.addAll(getFormSets());

		while ( ! stack.isEmpty() && ! visitor.isStopped() ) {
			UIModelObject obj = stack.pop();
			
			// Pre-order operation
			visitor.visit(obj);
			
			if ( obj instanceof Table ) {
				List<TableHeadingComponent> headingComponents = ((Table) obj).getHeadingComponents();
				for (TableHeadingComponent tableHeadingComponent : headingComponents) {
					stack.push(tableHeadingComponent);
				}
//			} else if ( obj instanceof TableHeadingComponent ) {
			} else if ( obj instanceof Form ) {
				List<FormSection> formSections = ((Form) obj).getFormSections();
				for (FormSection formSection : formSections) {
					stack.push(formSection);
				}
			} else if ( obj instanceof FormSection ) {
				List<FormSectionComponent> children = ((FormSection) obj).getChildren();
				for (FormSectionComponent child : children) {
					stack.push((UIModelObject) child);
				}
			} else if ( obj instanceof FormContainer ) {
				List<Form> forms = ((FormContainer) obj).getForms();
				for (Form form : forms) {
					stack.push(form);
				}
			}

		}		
	}
	
	public UIModelObject findModelObjetByNodeDefinitionId(final int id) {
		UIModelObjectVisitor visitor = new UIModelObjectVisitor() {
			@Override
			public void visit(UIModelObject object) {
				if ( object instanceof Field ) {
					if ( ((Field) object).getAttributeId() == id ) {
						setLastItem(object);
						this.stop();
					}
				}
			}
		};
		traverse(visitor);
		
		if ( visitor.isStopped() ) {
			return visitor.getLastItem();
		} else {
			return null;
		}
	}
	
	public void attachItem(UIModelObject item) {
		modelObjectsById.put(item.getId(), item);
	}
	
	public void detachItem(UIModelObject item) {
		modelObjectsById.remove(item.getId());
	}
	
	public abstract class UIModelObjectVisitor {
		
		private boolean stopped;
		private UIModelObject lastItem = null;
		
		public UIModelObjectVisitor() {
			this.stopped = false;
		}
		
		public abstract void visit(UIModelObject object);

		void stop() {
			this.stopped = true;
		}

		public boolean isStopped() {
			return stopped;
		}
		
		public UIModelObject getLastItem() {
			return lastItem;
		}
		
		void setLastItem(UIModelObject lastItem) {
			this.lastItem = lastItem;
		}
		
	}
}

package org.openforis.collect.metamodel.ui;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.UI_CONFIGURATION_TYPE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void attachItem(UIModelObject item) {
		modelObjectsById.put(item.getId(), item);
	}
	
	public void detachItem(UIModelObject item) {
		modelObjectsById.remove(item.getId());
	}
}

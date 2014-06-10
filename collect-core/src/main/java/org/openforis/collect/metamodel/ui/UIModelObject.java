package org.openforis.collect.metamodel.ui;

import java.io.Serializable;

import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * 
 * @author S. Ricci
 *
 */
public abstract class UIModelObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UIFormSets uiOptions;
	private int id;

	UIModelObject(UIFormSets uiOptions, int id) {
		super();
		this.uiOptions = uiOptions;
		this.id = id;
	}
	
	protected NodeDefinition getNodeDefinition(int id) {
		UIFormSets uiOptions = getUIOptions();
		if ( uiOptions == null || uiOptions.getSurvey() == null ) {
			throw new IllegalStateException("UIOptions not initialized correctly");
		}
		CollectSurvey survey = uiOptions.getSurvey();
		Schema schema = survey.getSchema();
		NodeDefinition result = schema.getDefinitionById(id);
		return result;
	}
	
	public UIFormSets getUIOptions() {
		return uiOptions;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UIModelObject other = (UIModelObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

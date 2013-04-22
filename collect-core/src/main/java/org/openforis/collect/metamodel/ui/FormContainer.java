package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.LanguageSpecificTextMap;

/**
 * 
 * @author S. Ricci
 *
 */
public abstract class FormContainer extends UIModelObject {

	private static final long serialVersionUID = 1L;
	
	private int entityId;
	private List<Form> forms;
	private LanguageSpecificTextMap labels;

	FormContainer(UIOptions uiOptions, int id) {
		super(uiOptions, id);
	}

	public Form createForm() {
		UIOptions uiOptions = getUIOptions();
		return createForm(uiOptions.nextId());
	}
	
	public Form createForm(int id) {
		return new Form(this, id);
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public EntityDefinition getEntity() {
		return (EntityDefinition) getNodeDefinition(entityId);
	}

	public List<Form> getForms() {
		return CollectionUtils.unmodifiableList(forms);
	}
	
	public void addForm(Form form) {
		if ( forms == null ) {
			forms = new ArrayList<Form>();
		}
		forms.add(form);
	}
	
	public void removeForm(Form form) {
		forms.remove(form);
	}
	
	public List<LanguageSpecificText> getLabels() {
		if ( labels == null ) {
			return Collections.emptyList();
		} else {
			return labels.values();
		}
	}
	
	public String getLabel(String language) {
		return labels == null ? null: labels.getText(language);
	}
	
	public void addLabel(LanguageSpecificText label) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.add(label);
	}

	public void setLabel(String language, String text) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.setText(language, text);
	}
	
	public void removeLabel(String language) {
		labels.remove(language);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + entityId;
		result = prime * result + ((forms == null) ? 0 : forms.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormContainer other = (FormContainer) obj;
		if (entityId != other.entityId)
			return false;
		if (forms == null) {
			if (other.forms != null)
				return false;
		} else if (!forms.equals(other.forms))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Entity: " + getEntity().getPath();
	}
	
}

/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.LanguageSpecificTextMap;

/**
 * @author S. Ricci
 *
 */
public class FormSection extends UIModelObject implements FormSectionComponent, FormSectionContainer {

	private static final long serialVersionUID = 1L;

	protected FormSectionContainer parent;
	private List<FormSectionComponent> children;
	private LanguageSpecificTextMap labels;

	public FormSection(FormSectionContainer parent, int id) {
		super(parent.getUIOptions(), id);
		this.parent = parent;
	}

	@Override
	public void addFormSection(FormSection formSection) {
		addChild(formSection);
		getUIOptions().attachItem(formSection);
	}
	
	public FormSection createFormSection() {
		UIConfiguration uiOptions = getUIOptions();
		return createFormSection(uiOptions.nextId());
	}
	
	public FormSection createFormSection(int id) {
		return new FormSection(this, id);
	}
	
	public Field createField() {
		UIConfiguration uiOptions = getUIOptions();
		return createField(uiOptions.nextId());
	}
	
	public Field createField(int id) {
		return new Field(this, id);
	}

	public Table createTable() {
		UIConfiguration uiOptions = getUIOptions();
		return createTable(uiOptions.nextId());
	}
	
	public Table createTable(int id) {
		return new Table(this, id);
	}
	
	public List<FormSectionComponent> getChildren() {
		return CollectionUtils.unmodifiableList(children);
	}
	
	public void addChild(FormSectionComponent child) {
		if ( children == null ) {
			children = new ArrayList<FormSectionComponent>();
		}
		children.add(child);
		getUIOptions().attachItem((UIModelObject) child);
	}
	
	public void removeChild(FormSectionComponent child) {
		children.remove(child);
		getUIOptions().detachItem((UIModelObject) child);
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
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
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
		FormSection other = (FormSection) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		return true;
	}


}

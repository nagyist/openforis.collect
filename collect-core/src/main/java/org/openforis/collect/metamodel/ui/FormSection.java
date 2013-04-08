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
public class FormSection extends UIModelObject {

	private static final long serialVersionUID = 1L;

	private Form form;
	private List<Component> components;
	private LanguageSpecificTextMap labels;

	public FormSection(Form form, int id) {
		super(form.getUiOptions(), id);
		this.form = form;
	}

	public Form getForm() {
		return form;
	}

	public Field createField() {
		UIOptions uiOptions = getUiOptions();
		return createField(uiOptions.nextId());
	}
	
	public Field createField(int id) {
		return new Field(this, id);
	}

	public Table createTable() {
		UIOptions uiOptions = getUiOptions();
		return createTable(uiOptions.nextId());
	}
	
	public Table createTable(int id) {
		return new Table(this, id);
	}
	
	public List<Component> getComponents() {
		return CollectionUtils.unmodifiableList(components);
	}
	
	public void addComponent(Component component) {
		if ( components == null ) {
			components = new ArrayList<Component>();
		}
		components.add(component);
	}
	
	public void removeComponent(Component component) {
		components.remove(component);
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
				+ ((components == null) ? 0 : components.hashCode());
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
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		return true;
	}
	
}

/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;

/**
 * @author S. Ricci
 *
 */
public class Form extends FormContainer implements FormSectionContainer {

	private static final long serialVersionUID = 1L;

	private FormContainer parent;
	private List<FormSection> formSections;
	private boolean multiple;

	public Form(FormContainer parent, int id) {
		super(parent.getUIOptions(), id);
		this.parent = parent;
		this.multiple = false;
	}
	
	public FormSection createFormSection() {
		UIOptions uiOptions = getUIOptions();
		return createFormSection(uiOptions.nextId());
	}
	
	public FormSection createFormSection(int id) {
		return new FormSection(this, id);
	}

	public FormContainer getParent() {
		return parent;
	}
	
	public List<FormSection> getFormSections() {
		return CollectionUtils.unmodifiableList(formSections);
	}
	
	public void addFormSection(FormSection formSection) {
		if ( formSections == null ) {
			formSections = new ArrayList<FormSection>();
		}
		formSections.add(formSection);
	}
	
	public void removeFormSection(FormSection formSection) {
		formSections.remove(formSection);
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((formSections == null) ? 0 : formSections.hashCode());
		result = prime * result + (multiple ? 1231 : 1237);
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
		Form other = (Form) obj;
		if (formSections == null) {
			if (other.formSections != null)
				return false;
		} else if (!formSections.equals(other.formSections))
			return false;
		if (multiple != other.multiple)
			return false;
		return true;
	}

}

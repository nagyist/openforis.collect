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
public class Form extends FormContainer implements FormSectionsContainer {

	private static final long serialVersionUID = 1L;

	private FormContainer parent;
	private List<FormSection> formSections;

	public Form(FormContainer parent, int id) {
		super(parent.getUiOptions(), id);
		this.parent = parent;
	}
	
	public FormSection createFormSection() {
		UIOptions uiOptions = getUiOptions();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((formSections == null) ? 0 : formSections.hashCode());
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
		return true;
	}

}

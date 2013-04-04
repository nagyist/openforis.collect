/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S. Ricci
 *
 */
public class Form extends FormContainer {

	private static final long serialVersionUID = 1L;

	private FormContainer parent;
	private List<FormSection> formSections;
	
	public Form(FormContainer parent) {
		super();
		this.parent = parent;
	}
	
	public FormSection createFormSection() {
		return new FormSection(this);
	}

	public FormContainer getParent() {
		return parent;
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
	
}

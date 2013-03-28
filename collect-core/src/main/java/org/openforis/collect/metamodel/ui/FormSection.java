/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.io.Serializable;

/**
 * @author S. Ricci
 *
 */
public class FormSection implements Serializable {

	private static final long serialVersionUID = 1L;

	private Form form;

	public FormSection(Form form) {
		super();
		this.form = form;
	}

	public Form getForm() {
		return form;
	}

	public Field createField() {
		return new Field(this);
	}
	
}

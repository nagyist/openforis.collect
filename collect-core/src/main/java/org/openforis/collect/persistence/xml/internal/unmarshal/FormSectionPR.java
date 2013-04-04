package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_SECTION;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FormSectionPR extends UIModelPR {
	
	private Form parentForm;
	private FormSection formSection;
	
	public FormSectionPR() {
		super(FORM_SECTION);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		formSection = parentForm.createFormSection();
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parentForm.addFormSection(formSection);
	}
	
	public Form getParentForm() {
		return parentForm;
	}
	
	public void setParentForm(Form parentForm) {
		this.parentForm = parentForm;
	}
	
}

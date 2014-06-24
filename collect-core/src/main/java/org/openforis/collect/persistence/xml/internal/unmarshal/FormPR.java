/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.FORM;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.LABEL;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormContainer;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author S. Ricci
 *
 */
public class FormPR extends UIModelPR {
	
	protected FormContainer parentFormContainer;
	protected Form form;

	public FormPR() {
		super(FORM);
		
		addChildPullReaders(
			new LabelPR(),
			new FormSectionPR(),
			this
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		form = parentFormContainer.createForm(id);
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		form.setEntityId(entityId);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parentFormContainer.addForm(form);
	}
	
	@Override
	protected final void handleChildTag(XmlPullReader childPR)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childPR instanceof FormPR ) {
			FormPR formpr = (FormPR) childPR;
			// Store child state in case reused recursively
			FormContainer tmpParent = formpr.parentFormContainer;
			Form tmpForm = formpr.form;
			formpr.parentFormContainer = this.form;
			super.handleChildTag(childPR);
			formpr.parentFormContainer = tmpParent;
			formpr.form = tmpForm;
		} else if ( childPR instanceof FormSectionPR ) {
			((FormSectionPR) childPR).parent = form;
			super.handleChildTag(childPR);
		} else {
			super.handleChildTag(childPR);
		}
	}
	
	public Form getForm() {
		return form;
	}

	private class LabelPR extends LanguageSpecificTextPR {

		public LabelPR() {
			super(LABEL);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			((Form) form).addLabel(lst);
		}
	}
}


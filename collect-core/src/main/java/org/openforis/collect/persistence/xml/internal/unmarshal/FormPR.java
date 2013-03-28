/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ENTITY_ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormContainer;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
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

	public FormPR(UIOptionsBinder binder) {
		super(FORM);
		
		addChildPullReaders(
			new FormSectionPR(binder)
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		form = parentFormContainer.createForm();
		
		Integer entityId = getIntegerAttribute(ENTITY_ID, true);
		
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
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
		} else {
			super.handleChildTag(childPR);
		}
	}
	
	public Form getForm() {
		return form;
	}

}


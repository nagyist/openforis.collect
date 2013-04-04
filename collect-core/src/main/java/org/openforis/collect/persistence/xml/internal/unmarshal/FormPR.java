/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormContainer;
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
			new FormSectionPR()
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		form = parentFormContainer.createForm();
		int entityDefinitionId = getIntegerAttribute(ENTITY_ID, true);
		form.setEntityDefinitionId(entityDefinitionId);
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
		} else {
			super.handleChildTag(childPR);
		}
	}
	
	public Form getForm() {
		return form;
	}

}


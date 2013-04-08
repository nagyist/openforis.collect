/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_BUNDLE;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormBundle;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author S. Ricci
 *
 */
public class FormBundlePR extends UIModelPR {
	
	protected FormBundle formBundle;

	public FormBundlePR() {
		super(FORM_BUNDLE);
		
		addChildPullReaders(
			new FormPR()
		);
	}
	
	@Override
	public synchronized void parse(XmlPullParser parser)
			throws XmlParseException, IOException {
		super.parse(parser);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		UIOptions uiOptions = getUIOptions();
		int id = getIntegerAttribute(ID, true);
		formBundle = uiOptions.createFormBundle(id);
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		formBundle.setEntityId(entityId);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		FormBundlesPR rootReader = getRootReader();
		rootReader.addFormBundle(formBundle);
	}
	
	@Override
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof FormPR ) {
			((FormPR) childTagReader).parentFormContainer = formBundle;
		}
		super.handleChildTag(childTagReader);
		
	}
	
	public FormBundle getFormBundle() {
		return formBundle;
	}

}


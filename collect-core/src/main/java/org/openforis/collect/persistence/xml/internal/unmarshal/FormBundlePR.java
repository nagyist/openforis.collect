/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_BUNDLE;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormBundle;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author S. Ricci
 *
 */
public class FormBundlePR extends UIModelPR {
	
	protected FormBundle formBundle;

	public FormBundlePR(UIOptionsBinder binder) {
		super(FORM_BUNDLE);
		
		addChildPullReaders(
			new FormPR(binder)
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
		formBundle = uiOptions.createFormBundle();
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		UIOptions uiOptions = getUIOptions();
		uiOptions.addFormBundle(formBundle);
	}
	
	public FormBundle getFormBundle() {
		return formBundle;
	}

}


/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_BUNDLES;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.metamodel.ui.FormBundle;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author S. Ricci
 *
 */
public class FormBundlesPR extends UIModelPR {
	
	private List<FormBundle> formBundles;
	
	public FormBundlesPR(UIOptionsBinder binder) {
		super(FORM_BUNDLES);
		
		addChildPullReaders(
			new FormBundlePR()
		);
		
		setUIOptionsBinder(binder);
	}
	
	@Override
	public synchronized void parse(XmlPullParser parser)
			throws XmlParseException, IOException {
		super.parse(parser);
	}
	
	protected void addFormBundle(FormBundle formBundle) {
		if ( formBundles == null ) {
			formBundles = new ArrayList<FormBundle>();
		}
		formBundles.add(formBundle);
	}

	@Override
	public List<FormBundle> getFormBundles() {
		return CollectionUtils.unmodifiableList(formBundles);
	}
	
}


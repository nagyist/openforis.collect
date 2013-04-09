/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_SETS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author S. Ricci
 *
 */
public class FormSetsPR extends UIModelPR {
	
	private List<FormSet> formBundles;
	
	public FormSetsPR(UIOptionsBinder binder) {
		super(FORM_SETS);
		
		addChildPullReaders(
			new FormSetPR()
		);
		
		setUIOptionsBinder(binder);
	}
	
	@Override
	public synchronized void parse(XmlPullParser parser)
			throws XmlParseException, IOException {
		super.parse(parser);
	}
	
	protected void addFormBundle(FormSet formBundle) {
		if ( formBundles == null ) {
			formBundles = new ArrayList<FormSet>();
		}
		formBundles.add(formBundle);
	}

	@Override
	public List<FormSet> getFormBundles() {
		return CollectionUtils.unmodifiableList(formBundles);
	}
	
}


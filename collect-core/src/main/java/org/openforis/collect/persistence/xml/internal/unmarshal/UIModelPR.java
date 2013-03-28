package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.UI_NAMESPACE_URI;

import java.util.List;

import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;

/**
 * @author S. Ricci
 */
abstract class UIModelPR extends XmlPullReader {

	private UIOptionsBinder binder;

	UIModelPR(String tagName) {
		super(UI_NAMESPACE_URI, tagName);
	}
	
	UIOptionsBinder getUIOptionsBinder() {
		return binder;
	}
	
	void setUIOptionsBinder(UIOptionsBinder binder) {
		this.binder = binder;
		List<XmlPullReader> childPullReaders = getChildPullReaders();
		if ( childPullReaders != null ) {
			for (XmlPullReader reader : childPullReaders) {
				UIModelPR pr = (UIModelPR) reader;
				if ( pr.binder == null ) {
					pr.setUIOptionsBinder(binder);
				}
			}
		}
	}

	public UIOptions getUIOptions() {
		XmlPullReader parent = getParentReader();
		while ( parent != null ) {
			if ( parent instanceof FormBundlePR ) {
				return ((FormBundlePR) parent).getUIOptions();
			}
			parent = parent.getParentReader();
		}
		return null;
	}

}
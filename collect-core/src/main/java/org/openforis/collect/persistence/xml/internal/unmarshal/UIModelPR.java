package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.UI_NAMESPACE_URI;

import java.util.List;

import org.openforis.collect.metamodel.ui.FormBundle;
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
				if ( reader instanceof UIModelPR ) {
					UIModelPR uiModelPR = (UIModelPR) reader;
					if ( uiModelPR.binder == null ) {
						uiModelPR.setUIOptionsBinder(binder);
					}
				}
			}
		}
	}

	public UIOptions getUIOptions() {
		UIOptions uiOptions = binder.getUiOptions();
		return uiOptions;
//		FormBundlesPR rootReader = getRootReader();
//		return rootReader == null ? null: rootReader.getUIOptions();
	}
	
	public FormBundlesPR getRootReader() {
		UIModelPR parent = (UIModelPR) getParentReader();
		while ( parent != null ) {
			if ( parent instanceof FormBundlesPR ) {
				return (FormBundlesPR) parent;
			}
		}
		return null;
	}
	
	public List<FormBundle> getFormBundles() {
		UIModelPR rootReader = getRootReader();
		return rootReader == null ? null: rootReader.getFormBundles();
	}
	
	@Override
	protected UIModelPR getParentReader() {
		return (UIModelPR) super.getParentReader();
	}

}
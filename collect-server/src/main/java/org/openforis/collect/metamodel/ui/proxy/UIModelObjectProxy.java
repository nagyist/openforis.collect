/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.Proxy;
import org.openforis.collect.metamodel.ui.UIModelObject;

/**
 * @author S. Ricci
 *
 */
public class UIModelObjectProxy<T extends UIModelObject> implements Proxy {
	
	protected transient T modelObject;

	public UIModelObjectProxy(T modelObject) {
		super();
		this.modelObject = modelObject;
	}

	@ExternalizedProperty
	public int getId() {
		return modelObject.getId();
	}
	
	
}

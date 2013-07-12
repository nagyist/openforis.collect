/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.Proxy;
import org.openforis.collect.metamodel.ui.UIModelObject;

/**
 * @author S. Ricci
 *
 */
public class UIModelObjectProxy<T extends UIModelObject> implements Proxy {
	
	protected transient T modelObject;
	private UIModelObjectProxy<?> parent;

	public UIModelObjectProxy(UIModelObjectProxy<?> parent, T modelObject) {
		super();
		this.parent = parent;
		this.modelObject = modelObject;
	}

	public String getModelObjectType() {
		return modelObject.getClass().getSimpleName();
	}
	
	@ExternalizedProperty
	public int getId() {
		return modelObject.getId();
	}

	@JsonBackReference
	public UIModelObjectProxy<?> getParent() {
		return parent;
	}

	
}

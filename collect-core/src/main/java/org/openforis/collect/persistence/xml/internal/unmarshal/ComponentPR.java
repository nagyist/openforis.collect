/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import org.openforis.collect.metamodel.ui.Component;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * @author S. Ricci
 *
 */
abstract class ComponentPR extends UIModelPR {

	protected FormSection parent;
	protected Component component;
	
	ComponentPR(String tagName) {
		super(tagName);
	}

	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parent.addComponent(component);
	}

	public FormSection getParent() {
		return parent;
	}

	public void setParent(FormSection parentFormSection) {
		this.parent = parentFormSection;
	}
	
	
}

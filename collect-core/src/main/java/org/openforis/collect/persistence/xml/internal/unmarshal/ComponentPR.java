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

	protected FormSection parentFormSection;
	protected Component component;
	
	ComponentPR(String tagName) {
		super(tagName);
	}

	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parentFormSection.addComponent(component);
	}

	public FormSection getParentFormSection() {
		return parentFormSection;
	}

	public void setParentFormSection(FormSection parentFormSection) {
		this.parentFormSection = parentFormSection;
	}
	
	
}

/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * @author S. Ricci
 *
 */
abstract class ComponentPR extends FormSectionComponentPR {

	ComponentPR(String tagName) {
		super(tagName);
	}

	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		((FormSection) parent).addChild(component);
	}
	
}

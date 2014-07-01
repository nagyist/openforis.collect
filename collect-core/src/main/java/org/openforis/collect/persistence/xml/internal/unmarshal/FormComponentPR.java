package org.openforis.collect.persistence.xml.internal.unmarshal;

import org.openforis.collect.metamodel.ui.FormComponent;
import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * 
 * @author S. Ricci
 *
 */
public class FormComponentPR extends UIModelPR {

	FormComponentPR(String tagName) {
		super(tagName);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		((FormContentContainer) parent).addChild((FormComponent) item);
	}
	
}

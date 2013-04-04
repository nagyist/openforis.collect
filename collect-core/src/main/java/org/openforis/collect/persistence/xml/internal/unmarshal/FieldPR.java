package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FIELD;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Field;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FieldPR extends ComponentPR {
	
	public FieldPR() {
		super(FIELD);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		component = parentFormSection.createField();
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		Field field = (Field) component;
		field.setAttributeId(attributeId);
	}
	
}

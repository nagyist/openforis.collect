package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.AUTOCOMPLETE;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FIELD;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormSection;
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
		int id = getIntegerAttribute(ID, true);
		component = ((FormSection) parent).createField(id);
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		String autoCompleteGroup = getAttribute(AUTOCOMPLETE, false);
		Field field = (Field) component;
		field.setAutoCompleteGroup(autoCompleteGroup);
		field.setAttributeId(attributeId);
	}
	
}

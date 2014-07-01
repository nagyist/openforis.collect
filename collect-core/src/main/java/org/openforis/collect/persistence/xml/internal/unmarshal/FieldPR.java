package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.AUTOCOMPLETE;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.FIELD;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FieldPR extends FormComponentPR {
	
	public FieldPR() {
		super(FIELD);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		String autoCompleteGroup = getAttribute(AUTOCOMPLETE, false);

		item = ((FormContentContainer) parent).createField(id);
		Field field = (Field) item;
		field.setAutoCompleteGroup(autoCompleteGroup);
		field.setAttributeDefinitionId(attributeId);
	}
	
}

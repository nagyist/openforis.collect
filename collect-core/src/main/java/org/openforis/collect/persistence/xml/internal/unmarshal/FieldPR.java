package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ATTRIBUTE_ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.persistence.xml.UIOptionsBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FieldPR extends UIModelPR {
	
	private FormSection parentFormSection;
	private Field field;
	
	public FieldPR(UIOptionsBinder binder) {
		super(binder);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		field = parentFormSection.createField();
		XmlPullParser parser = getParser();
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		
	}

	public FormSection getParentFormSection() {
		return parentFormSection;
	}
}

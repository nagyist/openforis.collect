package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COLUMN;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Column;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class ColumnPR extends UIModelPR {
	
	protected Table parent;
	protected Column column;
	
	public ColumnPR() {
		super(COLUMN);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		column = parent.createColumn(id);
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		column.setAttributeId(attributeId);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parent.addHeadingComponent(column);
	}
}

package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COLUMN;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Column;
import org.openforis.collect.metamodel.ui.TableHeadingComponent;
import org.openforis.collect.metamodel.ui.TableHeadingContainer;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class ColumnPR extends UIModelPR {
	
	public ColumnPR() {
		super(COLUMN);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);

		Column col = ((TableHeadingContainer) parent).createColumn(id);
		col.setAttributeDefinitionId(attributeId);
		item = col;
	}

	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		((TableHeadingContainer) parent).addHeadingComponent((TableHeadingComponent) item);
	}
}

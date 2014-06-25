package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COLUMN;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Column;
import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.collect.metamodel.ui.UIModelObject;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class ColumnPR extends UIModelPR {
	
	//parent can be Table or ColumnGroup
	protected UIModelObject parent;
	protected Column column;
	
	public ColumnPR() {
		super(COLUMN);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		Table parentTable = getParentTable();
		column = parentTable.createColumn(id);
		int attributeId = getIntegerAttribute(ATTRIBUTE_ID, true);
		column.setAttributeId(attributeId);
	}

	private Table getParentTable() {
		Table parentTable = parent instanceof Table ? (Table) parent: ((ColumnGroup) parent).getTable();
		return parentTable;
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		Table parentTable = getParentTable();
		parentTable.addHeadingComponent(column);
	}
}

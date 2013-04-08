package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.COLUMN;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class ColumnGroupPR extends UIModelPR {
	
	protected Table parent;
	private ColumnGroup columnGroup;
	
	public ColumnGroupPR() {
		super(COLUMN);
		
		addChildPullReaders(
			new ColumnPR()
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		columnGroup = parent.createColumnGroup(id);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parent.addColumnGroup(columnGroup);
	}
}

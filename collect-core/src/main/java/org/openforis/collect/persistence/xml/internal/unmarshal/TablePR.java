package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COUNT;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.SHOW_ROW_NUMBERS;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.TABLE;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class TablePR extends FormComponentPR {
	
	public TablePR() {
		super(TABLE);
		
		addChildPullReaders(
				new ColumnPR(),
				new ColumnGroupPR()
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		Table table = ((FormContentContainer) parent).createTable(id);
		item = table;
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		Boolean countInSummaryList = getBooleanAttribute(COUNT, false);
		Boolean showRowNumbers = getBooleanAttribute(SHOW_ROW_NUMBERS, false);
		table.setEntityDefinitionId(entityId);
		table.setCountInSummaryList(countInSummaryList == null ? false: countInSummaryList.booleanValue());
		table.setShowRowNumbers(showRowNumbers == null ? false: showRowNumbers.booleanValue());
	}
	
}

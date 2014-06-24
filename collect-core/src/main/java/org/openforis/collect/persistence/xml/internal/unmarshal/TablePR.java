package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COUNT;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.SHOW_ROW_NUMBERS;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.LABEL;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.TABLE;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class TablePR extends ComponentPR {
	
	public TablePR() {
		super(TABLE);
		
		addChildPullReaders(
				new LabelPR(),
				new ColumnPR(),
				new ColumnGroupPR()
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		component = ((FormSection) parent).createTable(id);
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		Boolean countInSummaryList = getBooleanAttribute(COUNT, false);
		Boolean showRowNumbers = getBooleanAttribute(SHOW_ROW_NUMBERS, false);
		Table table = (Table) component;
		table.setEntityId(entityId);
		table.setCountInSummaryList(countInSummaryList == null ? false: countInSummaryList.booleanValue());
		table.setShowRowNumbers(showRowNumbers == null ? false: showRowNumbers.booleanValue());
	}
	
	@Override
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof ColumnPR ) {
			((ColumnPR) childTagReader).parent = (Table) component;
		} else if ( childTagReader instanceof ColumnGroupPR ) {
			((ColumnGroupPR) childTagReader).parent = (Table) component;
		}
		super.handleChildTag(childTagReader);
	}
	
	private class LabelPR extends LanguageSpecificTextPR {

		public LabelPR() {
			super(LABEL);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			((Table) component).addLabel(lst);
		}
	}
}

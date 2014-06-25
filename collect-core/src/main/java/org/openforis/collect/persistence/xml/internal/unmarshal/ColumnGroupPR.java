package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COLUMN_GROUP;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.LABEL;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.ColumnGroup;
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
class ColumnGroupPR extends UIModelPR {
	
	protected Table parent;
	private ColumnGroup columnGroup;
	
	public ColumnGroupPR() {
		super(COLUMN_GROUP);
		
		addChildPullReaders(
			new LabelPR(),
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
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof ColumnPR ) {
			((ColumnPR) childTagReader).parent = columnGroup;
		}
		super.handleChildTag(childTagReader);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parent.addHeadingComponent(columnGroup);
	}
	
	private class LabelPR extends LanguageSpecificTextPR {

		public LabelPR() {
			super(LABEL);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			columnGroup.addLabel(lst);
		}
	}
}

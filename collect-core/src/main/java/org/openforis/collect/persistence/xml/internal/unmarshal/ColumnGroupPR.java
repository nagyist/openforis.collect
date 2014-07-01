package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.COLUMN_GROUP;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ENTITY_ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.collect.metamodel.ui.TableHeadingComponent;
import org.openforis.collect.metamodel.ui.TableHeadingContainer;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class ColumnGroupPR extends UIModelPR {
	
	public ColumnGroupPR() {
		super(COLUMN_GROUP);
		
		addChildPullReaders(
				new ColumnPR(),
				this
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		item = ((TableHeadingContainer) parent).createColumnGroup(id);
		((ColumnGroup) item).setEntityDefinitionId(entityId);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		
		((TableHeadingContainer) parent).addHeadingComponent((TableHeadingComponent) item);
	}
	
}

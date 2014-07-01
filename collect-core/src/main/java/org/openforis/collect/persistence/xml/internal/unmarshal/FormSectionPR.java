package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.FORM_SECTION;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FormSectionPR extends FormComponentPR {
	
	public FormSectionPR(FormPR formPR) {
		super(FORM_SECTION);

		addChildPullReaders(
				new FieldPR(),
				new TablePR(),
				formPR,
				this
			);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		int entityId = getIntegerAttribute(ENTITY_ID, true);
		
		FormSection section = ((FormContentContainer) parent).createFormSection(id);
		section.setEntityDefinitionId(entityId);
		item = section;
	}
	
}

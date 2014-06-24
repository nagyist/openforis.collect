package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.FORM_SECTION;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.LABEL;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author S. Ricci
 *
 */
class FormSectionPR extends FormSectionComponentPR {
	
	protected FormSection formSection;
	
	public FormSectionPR() {
		super(FORM_SECTION);
		
		addChildPullReaders(
			new LabelPR(),
			new FieldPR(),
			new TablePR(),
			this
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		formSection = parent.createFormSection(id);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		parent.addFormSection(formSection);
	}
	
	@Override
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof FormSectionComponentPR ) {
			((FormSectionComponentPR) childTagReader).parent = formSection;
			super.handleChildTag(childTagReader);
		} else {
			super.handleChildTag(childTagReader);
		}
	}
	
	private class LabelPR extends LanguageSpecificTextPR {

		public LabelPR() {
			super(LABEL);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			((FormSection) formSection).addLabel(lst);
		}
	}
}

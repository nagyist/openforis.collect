package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_SECTION;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.LABEL;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
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
class FormSectionPR extends UIModelPR {
	
	protected Form parent;
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
		if ( childTagReader instanceof FormSectionPR ) {
			FormSectionPR formpr = (FormSectionPR) childTagReader;
			// Store child state in case reused recursively
			Form tmpParent = formpr.parent;
			FormSection tmpFormSection = formpr.formSection;
			super.handleChildTag(childTagReader);
			formpr.parent = tmpParent;
			formpr.formSection = tmpFormSection;
		} else if ( childTagReader instanceof ComponentPR ) {
			((ComponentPR) childTagReader).parent = formSection;
			super.handleChildTag(childTagReader);
		} else {
			super.handleChildTag(childTagReader);
		}
	}
	
	public Form getParent() {
		return parent;
	}
	
	public void setParent(Form parent) {
		this.parent = parent;
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

/**
 * 
 */
package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.FORM;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.ID;
import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.LABEL;

import java.io.IOException;

import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author S. Ricci
 *
 */
public class FormPR extends UIModelPR {

	public FormPR() {
		super(FORM);
		
		addChildPullReaders(
			new LabelPR(),
			new FieldPR(),
			new TablePR(),
			new FormSectionPR(this),
			this
		);
	}
	
	@Override
	protected void onStartTag() throws XmlParseException,
			XmlPullParserException, IOException {
		super.onStartTag();
		int id = getIntegerAttribute(ID, true);
		item = ((FormContentContainer) parent).createForm(id);
	}
	
	@Override
	protected void onEndTag() throws XmlParseException {
		super.onEndTag();
		((FormContentContainer) parent).addForm((Form) item);
	}
	
	public Form getForm() {
		return (Form) item;
	}

	private class LabelPR extends UILanguageSpecificTextPR {

		public LabelPR() {
			super(LABEL);
		}
		
		@Override
		protected void processText(LanguageSpecificText lst) {
			((Form) item).addLabel(lst);
		}
	}
}


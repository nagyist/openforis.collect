/**
 * 
 */
package org.openforis.collect.persistence.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.metamodel.ui.UIConfiguration;
import org.openforis.collect.metamodel.ui.UIConfigurationConstants;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.DataInconsistencyException;
import org.openforis.collect.persistence.xml.internal.marshal.UIConfigurationSerializer;
import org.openforis.collect.persistence.xml.internal.unmarshal.FormSetsPR;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.ApplicationOptionsBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author S. Ricci
 *
 */
public class UIConfigurationBinder implements
		ApplicationOptionsBinder<UIConfiguration> {
	
	private UIConfiguration uiConfiguration;

	@Override
	public UIConfiguration unmarshal(Survey survey, String type, String body) {
		XmlPullParser parser = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
			
			uiConfiguration = new UIConfiguration((CollectSurvey) survey);
			Reader reader = new StringReader(body);
			parser.setInput(reader);
			unmarshalFormSet(parser);
			return uiConfiguration;
		} catch (Exception e) {
			throw new DataInconsistencyException(e.getMessage(), e);
		}
	}

	protected void unmarshalFormSet(XmlPullParser parser) throws IOException, XmlPullParserException, XmlParseException {
		try {
			FormSetsPR reader = new FormSetsPR(this);
			reader.parse(parser);
			List<FormSet> formSets = reader.getFormBundles();
			for (FormSet formSet : formSets) {
				uiConfiguration.addFormSet(formSet);
			}
		} catch ( XmlParseException e) {
			if ( parser != null && parser.getEventType() != XmlPullParser.END_DOCUMENT ) {
				throw e;
			}
		}
	}

	@Override
	public String marshal(UIConfiguration conf) {
		try {
			UIConfigurationSerializer serializer = new UIConfigurationSerializer();
			Writer writer = new StringWriter();
			serializer.write(conf, writer);
			String result = writer.toString();
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Error serializing UIOptions", e);
		}
	}

	@Override
	public boolean isSupported(String optionsType) {
		return UIConfigurationConstants.UI_CONFIGURATION_TYPE.equals(optionsType);
	}
	
	public UIConfiguration getUiConfiguration() {
		return uiConfiguration;
	}
	
}
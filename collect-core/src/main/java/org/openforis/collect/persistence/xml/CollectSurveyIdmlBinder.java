package org.openforis.collect.persistence.xml;

import java.io.InputStream;
import java.io.Reader;

import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptionsMigrator;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.xml.IdmlParseException;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;

/**
 * 
 * @author S. Ricci
 *
 */
public class CollectSurveyIdmlBinder extends SurveyIdmlBinder {

	public CollectSurveyIdmlBinder(SurveyContext surveyContext) {
		super(surveyContext);
		addApplicationOptionsBinder(new UIOptionsBinder());
	}
	
	@Override
	public Survey unmarshal(InputStream is) throws IdmlParseException {
		CollectSurvey result = (CollectSurvey) super.unmarshal(is);
		migrateUIOptions(result);
		return result;
	}
	
	@Override
	public Survey unmarshal(Reader r) throws IdmlParseException {
		CollectSurvey result = (CollectSurvey) super.unmarshal(r);
		migrateUIOptions(result);
		return result;
	}

	@SuppressWarnings("deprecation")
	protected void migrateUIOptions(CollectSurvey survey) {
		UIOptions uiOptions = survey.getUIOptions();
		if ( ! uiOptions.getTabSets().isEmpty() ) {
			UIOptionsMigrator migrator = new UIOptionsMigrator();
			UIOptions newUIOptions = migrator.migrate(uiOptions);
			survey.removeApplicationOptions(uiOptions.getType());
			survey.addApplicationOptions(newUIOptions);
		}
	}
}

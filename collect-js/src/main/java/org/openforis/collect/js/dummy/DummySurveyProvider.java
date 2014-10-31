package org.openforis.collect.js.dummy;

import java.io.InputStream;

import org.openforis.collect.apiadapter.SurveyProvider;
import org.openforis.collect.manager.CodeListManager;
import org.openforis.collect.model.CollectSurveyContext;
import org.openforis.collect.model.validation.CollectValidator;
import org.openforis.collect.persistence.xml.CollectSurveyIdmlBinder;
import org.openforis.collect.service.CollectCodeListService;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.expression.ExpressionFactory;

public class DummySurveyProvider implements SurveyProvider {

	private Survey survey;

	public DummySurveyProvider() {
		try {
			CollectCodeListService codeListService = new CollectCodeListService();
			CodeListManager codeListManager = new CodeListManager();
			codeListService.setCodeListManager(codeListManager);
			CollectValidator validator = new CollectValidator();
			validator.setCodeListManager(codeListManager);
			CollectSurveyContext surveyContext = new CollectSurveyContext(new ExpressionFactory(), validator);
			surveyContext.setCodeListService(codeListService);
			CollectSurveyIdmlBinder idmlBinder = new CollectSurveyIdmlBinder(surveyContext);
			InputStream is = this.getClass().getResourceAsStream("/test.idm.xml");
			survey = idmlBinder.unmarshal(is);
			survey.setId(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Survey provideSurvey(int surveyId) {
		return survey;
	}

}

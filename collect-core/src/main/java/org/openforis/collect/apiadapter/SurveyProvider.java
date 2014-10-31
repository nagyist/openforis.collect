package org.openforis.collect.apiadapter;

import org.openforis.idm.metamodel.Survey;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public interface SurveyProvider {
	
	Survey provideSurvey(int surveyId);

}

/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.Proxy;
import org.openforis.collect.metamodel.proxy.SurveyProxy;
import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.metamodel.ui.UIConfiguration;

/**
 * @author S. Ricci
 *
 */
public class UIConfigurationProxy implements Proxy {
	
	private transient UIConfiguration uiConfiguration;
	private SurveyProxy survey;

	public UIConfigurationProxy(SurveyProxy survey, UIConfiguration uiConfiguration) {
		super();
		this.survey = survey;
		this.uiConfiguration = uiConfiguration;
	}

	public SurveyProxy getSurvey() {
		return survey;
	}
	
	@ExternalizedProperty
	public List<FormSetProxy> getFormSets() {
		List<FormSet> formSets = uiConfiguration.getFormSets();
		return FormSetProxy.fromList(this, formSets);
	}

}

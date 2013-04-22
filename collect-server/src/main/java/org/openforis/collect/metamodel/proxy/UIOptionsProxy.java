/**
 * 
 */
package org.openforis.collect.metamodel.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.Proxy;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.proxy.FormSetProxy;

/**
 * @author S. Ricci
 *
 */
public class UIOptionsProxy implements Proxy {
	
	private transient UIOptions uiOptions;
	private SurveyProxy survey;

	public UIOptionsProxy(SurveyProxy survey, UIOptions uiOptions) {
		super();
		this.uiOptions = uiOptions;
		this.survey = survey;
	}

	public SurveyProxy getSurvey() {
		return survey;
	}
	
	@ExternalizedProperty
	public List<FormSetProxy> getFormSets() {
		return FormSetProxy.fromList(this, uiOptions.getFormSets());
	}
	
}

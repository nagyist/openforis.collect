/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.FormSet;

/**
 * @author S. Ricci
 *
 */
public class FormSetProxy extends FormContentContainerProxy<FormSet> implements EntityDefinitionLinkedUIObjectProxy {

	private transient UIConfigurationProxy uiConfiguration;

	public FormSetProxy(UIConfigurationProxy uiConfiguration, FormSet modelObject) {
		super(null, modelObject);
		this.uiConfiguration = uiConfiguration;
	}

	public static List<FormSetProxy> fromList(UIConfigurationProxy uiConfiguration, List<FormSet> items) {
		List<FormSetProxy> result = new ArrayList<FormSetProxy>();
		for (FormSet item: items) {
			result.add(new FormSetProxy(uiConfiguration, item));
		}
		return result;
	}
	
	@Override
	@ExternalizedProperty
	public int getEntityDefinitionId() {
		return getRootEntityDefinitionId();
	}
	
	@Override
	@ExternalizedProperty
	public int getNodeDefinitionId() {
		return getEntityDefinitionId();
	}

	@ExternalizedProperty
	public UIConfigurationProxy getUIConfiguration() {
		return uiConfiguration;
	}

	@ExternalizedProperty
	public int getRootEntityDefinitionId() {
		return modelObject.getRootEntityDefinitionId();
	}
}

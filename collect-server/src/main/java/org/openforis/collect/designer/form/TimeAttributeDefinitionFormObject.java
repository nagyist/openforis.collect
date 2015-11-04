/**
 * 
 */
package org.openforis.collect.designer.form;

import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class TimeAttributeDefinitionFormObject<T extends TimeAttributeDefinition> extends AttributeDefinitionFormObject<T> {
	
	@Override
	public void saveTo(T dest, String languageCode) {
		super.saveTo(dest, languageCode);
	}
	
	@Override
	public void loadFrom(T source, String languageCode) {
		super.loadFrom(source, languageCode);
	}

}

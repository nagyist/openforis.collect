package org.openforis.collect.metamodel.ui;

import java.util.List;

/**
 * 
 * @author S. Ricci
 *
 */
public interface FormSectionComponentContainer {

	public List<FormSectionComponent> getChildren();
	
	public void addChild(FormSectionComponent child);
	
	public void removeChild(FormSectionComponent child);
	
}

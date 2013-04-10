package org.openforis.collect.metamodel.ui;

/**
 * 
 * @author S. Ricci
 *
 */
public interface FormSectionsContainer {
	
	UIOptions getUiOptions();
	
	void addFormSection(FormSection formSection);

	FormSection createFormSection();
	
	FormSection createFormSection(int id);
	
}

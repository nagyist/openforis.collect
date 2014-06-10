package org.openforis.collect.metamodel.ui;

/**
 * 
 * @author S. Ricci
 *
 */
public interface FormSectionContainer {
	
	UIFormSets getUIOptions();
	
	void addFormSection(FormSection formSection);

	FormSection createFormSection();
	
	FormSection createFormSection(int id);
	
}

/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class Field extends Component {

	private static final long serialVersionUID = 1L;

	private FormSection formSection;
	private AttributeDefinition attributeDefinition;

	public Field(FormSection formSection) {
		super();
		this.formSection = formSection;
	}

	public FormSection getFormSection() {
		return formSection;
	}
	
	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}

	public void setAttributeDefinition(AttributeDefinition attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}
	
}

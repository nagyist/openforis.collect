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
	private int attributeId; 
	private AttributeDefinition attributeDefinition;

	Field(FormSection formSection) {
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

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	
}

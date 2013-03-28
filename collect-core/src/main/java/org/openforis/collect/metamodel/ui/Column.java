/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.io.Serializable;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class Column implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AttributeDefinition attributeDefinition;

	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}

	public void setAttributeDefinition(AttributeDefinition attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}

}

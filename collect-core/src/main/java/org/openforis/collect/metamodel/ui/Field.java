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

	private int attributeId; 
	private String autoCompleteGroup;
	
	Field(FormSection parent, int id) {
		super(parent, id);
	}

	public AttributeDefinition getAttribute() {
		return (AttributeDefinition) getNodeDefinition(attributeId);
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public String getAutoCompleteGroup() {
		return autoCompleteGroup;
	}
	
	public void setAutoCompleteGroup(String autoCompleteGroup) {
		this.autoCompleteGroup = autoCompleteGroup;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + attributeId;
		result = prime
				* result
				+ ((autoCompleteGroup == null) ? 0 : autoCompleteGroup
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (attributeId != other.attributeId)
			return false;
		if (autoCompleteGroup == null) {
			if (other.autoCompleteGroup != null)
				return false;
		} else if (!autoCompleteGroup.equals(other.autoCompleteGroup))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Attribute: " + getAttribute().getPath();
	}

}

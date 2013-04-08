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
	private boolean autocomplete;
	
	Field(FormSection formSection, int id) {
		super(formSection, id);
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

	public boolean isAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(boolean autocomplete) {
		this.autocomplete = autocomplete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + attributeId;
		result = prime * result + (autocomplete ? 1231 : 1237);
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
		if (autocomplete != other.autocomplete)
			return false;
		return true;
	}

}

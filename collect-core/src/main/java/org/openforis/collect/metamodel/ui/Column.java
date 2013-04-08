/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class Column extends UIModelObject {

	private static final long serialVersionUID = 1L;
	
	private Table parent;
	private int attributeId;
	
	Column(Table parent, int id) {
		super(parent.getUiOptions(), id);
	}

	public AttributeDefinition getAttributeDefinition() {
		return (AttributeDefinition) getNodeDefinition(attributeId);
	}

	public Table getParent() {
		return parent;
	}
	
	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + attributeId;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Column other = (Column) obj;
		if (attributeId != other.attributeId)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	
}

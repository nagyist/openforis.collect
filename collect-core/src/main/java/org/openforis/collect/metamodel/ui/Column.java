/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author S. Ricci
 *
 */
public class Column extends TableHeadingComponent {

	private static final long serialVersionUID = 1L;
	
	private int attributeId;
	
	Column(Table table, int id) {
		super(table, id);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + attributeId;
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
		return true;
	}

	@Override
	public String toString() {
		return "Attribute: " + getAttribute().getPath();
	}

}

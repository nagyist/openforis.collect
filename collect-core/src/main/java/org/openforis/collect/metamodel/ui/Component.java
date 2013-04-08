/**
 * 
 */
package org.openforis.collect.metamodel.ui;


/**
 * @author S. Ricci
 *
 */
public abstract class Component extends UIModelObject {

	private static final long serialVersionUID = 1L;
	
	protected FormSection parent;
	
	Component(FormSection parent, int id) {
		super(parent.getUiOptions(), id);
		this.parent = parent;
	}

	public FormSection getParent() {
		return parent;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
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
		return true;
	}

}

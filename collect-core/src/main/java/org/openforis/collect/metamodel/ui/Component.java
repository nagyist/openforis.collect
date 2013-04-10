/**
 * 
 */
package org.openforis.collect.metamodel.ui;


/**
 * @author S. Ricci
 *
 */
public abstract class Component extends UIModelObject implements FormSectionComponent {

	private static final long serialVersionUID = 1L;
	
	Component(FormSection parent, int id) {
		super(parent.getUiOptions(), id);
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

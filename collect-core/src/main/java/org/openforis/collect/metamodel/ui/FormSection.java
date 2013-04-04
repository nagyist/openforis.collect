/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;

/**
 * @author S. Ricci
 *
 */
public class FormSection implements Serializable {

	private static final long serialVersionUID = 1L;

	private Form form;
	private List<Component> components;

	public FormSection(Form form) {
		super();
		this.form = form;
	}

	public Form getForm() {
		return form;
	}

	public Field createField() {
		return new Field(this);
	}
	
	public List<Component> getComponents() {
		return CollectionUtils.unmodifiableList(components);
	}
	
	public void addComponent(Component component) {
		if ( components == null ) {
			components = new ArrayList<Component>();
		}
		components.add(component);
	}
	
	public void removeComponent(Component component) {
		components.remove(component);
	}

	
	
	
}

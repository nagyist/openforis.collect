package org.openforis.collect.metamodel.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;

/**
 * 
 * @author S. Ricci
 *
 */
public abstract class FormContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityDefinition entityDefinition;
	private List<Form> forms;
	
	public Form createForm() {
		return new Form(this);
	}
	
	public EntityDefinition getEntityDefinition() {
		return entityDefinition;
	}

	public void setEntityDefinition(EntityDefinition entityDefinition) {
		this.entityDefinition = entityDefinition;
	}
	
	public void addForm(Form form) {
		if ( forms == null ) {
			forms = new ArrayList<Form>();
		}
		forms.add(form);
	}
	
	public void removeForm(Form form) {
		forms.remove(form);
	}

}

/**
 * 
 */
package org.openforis.collect.designer.viewmodel;

import org.openforis.collect.designer.form.BooleanAttributeDefinitionFormObject;
import org.openforis.collect.designer.form.BooleanAttributeDefinitionFormObject.Type;
import org.openforis.collect.designer.form.NodeDefinitionFormObject;
import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.proxy.FormProxyObject;

/**
 * @author S. Ricci
 *
 */
public class BooleanAttributeVM extends AttributeVM<BooleanAttributeDefinition> {

	@Init(superclass=false)
	public void init(@ExecutionArgParam("parentEntity") EntityDefinition parentEntity, 
			@ExecutionArgParam("item") BooleanAttributeDefinition attributeDefn, 
			@ExecutionArgParam("newItem") Boolean newItem) {
		super.initInternal(parentEntity, attributeDefn, newItem);
	}
	
	@Command
	public void changeType(@ContextParam(ContextType.BINDER) Binder binder) {
		dispatchApplyChangesCommand(binder);
		checkRequiredFieldValue();
		notifyChange("requiredApplied");
	}
	
	@Override
	public boolean isRequiredApplied() {
		Type type = getCurrentType();
		boolean result = type == BooleanAttributeDefinitionFormObject.Type.THREE_STATE;
		return result;
	}
	
	protected void checkRequiredFieldValue() {
		if ( ! isRequiredApplied() ) {
			setValueOnFormField(tempFormObject, NodeDefinitionFormObject.REQUIRED_FIELD, false);
			BindUtils.postNotifyChange(null, null, tempFormObject, NodeDefinitionFormObject.REQUIRED_FIELD);
		}
	}
	
	protected Type getCurrentType() {
		FormProxyObject tempFormObject = getTempFormObject();
		String typeValue = getFormFieldValue(tempFormObject, BooleanAttributeDefinitionFormObject.TYPE_FIELD);
		Type type = null;
		if ( typeValue != null ) {
			type = BooleanAttributeDefinitionFormObject.Type.valueOf(typeValue);
		}
		return type;
	}
	
}

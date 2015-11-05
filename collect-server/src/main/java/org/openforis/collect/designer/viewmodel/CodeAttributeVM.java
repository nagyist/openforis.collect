/**
 * 
 */
package org.openforis.collect.designer.viewmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openforis.collect.designer.form.CodeAttributeDefinitionFormObject;
import org.openforis.collect.designer.util.MessageUtil;
import org.openforis.collect.designer.util.MessageUtil.ConfirmParams;
import org.openforis.collect.designer.util.Predicate;
import org.openforis.collect.designer.viewmodel.SchemaTreePopUpVM.NodeSelectedEvent;
import org.openforis.collect.metamodel.CollectAnnotations.Annotation;
import org.openforis.collect.metamodel.ui.UITab;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.SurveyObject;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.proxy.FormProxyObject;
import org.zkoss.bind.proxy.MapProxy;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;

import liquibase.util.StringUtils;

/**
 * @author S. Ricci
 *
 */
public class CodeAttributeVM extends AttributeVM<CodeAttributeDefinition> {

	private static final String CODE_LIST_ASSIGNED_COMMAND = "codeListAssigned";
	private static final String FORM_ID = "fx";

	private CodeList selectedList;
	
	@Init(superclass=false)
	public void init(@ExecutionArgParam("parentEntity") EntityDefinition parentEntity, 
			@ExecutionArgParam("item") CodeAttributeDefinition attributeDefn, 
			@ExecutionArgParam("newItem") Boolean newItem) {
		super.initInternal(parentEntity, attributeDefn, newItem);
	}
	
	@Override
	public void setEditedItem(CodeAttributeDefinition editedItem) {
		super.setEditedItem(editedItem);
		this.selectedList = editedItem.getList();
		setValueOnFormField(tempFormObject, "codeListId", selectedList == null ? null : selectedList.getId());
		notifyChange("selectedList");
	}

	@Override
	protected FormProxyObject createTemporaryFormObject() {
		@SuppressWarnings("serial")
		FormProxyObject formObject = new MapProxy<String, Object>(new HashMap<String, Object>(){{
			put("codeListId", -1);
		}}, null);
		return formObject;
	}
	
	@Command
	public void onListChanged(@ContextParam(ContextType.BINDER) final Binder binder,
			@BindingParam("list") CodeList list) {
		CodeList oldList = selectedList;
		boolean listChanged = oldList != null && ! oldList.equals(list);
		if (oldList == null) {
			performListChange(binder, list);
		} else if (listChanged) {
			if (editedItem.hasDependentCodeAttributeDefinitions() ) {
				confirmParentCodeListChange(binder, list);
			} else {
				performListChange(binder, list);
			}
		}
	}

	private void confirmCodeListChange(final Binder binder, final CodeList list) {
		ConfirmParams confirmParams = new ConfirmParams(new MessageUtil.ConfirmHandler() {
			@Override
			public void onOk() {
				performListChange(binder, list);
			}
		}, "survey.schema.attribute.code.confirm_change_list.message");
		confirmParams.setOkLabelKey("global.change");
		confirmParams.setCancelLabelKey("global.leave_original_value");
		
		CodeAttributeDefinitionFormObject fo = (CodeAttributeDefinitionFormObject) getFormObject();
		CodeList oldList = fo.getCodeListId() == null ? null : survey.getCodeListById(fo.getCodeListId());
		confirmParams.setMessageArgs(new String[] {oldList.getName(), list.getName()});
		MessageUtil.showConfirm(confirmParams);
	}

	private void confirmParentCodeListChange(final Binder binder, final CodeList list) {
		ConfirmParams confirmParams = new ConfirmParams(new MessageUtil.CompleteConfirmHandler() {
			@Override
			public void onOk() {
				performListChange(binder, list);
			}
			@Override
			public void onCancel() {
				FormProxyObject form = getForm(binder);
				CodeList oldList = editedItem.getList();
				setValueOnFormField(form, "list", oldList);
			}
		}, "survey.schema.attribute.code.confirm_change_list_on_referenced_node.message");
		confirmParams.setOkLabelKey("survey.schema.attribute.code.confirm_change_list_on_referenced_node.ok");
		confirmParams.setTitleKey("survey.schema.attribute.code.confirm_change_list_on_referenced_node.title");
		List<String> dependentAttributePaths = new ArrayList<String>();
		for (CodeAttributeDefinition codeAttributeDefinition : editedItem.getDependentCodeAttributeDefinitions()) {
			dependentAttributePaths.add(codeAttributeDefinition.getPath());
		}
		confirmParams.setMessageArgs(new String[]{StringUtils.join(dependentAttributePaths, ", ")});
		MessageUtil.showConfirm(confirmParams);
	}
	
	private void performListChange(final Binder binder,
			final CodeList list) {
		CodeAttributeDefinitionFormObject fo = (CodeAttributeDefinitionFormObject) getFormObject();
		CodeList oldList = selectedList;
		fo.setParentCodeAttributeDefinitionId(null);
		fo.setCodeListId(list == null ? null : list.getId());
		FormProxyObject form = getForm(binder);
		setValueOnFormField(form, "codeListId", fo.getCodeListId());
		setValueOnFormField(form, "list.hierarchical", list != null && list.isHierarchical());
		setValueOnFormField(form, "parentCodeAttributeDefinition.path", null);
		setValueOnFormField(form, "hierarchicalLevel", null);
		selectedList = list;
		notifyChange("selectedList");
		dispatchApplyChangesCommand(binder);
		notifyChange("dependentCodePaths");
		
		dispatchCodeListAssignedCommand(list, oldList);
	}

	private void dispatchCodeListAssignedCommand(CodeList list, CodeList oldList) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("list", list);
		args.put("oldList", oldList);
		args.put("codeAttribute", editedItem);
		BindUtils.postGlobalCommand(null, null, CODE_LIST_ASSIGNED_COMMAND, args);
	}

	@GlobalCommand
	public void codeListsPopUpClosed(@ContextParam(ContextType.BINDER) Binder binder, 
			@BindingParam(CodeListsVM.EDITING_ATTRIBUTE_PARAM) Boolean editingAttribute, 
			@BindingParam(CodeListsVM.SELECTED_CODE_LIST_PARAM) CodeList selectedCodeList) {
		if ( editingAttribute ) {
			CodeList oldList = selectedList;
			if (oldList != null && ! oldList.equals(selectedCodeList)) {
				confirmCodeListChange(binder, selectedCodeList);
			} else if ( selectedCodeList != null ) {
				onListChanged(binder, selectedCodeList);
			}
			validateForm(binder);
		}
	}

	protected FormProxyObject getForm(Binder binder) {
		Component view = binder.getView();
		return (FormProxyObject) view.getAttribute(FORM_ID);
	}

	@Command
	public void openParentAttributeSelector(@ContextParam(ContextType.BINDER) final Binder binder) {
		String title = Labels.getLabel("survey.schema.attribute.code.select_parent_for_node", new String[]{editedItem.getName()});

		final Collection<CodeAttributeDefinition> assignableParentAttributes = editedItem.getAssignableParentCodeAttributeDefinitions();
		if ( assignableParentAttributes.isEmpty() ) {
			MessageUtil.showWarning("survey.schema.attribute.code.no_assignable_parent_available");
		} else {
			Integer parentCodeDefId = ((CodeAttributeDefinitionFormObject) formObject).getParentCodeAttributeDefinitionId();
			CodeAttributeDefinition parentCodeAttributeDefinition = parentCodeDefId == null ? null : 
				(CodeAttributeDefinition) survey.getSchema().getDefinitionById(parentCodeDefId);
			Predicate<SurveyObject> includedNodePredicate = new Predicate<SurveyObject>() {
				@Override
				public boolean evaluate(SurveyObject item) {
					return item instanceof UITab || item instanceof EntityDefinition ||
							item instanceof CodeAttributeDefinition && assignableParentAttributes.contains(item);
				}
			};
			Predicate<SurveyObject> disabledNodePredicate = new Predicate<SurveyObject>() {
				@Override
				public boolean evaluate(SurveyObject item) {
					return ! (item instanceof CodeAttributeDefinition);
				}
			};
			final Window parentSelectorPopUp = SchemaTreePopUpVM.openPopup(title,
					editedItem.getRootEntity(), null, includedNodePredicate,
					false, disabledNodePredicate, null,
					parentCodeAttributeDefinition);
			parentSelectorPopUp.addEventListener(SchemaTreePopUpVM.NODE_SELECTED_EVENT_NAME, new EventListener<NodeSelectedEvent>() {
				public void onEvent(NodeSelectedEvent event) throws Exception {
					CodeAttributeDefinition parentAttrDefn = (CodeAttributeDefinition) event.getSelectedItem();
					CodeAttributeDefinitionFormObject fo = (CodeAttributeDefinitionFormObject) formObject;
					fo.setParentCodeAttributeDefinitionId(parentAttrDefn == null ? null : parentAttrDefn.getId());
					String hierarchicalLevel = getHierarchicalLevelName(parentAttrDefn);
					fo.setHierarchicalLevel(hierarchicalLevel);
					notifyChange("formObject");
					dispatchApplyChangesCommand(binder);
					notifyChange("dependentCodePaths");
					closePopUp(parentSelectorPopUp);
				}
			});
		}
	}

	private String getHierarchicalLevelName(CodeAttributeDefinition parentAttrDefn) {
		if (parentAttrDefn == null) {
			return null;
		} else {
			Integer parentLevelIndex = parentAttrDefn.getListLevelIndex();
			int levelIndex = parentLevelIndex + 1;
			CodeListLevel level = parentAttrDefn.getList().getHierarchy().get(levelIndex);
			return level.getName();
		}
	}

	@Command
	public void layoutTypeChange(@ContextParam(ContextType.BINDER) Binder binder, 
			@BindingParam("layoutType") String layoutType) {
		setTempFormObjectFieldValue("showAllowedValuesPreview", Annotation.SHOW_ALLOWED_VALUES_PREVIEW.getDefaultValue());
		String layoutDirection = null;
		if ( "radio".equals(layoutType) ) {
			layoutDirection = Annotation.CODE_ATTRIBUTE_LAYOUT_DIRECTION.getDefaultValue();
		}
		setTempFormObjectFieldValue("layoutDirection", layoutDirection);
		dispatchApplyChangesCommand(binder);
	}
	
	public String getDependentCodePaths() {
		if ( newItem ) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			Collection<CodeAttributeDefinition> dependents = editedItem.getDependentCodeAttributeDefinitions();
			Iterator<CodeAttributeDefinition> it = dependents.iterator();
			while (it.hasNext()) {
				CodeAttributeDefinition dependent = it.next();
				sb.append(dependent.getPath());
				if ( it.hasNext() ) {
					sb.append(", ");
				}
			}
			return sb.toString();
		}
	}
	
	public CodeList getSelectedList() {
		return selectedList;
	}
	
}

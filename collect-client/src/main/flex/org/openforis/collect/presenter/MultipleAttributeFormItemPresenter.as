package org.openforis.collect.presenter
{
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.collections.IList;
	import mx.rpc.events.ResultEvent;
	
	import org.openforis.collect.client.ClientFactory;
	import org.openforis.collect.event.ApplicationEvent;
	import org.openforis.collect.event.InputFieldEvent;
	import org.openforis.collect.metamodel.proxy.AttributeDefinitionProxy;
	import org.openforis.collect.model.proxy.AttributeAddRequestProxy;
	import org.openforis.collect.model.proxy.EntityUpdateResponseProxy;
	import org.openforis.collect.model.proxy.NodeUpdateResponseProxy;
	import org.openforis.collect.model.proxy.RecordUpdateRequestSetProxy;
	import org.openforis.collect.model.proxy.RecordUpdateResponseSetProxy;
	import org.openforis.collect.ui.component.detail.MultipleAttributeFormItem;
	import org.openforis.collect.ui.component.input.InputField;
	import org.openforis.collect.util.AlertUtil;
	import org.openforis.collect.util.CollectionUtil;
	import org.openforis.collect.util.UIUtil;

	/**
	 * 
	 * @author S. Ricci
	 *  
	 */
	public class MultipleAttributeFormItemPresenter extends FormItemPresenter {
		
		public function MultipleAttributeFormItemPresenter(view:MultipleAttributeFormItem) {
			super(view);
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			view.addButton.addEventListener(MouseEvent.CLICK, addButtonClickHandler);
			view.addButton.addEventListener(FocusEvent.FOCUS_IN, addButtonFocusInHandler);
			eventDispatcher.addEventListener(InputFieldEvent.VISITED, inputFieldVisitedHandler);
		}
		
		private function get view():MultipleAttributeFormItem {
			return MultipleAttributeFormItem(_view);
		}
		
		override protected function updateResponseReceivedHandler(event:ApplicationEvent):void {
			super.updateResponseReceivedHandler(event);
			if(_view.parentEntity != null) {
				var responseSet:RecordUpdateResponseSetProxy = RecordUpdateResponseSetProxy(event.result);
				for each (var response:NodeUpdateResponseProxy in responseSet.responses) {
					if( response is EntityUpdateResponseProxy && 
							EntityUpdateResponseProxy(response).nodeId == _view.parentEntity.id) {
						updateValidationDisplayManager();
						updateRelevanceDisplayManager();
						break;
					}
				}
			}
		}
		
		protected function inputFieldVisitedHandler(event:InputFieldEvent):void {
			var inputField:InputField = event.inputField;
			if(inputField != null && inputField.parentEntity != null && inputField.attributeUIModelObject == view.attributeUIModelObject) {
				updateValidationDisplayManager();
			}
		}
		
		override protected function updateView():void {
			super.updateView();
			if(view.dataGroup != null && view.parentEntity != null) {
				var attributes:IList = getAttributes();
				view.dataGroup.dataProvider = attributes;
			}
		}

		protected function getAttributes():IList {
			if(view.dataGroup != null && view.parentEntity != null) {
				var name:String = view.attributeUIModelObject.attributeDefinition.name;
				var attributes:IList = view.parentEntity.getChildren(name);
				return attributes;
			} else {
				return null;
			}
		}
		
		protected function addButtonFocusInHandler(event:FocusEvent):void {
			UIUtil.ensureElementIsVisible(event.target);
		}
		
		protected function addButtonClickHandler(event:MouseEvent):void {
			var attributes:IList = getAttributes();
			var attrDefn:AttributeDefinitionProxy = view.attributeUIModelObject.attributeDefinition;
			var maxCount:Number = attrDefn.maxCount
			if(isNaN(maxCount) || CollectionUtil.isEmpty(attributes) || attributes.length < maxCount) {
				var r:AttributeAddRequestProxy = new AttributeAddRequestProxy();
				r.parentEntityId = view.parentEntity.id;
				r.nodeName = attrDefn.name;
				var reqSet:RecordUpdateRequestSetProxy = new RecordUpdateRequestSetProxy(r);
				ClientFactory.dataClient.updateActiveRecord(reqSet, addResultHandler, faultHandler);
			} else {
				var labelText:String = attrDefn.getInstanceOrHeadingLabelText();
				AlertUtil.showError("edit.maxCountExceed", [maxCount, labelText]);
			}	
		}
		
		protected function addResultHandler(event:ResultEvent, token:Object = null):void {
			view.callLater(function():void {
				UIUtil.ensureElementIsVisible(view.addButton);
			});
		}
		
		override protected function initValidationDisplayManager():void {
			super.initValidationDisplayManager();
			_validationDisplayManager.showMinMaxCountErrors = true;
			updateValidationDisplayManager();
		}
		
		override protected function updateRelevanceDisplayManager():void {
			_relevanceDisplayManager.displayNodeRelevance(view.parentEntity, view.attributeUIModelObject.attributeDefinition);
		}
		
		override protected function updateValidationDisplayManager():void {
			super.updateValidationDisplayManager();
			var attrDefn:AttributeDefinitionProxy = view.attributeUIModelObject.attributeDefinition;
			if(_view.parentEntity != null) {
				var attributeName:String = attrDefn.name;
				var visited:Boolean = _view.parentEntity.isErrorOnChildVisible(attributeName);
				var active:Boolean = visited;
				if(active) {
					_validationDisplayManager.active = true;
					_validationDisplayManager.displayMinMaxCountValidationErrors(view.parentEntity, attrDefn);
				} else {
					_validationDisplayManager.active = false;
					_validationDisplayManager.reset();
				}
			}
		}

	}
}
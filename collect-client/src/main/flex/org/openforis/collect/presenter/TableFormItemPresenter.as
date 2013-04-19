package org.openforis.collect.presenter
{
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.binding.utils.BindingUtils;
	import mx.collections.IList;
	import mx.rpc.events.ResultEvent;
	
	import org.openforis.collect.client.ClientFactory;
	import org.openforis.collect.event.InputFieldEvent;
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.model.proxy.EntityAddRequestProxy;
	import org.openforis.collect.model.proxy.EntityProxy;
	import org.openforis.collect.model.proxy.RecordUpdateRequestSetProxy;
	import org.openforis.collect.ui.component.detail.TableFormItem;
	import org.openforis.collect.ui.component.input.InputField;
	import org.openforis.collect.util.AlertUtil;
	import org.openforis.collect.util.CollectionUtil;
	import org.openforis.collect.util.UIUtil;

	/**
	 * 
	 * @author S. Ricci
	 *  
	 */
	public class TableFormItemPresenter extends FormItemPresenter {
		
		public function TableFormItemPresenter(view:TableFormItem) {
			super(view);
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			BindingUtils.bindSetter(setModelVersion, _view, "modelVersion");

			view.addButton.addEventListener(MouseEvent.CLICK, addButtonClickHandler);
			view.addButton.addEventListener(FocusEvent.FOCUS_IN, addButtonFocusInHandler);
			eventDispatcher.addEventListener(InputFieldEvent.VISITED, inputFieldVisitedHandler);
		}
		
		private function get view():TableFormItem {
			return TableFormItem(_view);
		}
		
		protected function setModelVersion(version:ModelVersionProxy):void {
			updateView();
		}
		
		protected function initHeadingComponents():void {
			var temp:IList = null;
			if(view.table != null ) {
				temp = view.table.getHeadingComponentsInVersion(view.modelVersion);
			}
			view.headingComponents = temp;
		}
		
		override protected function initValidationDisplayManager():void {
			super.initValidationDisplayManager();
			_validationDisplayManager.showMinMaxCountErrors = true;
		}
		
		override protected function updateView():void {
			initHeadingComponents();
			if(view.parentEntity != null) {
				var entities:IList = getEntities();
				view.dataGroup.dataProvider = entities;
			} else {
				view.dataGroup.dataProvider = null;
			}
			/*var entity:EntityProxy = null;
			if(view.parentEntity != null && view.entityDefinition != null && ! view.entityDefinition.multiple) {
				//assign entity
				entity = view.parentEntity.getChild(view.entityDefinition.name, 0) as EntityProxy;
			}
			view.entity = entity;*/
			super.updateView();
		}
		
		protected function getEntities():IList {
			var name:String = view.table.entityDefinition.name;
			var entities:IList = null;
			if(view.parentEntity != null) {
				entities = view.parentEntity.getChildren(name);
			}
			return entities;
		}

		protected function addButtonFocusInHandler(event:FocusEvent):void {
			UIUtil.ensureElementIsVisible(event.target);
		}
		
		protected function addButtonClickHandler(event:MouseEvent):void {
			var entities:IList = getEntities();
			var entityDefn:EntityDefinitionProxy = view.table.entityDefinition;
			var maxCount:Number = entityDefn.maxCount
			if(isNaN(maxCount) || CollectionUtil.isEmpty(entities) || entities.length < maxCount) {
				var r:EntityAddRequestProxy = new EntityAddRequestProxy();
				r.parentEntityId = view.parentEntity.id;
				r.nodeName = entityDefn.name;
				var reqSet:RecordUpdateRequestSetProxy = new RecordUpdateRequestSetProxy(r);
				ClientFactory.dataClient.updateActiveRecord(reqSet, addResultHandler, faultHandler);
			} else {
				var labelText:String = entityDefn.getInstanceOrHeadingLabelText();
				AlertUtil.showError("edit.maxCountExceed", [maxCount, labelText]);
			}
		}
		
		protected function addResultHandler(event:ResultEvent, token:Object = null):void {
			view.callLater(function():void {
				updateValidationDisplayManager();
				
				if(view.scroller != null && view.scroller.verticalScrollBar != null) {
					view.scroller.verticalScrollBar.value = view.scroller.verticalScrollBar.maximum;
				}
				UIUtil.ensureElementIsVisible(view.addButton);
			});
		}
		
		protected function inputFieldVisitedHandler(event:InputFieldEvent):void {
			var inputField:InputField = event.inputField;
			if(inputField != null && inputField.parentEntity != null) {
				var entities:IList = getEntities();
				for each (var e:EntityProxy in entities) {
					if(e == inputField.parentEntity) {
						updateValidationDisplayManager();
						break;
					}
				}
			}
		}
		
		override protected function updateValidationDisplayManager():void {
			super.updateValidationDisplayManager();
			if(view.parentEntity != null) {
				var entityDefn:EntityDefinitionProxy = view.table.entityDefinition;
				var name:String = entityDefn.name;
				var visited:Boolean = view.parentEntity.isErrorOnChildVisible(name);
				var active:Boolean = visited;
				if(active) {
					_validationDisplayManager.active = true;
					_validationDisplayManager.displayMinMaxCountValidationErrors(view.parentEntity, entityDefn);
				} else {
					_validationDisplayManager.active = false;
					_validationDisplayManager.reset();
				}
			}
		}
		
		override protected function updateRelevanceDisplayManager():void {
			super.updateRelevanceDisplayManager();
			relevanceDisplayManager.displayNodeRelevance(view.parentEntity, view.table.entityDefinition);
		}
		
	}
}
package org.openforis.collect.presenter
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	
	import mx.binding.utils.ChangeWatcher;
	import mx.collections.IList;
	import mx.events.PropertyChangeEvent;
	
	import org.openforis.collect.event.ApplicationEvent;
	import org.openforis.collect.metamodel.proxy.AttributeDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.model.proxy.AttributeProxy;
	import org.openforis.collect.model.proxy.EntityProxy;
	import org.openforis.collect.ui.component.detail.AttributeFormItem;
	import org.openforis.collect.util.UIUtil;

	/**
	 * 
	 * @author S. Ricci
	 *  
	 */
	public class AttributeFormItemPresenter extends FormItemPresenter {
		
		public function AttributeFormItemPresenter(view:AttributeFormItem) {
			super(view);
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			ChangeWatcher.watch(_view, "attribute", attributeChangeHandler);
		}
		
		protected function focusInHandler(event:FocusEvent):void {
			UIUtil.ensureElementIsVisible(event.target);
		}
		
		protected function focusOutHandler(event:FocusEvent):void {
		}
		
		override protected function parentEntityChangeHandler(event:PropertyChangeEvent):void {
			assignAttribute();
		}
		
		protected function attributeChangeHandler(event:Event):void {
			updateView();
		}
		
		private function get view():AttributeFormItem {
			return AttributeFormItem(_view);
		}
		
		override protected function updateRelevanceDisplayManager():void {
			super.updateRelevanceDisplayManager();
			relevanceDisplayManager.displayNodeRelevance(view.parentEntity, view.attributeUIModelObject.attributeDefinition);
		}
		
		override protected function updateValidationDisplayManager():void {
			//validation display managed by AttributePresenter
			return;
		}

		/**
		 * get the attribute (or attributes) from the parentEntity
		 */
		protected function assignAttribute():void {
			var parentEntity:EntityProxy = view.parentEntity;
			if (parentEntity != null ) {
				var attrDefn:AttributeDefinitionProxy = view.attributeDefinition;
				var name:String = attrDefn.name;
				var nearestParentEntity:EntityProxy = parentEntity.getDescendantNearestParentEntity(attrDefn);
				if (attrDefn.multiple) {
					var attributes:IList = nearestParentEntity.getChildren(name);
					view.attributes = attributes;
				} else {
					var attribute:AttributeProxy = nearestParentEntity.getSingleAttribute(name);
					view.attribute = attribute;
				}
			} else {
				view.attribute = null;
				view.attributes = null;
			}
		}
		
	}
}
package org.openforis.collect.ui.component.detail {
	import mx.collections.IList;
	import mx.events.FlexEvent;
	
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.metamodel.proxy.NodeDefinitionProxy;
	import org.openforis.collect.model.proxy.EntityProxy;
	import org.openforis.collect.presenter.FormItemPresenter;
	
	import spark.components.Group;
	
	
	/**
	 * 
	 * @author M. Togna
	 * */
	public class CollectFormItem extends Group {
		
		private var _parentEntity:EntityProxy;
		private var _modelVersion:ModelVersionProxy;
		private var _childrenAdded:Boolean = false;
		private var _occupyEntirePage:Boolean = false;
		private var _labelWidth:Number = 150;
		
		protected var _presenter:FormItemPresenter;
		
		public function CollectFormItem() {
			this.addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
			this.addEventListener(FlexEvent.INITIALIZE, initializeHandler);
		}
		
		protected function initializeHandler(event:FlexEvent):void {
			
		}
		
		protected function creationCompleteHandler(event:FlexEvent):void {
			initPresenter();
		}
		
		protected function initPresenter():void {
			_presenter = new FormItemPresenter(this);
		}

		[Bindable]
		public function get parentEntity():EntityProxy {
			return _parentEntity;
		}
		
		public function set parentEntity(value:EntityProxy):void {
			_parentEntity = value;
		}
		
		protected function calculateEffectiveParentEntity(value:EntityProxy):EntityProxy {
			if ( value != null ) {
				var parentEntityDefn:EntityDefinitionProxy = EntityDefinitionProxy(value.definition);
				var childDefns:IList = parentEntityDefn.getDefinitionsInVersion(modelVersion);
				if ( childDefns.length == 1 ) {
					var firstChildDefn:NodeDefinitionProxy = NodeDefinitionProxy(childDefns.getItemAt(0));
					if ( firstChildDefn is EntityDefinitionProxy && !(firstChildDefn.multiple) ) {
						var result:EntityProxy = value.getChild(firstChildDefn.name, 0) as EntityProxy;
						return result;
					}
				}
			}
			return value;
		}
		
		[Bindable]
		public function get modelVersion():ModelVersionProxy {
			return _modelVersion;
		}
		
		public function set modelVersion(value:ModelVersionProxy):void {
			_modelVersion = value;
		}

		//to be implemented in subclasses
		public function get nodeDefinition():NodeDefinitionProxy {
			return null;
		}
		
		[Bindable]
		protected function get childrenAdded():Boolean {
			return _childrenAdded;
		}
		
		protected function set childrenAdded(value:Boolean):void {
			_childrenAdded = value;
		}
		
		protected function get presenter():FormItemPresenter {
			return _presenter;
		}
		
		protected function set presenter(value:FormItemPresenter):void {
			_presenter = value;
		}
		
		[Bindable]
		public function get occupyEntirePage():Boolean {
			return _occupyEntirePage;
		}
		
		public function set occupyEntirePage(value:Boolean):void {
			_occupyEntirePage = value;
		}
		
		[Bindable]
		public function get labelWidth():Number {
			return _labelWidth;
		}
		
		public function set labelWidth(value:Number):void {
			_labelWidth = value;
		}
		
	}
}
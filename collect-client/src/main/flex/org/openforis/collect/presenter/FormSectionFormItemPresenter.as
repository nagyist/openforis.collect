package org.openforis.collect.presenter
{
	import mx.binding.utils.BindingUtils;
	import mx.collections.IList;
	
	import org.openforis.collect.event.ApplicationEvent;
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.model.proxy.EntityProxy;
	import org.openforis.collect.ui.component.detail.EntityFormItem;
	import org.openforis.collect.ui.component.detail.FormSectionFormItem;
	
	/**
	 * 
	 * @author S. Ricci
	 * 
	 */
	public class FormSectionFormItemPresenter extends FormItemPresenter {
		
		public function FormSectionFormItemPresenter(view:FormSectionFormItem) {
			super(view);
			initItems();
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			BindingUtils.bindSetter(setEntityDefinition, view, "entityDefinition");
		}
		
		protected function setEntityDefinition(entityDefinition:EntityDefinitionProxy):void {
			updateView();
		}
		
		protected function setModelVersion(version:ModelVersionProxy):void {
			updateView();
		}
		
		private function get view():FormSectionFormItem {
			return FormSectionFormItem(_view);
		}
		
		protected function initItems():void {
			var temp:IList = null;
			if (view.formSection != null) {
				temp = view.formSection.getChildrenInVersion(view.modelVersion);
			}
			view.items = temp;
		}
		
		override protected function updateView():void {
			initItems();
			super.updateView();
		}
		
		override protected function updateValidationDisplayManager():void {
			super.updateValidationDisplayManager();
			/*if(view.parentEntity != null && view.entityDefinition != null) {
				validationDisplayManager.displayMinMaxCountValidationErrors(view.parentEntity, view.entityDefinition);
			}*/
		}
		
		override protected function updateRelevanceDisplayManager():void {
			super.updateRelevanceDisplayManager();
			/*relevanceDisplayManager.displayNodeRelevance(view.parentEntity, view.entityDefinition);*/
		}
	}
}
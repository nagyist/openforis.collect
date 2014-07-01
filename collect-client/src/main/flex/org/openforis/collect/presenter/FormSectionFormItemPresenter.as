package org.openforis.collect.presenter
{
	import mx.binding.utils.BindingUtils;
	import mx.collections.IList;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.metamodel.ui.proxy.FormSectionProxy;
	import org.openforis.collect.ui.component.detail.FormSectionFormItem;
	import org.openforis.collect.model.proxy.RecordProxy;
	
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
			
			BindingUtils.bindSetter(setFormSection, view, "formSection");
		}
		
		protected function setFormSection(formSection:FormSectionProxy):void {
			updateView();
		}
		
		private function get view():FormSectionFormItem {
			return FormSectionFormItem(_view);
		}
		
		protected function initItems():void {
			var temp:IList = null;
			if (view.formSection != null) {
				var record:RecordProxy = Application.activeRecord;
				temp = view.formSection.getChildrenInVersion(record.version);
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
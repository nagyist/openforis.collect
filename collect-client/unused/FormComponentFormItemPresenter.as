package org.openforis.collect.presenter
{
	import mx.binding.utils.BindingUtils;
	
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.ui.component.detail.FormComponentFormItem;
	
	/**
	 * 
	 * @author S. Ricci
	 * 
	 */
	public class FormComponentFormItemPresenter extends FormItemPresenter {
		
		public function FormComponentFormItemPresenter(view:FormComponentFormItem) {
			super(view);
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			BindingUtils.bindSetter(setModelVersion, _view, "modelVersion");
		}
		
		protected function setModelVersion(version:ModelVersionProxy):void {
			updateView();
		}
		
	}
}
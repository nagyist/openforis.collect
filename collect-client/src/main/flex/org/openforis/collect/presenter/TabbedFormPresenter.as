package org.openforis.collect.presenter {
	import mx.binding.utils.BindingUtils;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.metamodel.proxy.UIOptionsProxy;
	import org.openforis.collect.metamodel.ui.proxy.FormContainerProxy;
	import org.openforis.collect.metamodel.ui.proxy.FormProxy;
	import org.openforis.collect.ui.component.detail.TabbedFormContainer;
	
	/**
	 * 
	 * @author S. Ricci
	 * 
	 */
	public class TabbedFormPresenter extends AbstractPresenter {
		
		private var _view:TabbedFormContainer;
		
		public function TabbedFormPresenter(view:TabbedFormContainer) {
			_view = view;
			super();
			buildView();
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			BindingUtils.bindSetter(setFormContainer, _view, "formContainer");
			BindingUtils.bindSetter(setModelVersion, _view, "modelVersion");
		}
		
		protected function setFormContainer(value:FormContainerProxy):void {
			buildView();
		}
		
		protected function setModelVersion(value:ModelVersionProxy):void {
			buildView();
		}
		
		protected function buildView():void {
			if ( _view.formContainer != null ) {
				if ( _view.formContainer is FormProxy ) {
					_view.fieldsPerCurrentForm = UIOptionsProxy.getFieldsPerForm(org.openforis.collect.Application.activeSurvey, 
						_view.formContainer as FormProxy, _view.modelVersion);
				}
			}
		}
	}
}
package org.openforis.collect.presenter {
	import mx.binding.utils.BindingUtils;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.metamodel.proxy.EntityDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.metamodel.proxy.SurveyProxy;
	import org.openforis.collect.metamodel.proxy.UIOptionsProxy;
	import org.openforis.collect.metamodel.ui.proxy.FormContainerProxy;
	import org.openforis.collect.metamodel.ui.proxy.FormProxy;
	import org.openforis.collect.ui.component.detail.FormContainerContentRenderer;
	
	/**
	 * 
	 * @author S. Ricci
	 * 
	 */
	public class FormContainerContentPresenter extends AbstractPresenter {
		
		private var _view:FormContainerContentRenderer;
		
		public function FormContainerContentPresenter(view:FormContainerContentRenderer) {
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
			_view.innerForms = _view.formContainer == null ? null: _view.formContainer.forms;
			if ( _view.formContainer is FormProxy ) {
				if ( _view.hasItemsPerCurrentForm ) {
					if ( _view.hasInnerForms ) {
						_view.currentState = FormContainerContentRenderer.STATE_COMPLETE;
					} else {
						_view.currentState = FormContainerContentRenderer.STATE_DEFAULT;
					}
				} else if ( _view.hasInnerForms ) {
					_view.currentState = FormContainerContentRenderer.STATE_INNER_FORMS;
				}
			}
		}
	}
}
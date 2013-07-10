package org.openforis.collect.presenter
{
	import org.granite.reflect.Field;
	import org.openforis.collect.metamodel.proxy.CoordinateAttributeDefinitionProxy;
	import org.openforis.collect.metamodel.ui.UIOptions$CoordinateAttributeFieldsOrder;
	import org.openforis.collect.metamodel.ui.UIOptions$Direction;
	import org.openforis.collect.metamodel.ui.proxy.AttributeModelObjectProxy;
	import org.openforis.collect.metamodel.ui.proxy.FieldProxy;
	import org.openforis.collect.metamodel.ui.proxy.TableProxy;
	import org.openforis.collect.ui.component.input.CoordinateAttributeRenderer;
	import org.openforis.collect.util.UIUtil;
	
	/**
	 * @author S. Ricci
	 * */
	public class CoordinateAttributePresenter extends CompositeAttributePresenter {
		
		public function CoordinateAttributePresenter(view:CoordinateAttributeRenderer) {
			super(view);
		}
		
		override protected function initViewState():void {
			var attrDefn:CoordinateAttributeDefinitionProxy = CoordinateAttributeDefinitionProxy(_view.attributeDefinition);
			var uiField:FieldProxy = _view.attributeUIModelObject as FieldProxy;
			var view:CoordinateAttributeRenderer = CoordinateAttributeRenderer(_view);
			if ( uiField.parent is TableProxy ) {
				if ( (TableProxy(uiField.parent)).direction == UIOptions$Direction.BY_COLUMNS ) {
					if ( uiField.fieldsOrder == UIOptions$CoordinateAttributeFieldsOrder.SRS_X_Y ) {
						view.currentState = CoordinateAttributeRenderer.STATE_VERTICAL_SRS_X_Y;
					} else {
						view.currentState = CoordinateAttributeRenderer.STATE_VERTICAL_SRS_Y_X;
					}
				} else {
					if ( uiField.fieldsOrder == UIOptions$CoordinateAttributeFieldsOrder.SRS_X_Y ) {
						view.currentState = CoordinateAttributeRenderer.STATE_HORIZONTAL_SRS_X_Y;
					} else {
						view.currentState = CoordinateAttributeRenderer.STATE_HORIZONTAL_SRS_Y_X;
					}
				}
			} else {
				if ( uiField.fieldsOrder == UIOptions$CoordinateAttributeFieldsOrder.SRS_X_Y ) {
					view.currentState = CoordinateAttributeRenderer.STATE_VERTICAL_FORM_SRS_X_Y;
				} else {
					_view.currentState = CoordinateAttributeRenderer.STATE_VERTICAL_FORM_SRS_Y_X;
				}
			}
		}
	}
}
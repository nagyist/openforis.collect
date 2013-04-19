/**
 * Generated by Gas3 v2.3.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.metamodel.ui.proxy {
	import mx.collections.ArrayList;
	import mx.collections.IList;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.metamodel.proxy.LanguageSpecificTextProxy;
	import org.openforis.collect.metamodel.proxy.ModelVersionProxy;
	import org.openforis.collect.metamodel.proxy.NodeDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.SchemaProxy;
	import org.openforis.collect.metamodel.proxy.SurveyProxy;
	import org.openforis.collect.util.CollectionUtil;

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.metamodel.ui.proxy.FormSectionProxy")]
    public class FormSectionProxy extends FormSectionProxyBase {
		
		public function get labelText():String {
			var langCode:String = Application.localeLanguageCode;
			var defaultLanguage:Boolean = survey.defaultLanguageCode == langCode;
			var result:String = LanguageSpecificTextProxy.getLocalizedText(this.labels, langCode, defaultLanguage);
			if ( result == null ) {
				return String(id);
			} else {
				return result;
			}
		}
		
		/**
		 * Traverse each child and pass its parent and itself  to the argument function
		 * */
		override public function traverse(funct:Function):void {
			for each (var child:UIModelObjectProxy in children) {
				funct(this, child);
				child.traverse(funct);
			}
		}

		public function getChildrenInVersion(version:ModelVersionProxy):IList {
			var result:IList = new ArrayList();
			var survey:SurveyProxy = uiOptions.survey;
			var schema:SchemaProxy = survey.schema;
			for each ( var child:FormSectionComponentProxy in children ) {
				if ( child is FieldProxy ) {
					var attrDefn:NodeDefinitionProxy = (child as FieldProxy).attributeDefinition;
					if ( version == null || version.isApplicable(attrDefn) ) {
						result.addItem(child);
					}
				} else if ( child is TableProxy ) {
					var entityDefn:NodeDefinitionProxy = (child as TableProxy).entityDefinition;
					if ( version == null || version.isApplicable(entityDefn) ) {
						result.addItem(child);
					}
				} else if ( child is FormSectionProxy ) {
					var childrenInVersion:IList = (child as FormSectionProxy).getChildrenInVersion(version);
					if ( CollectionUtil.isNotEmpty(childrenInVersion) ) {
						result.addItem(child);
					}
				}
			}
			return result;
		}
		
    }
}
/**
 * Generated by Gas3 v2.3.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.metamodel.ui.proxy {

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.metamodel.ui.proxy.ColumnGroupProxy")]
    public class ColumnGroupProxy extends ColumnGroupProxyBase {
		
		/**
		 * Traverse each child and pass its parent and itself  to the argument function
		 * */
		override public function traverse(funct:Function):void {
			for each (var child:TableHeadingComponentProxy in headingComponents) {
				funct(this, child);
				child.traverse(funct);
			}
		}
    }
}
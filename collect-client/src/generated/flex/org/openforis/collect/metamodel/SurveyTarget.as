/**
 * Generated by Gas3 v2.3.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package org.openforis.collect.metamodel {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.metamodel.SurveyTarget")]
    public class SurveyTarget extends Enum {

        public static const COLLECT_DESKTOP:SurveyTarget = new SurveyTarget("COLLECT_DESKTOP", _);
        public static const COLLECT_EARTH:SurveyTarget = new SurveyTarget("COLLECT_EARTH", _);

        function SurveyTarget(value:String = null, restrictor:* = null) {
            super((value || COLLECT_DESKTOP.name), restrictor);
        }

        protected override function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [COLLECT_DESKTOP, COLLECT_EARTH];
        }

        public static function valueOf(name:String):SurveyTarget {
            return SurveyTarget(COLLECT_DESKTOP.constantOf(name));
        }
    }
}
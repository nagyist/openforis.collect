/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.model.proxy {
	import org.openforis.collect.model.CollectRecord$Step;
	import org.openforis.collect.util.CollectionUtil;

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.model.proxy.UserProxy")]
    public class UserProxy extends UserProxyBase {
		
		public static const ROLE_VIEW:String = "ROLE_VIEW";
		public static const ROLE_ENTRY_LIMITED:String = "ROLE_ENTRY_LIMITED";
		public static const ROLE_ENTRY:String = "ROLE_ENTRY";
		public static const ROLE_CLEANSING:String = "ROLE_CLEANSING";
		public static const ROLE_ANALYSIS:String = "ROLE_ANALYSIS";
		public static const ROLE_ADMIN:String = "ROLE_ADMIN";
		public static const ROLES:Array = [ROLE_VIEW, ROLE_ENTRY_LIMITED, ROLE_ENTRY, ROLE_CLEANSING, ROLE_ANALYSIS, ROLE_ADMIN];
		
		protected static const ROLES_HIERARCHY:Array = [ROLE_VIEW, ROLE_ENTRY_LIMITED, ROLE_ENTRY, ROLE_CLEANSING, ROLE_ANALYSIS, ROLE_ADMIN];
		
		private function calculateHighestRoleIndex():int {
			var max:int = -1;
			for each (var role:String in roles) {
				var i:int = ROLES_HIERARCHY.indexOf(role);
				if ( i > max ) {
					max = i;
				}
			}
			return max;
		}
		
		public function hasRole(role:String):Boolean {
			var result:Boolean = CollectionUtil.contains(roles, role);
			return result;
		}
		
		public function hasEffectiveRole(role:String):Boolean {
			var highest:int = calculateHighestRoleIndex();
			var index:int = ROLES_HIERARCHY.indexOf(role);
			return highest >= index;
		}
		
		public function addRole(role:String):void {
			roles.addItem(role);
		}
		
		public function removeRole(role:String):void {
			var index:int = roles.getItemIndex(role);
			if ( index >= 0 ) {
				roles.removeItemAt(index);
			}
		}
		
		public function isOwner(record:RecordProxy):Boolean {
			return record.owner != null && record.owner.id == id;
		}
		
		public function canSubmit(record:RecordProxy):Boolean {
			return hasEffectiveRole(ROLE_ENTRY_LIMITED) && record.step == CollectRecord$Step.ENTRY || 
				hasEffectiveRole(ROLE_CLEANSING) && record.step == CollectRecord$Step.CLEANSING;
		}

		public function canReject(record:RecordProxy):Boolean {
			return hasEffectiveRole(ROLE_CLEANSING) && record.step == CollectRecord$Step.CLEANSING || 
				hasEffectiveRole(ROLE_ANALYSIS) && record.step == CollectRecord$Step.ANALYSIS;
		}

		public function canEdit(record:RecordProxy):Boolean {
			var step:CollectRecord$Step = record.step;
			var result:Boolean = false;
			switch ( step ) {
				case CollectRecord$Step.ENTRY:
					result = hasEffectiveRole(ROLE_ENTRY_LIMITED);
					break;
				case CollectRecord$Step.CLEANSING:
					result = hasEffectiveRole(ROLE_CLEANSING);
					break;
				case CollectRecord$Step.ANALYSIS:
					result = false
					break;
			}
			return result;
		}
		
		public function get canDeleteNotOwnedRecords():Boolean {
			return hasEffectiveRole(ROLE_CLEANSING);
		}
		
		public function get canEditRecords():Boolean {
			return hasEffectiveRole(ROLE_ENTRY_LIMITED);
		}
		
		public function get canEditOnlyOwnedRecords():Boolean {
			var highest:int = calculateHighestRoleIndex();
			var index:int = ROLES_HIERARCHY.indexOf(ROLE_ENTRY_LIMITED);
			return highest == index;
		}
		
		public function get canEditNotOwnedRecords():Boolean {
			return hasEffectiveRole(ROLE_CLEANSING);
		}
		
		public function get canViewAllRecords():Boolean {
			return hasRole(ROLE_VIEW);
		}
		
		public function get canViewNotOwnedRecords():Boolean {
			return hasEffectiveRole(ROLE_CLEANSING) || hasRole(ROLE_VIEW);
		}
		
		public function get canViewMap():Boolean {
			return canViewAllRecords;
		}
		
		public function get canRunSaikuAnalysis():Boolean {
			return hasEffectiveRole(ROLE_CLEANSING) || hasRole(ROLE_VIEW);
		}
		
		public function get canCancelApplicationLockingJob():Boolean {
			return hasEffectiveRole(ROLE_CLEANSING);
		}
	}
}
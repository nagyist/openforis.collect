package org.openforis.collect.presenter {
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.collections.ListCollectionView;
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	import mx.events.FlexEvent;
	import mx.events.ValidationResultEvent;
	import mx.rpc.AsyncResponder;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	import mx.validators.Validator;
	
	import org.openforis.collect.client.ClientFactory;
	import org.openforis.collect.client.UserClient;
	import org.openforis.collect.event.UserManagementEvent;
	import org.openforis.collect.model.proxy.UserProxy;
	import org.openforis.collect.ui.component.user.UserManagementPopUp;
	import org.openforis.collect.ui.component.user.UserPerRoleContainer;
	import org.openforis.collect.ui.component.user.UsersListContainer;
	import org.openforis.collect.util.AlertUtil;
	import org.openforis.collect.util.StringUtil;
	
	import spark.events.GridSelectionEvent;
	import spark.events.IndexChangeEvent;
	
	/**
	 * 
	 * @author S. Ricci
	 * 
	 **/
	public class UserManagementPresenter extends PopUpPresenter {
		
		private var _userClient:UserClient;
		private var _loadedUsers:IList;
		private var _usersPerRoleMap:Array;
		
		public function UserManagementPresenter(view:UserManagementPopUp) {
			super(view);
			
			_userClient = ClientFactory.userClient;
			_usersPerRoleMap = new Array();
			initRoles();
			loadAll();
		}
		
		private function get view():UserManagementPopUp {
			return UserManagementPopUp(_view);
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			view.tabBar.addEventListener(IndexChangeEvent.CHANGE, tabBarIndexChangeHandler);
			view.usersListContainer.dataGrid.addEventListener(GridSelectionEvent.SELECTION_CHANGE, dataGridSelectionChangeHandler);
			view.usersListContainer.newUserButton.addEventListener(MouseEvent.CLICK, newUserButtonClickHandler);
			view.usersListContainer.userDetailsBox.addEventListener(FlexEvent.CREATION_COMPLETE, userDetailsBoxCreationCompleteHandler);
			
			view.userPerRoleContainer.rolesList.addEventListener(IndexChangeEvent.CHANGE, roleChangeHandler);
			
			eventDispatcher.addEventListener(UserManagementEvent.USER_PER_ROLE_SELECTED, userPerRoleSelectedHandler);
		}
		
		protected function userDetailsBoxCreationCompleteHandler(event:FlexEvent):void {
			view.usersListContainer.saveButton.addEventListener(MouseEvent.CLICK, saveButtonClickHandler);
			view.usersListContainer.deleteButton.addEventListener(MouseEvent.CLICK, deleteButtonClickHandler);
		}
		
		protected function tabBarIndexChangeHandler(event:IndexChangeEvent):void {
			loadAll();
		}
		
		protected function userPerRoleSelectedHandler(event:UserManagementEvent):void {
			var user:UserProxy = event.user;
			var role:String = event.role;
			if ( event.selected ) {
				user.addRole(role);
			} else {
				user.removeRole(role);
			}
			var responder:IResponder = new AsyncResponder(saveRolePerUserResultHandler, faultHandler);
			_userClient.save(responder, user);
		}
		
		protected function initRoles():void {
			view.userPerRoleContainer.rolesList.dataProvider = new ArrayCollection(UserProxy.ROLES);
		}
		
		protected function dataGridSelectionChangeHandler(event:GridSelectionEvent):void {
			var selectedUser:UserProxy = view.usersListContainer.dataGrid.selectedItem as UserProxy;
			if ( selectedUser == null ) {
				view.usersListContainer.currentState = UsersListContainer.STATE_DEFAULT;
			} else {
				view.usersListContainer.currentState = UsersListContainer.STATE_SELECTED;
				fillForm(selectedUser);
			}
		}
		
		protected function roleChangeHandler(event:IndexChangeEvent):void {
			var role:String = null;
			var newIndex:int = event.newIndex;
			if ( newIndex >= 0 ) {
				role = UserProxy.ROLES[newIndex];
				var selectableUsers:IList = new ArrayCollection();
				for each (var user:UserProxy in _loadedUsers) {
					var hasRole:Boolean = user.hasRole(role);
					var item:Object = {user: user, selected: hasRole};
					selectableUsers.addItem(item);
				}
				view.userPerRoleContainer.currentState = UserPerRoleContainer.STATE_ROLE_SELECTED;
				view.userPerRoleContainer.usersDataGroup.dataProvider = selectableUsers;
			} else {
				view.userPerRoleContainer.currentState = UserPerRoleContainer.STATE_DEFAULT;
			}
		}
		
		protected function resetForm():void {
			view.usersListContainer.enabledCheckBox.selected = true;
			view.usersListContainer.nameTextInput.text = "";
			view.usersListContainer.nameTextInput.errorString = "";
			view.usersListContainer.passwordTextInput.text = "";
			view.usersListContainer.passwordTextInput.errorString = "";
			view.usersListContainer.repeatPasswordTextInput.text = "";
			view.usersListContainer.repeatPasswordTextInput.errorString = "";
			resetRolesCheckBoxes();
		}
		
		protected function fillForm(user:UserProxy):void {
			view.usersListContainer.enabledCheckBox.selected = user.enabled;
			view.usersListContainer.nameTextInput.text = user.name;
			view.usersListContainer.passwordTextInput.text = "";
			resetRolesCheckBoxes();
			var roles:ListCollectionView = user.roles;
			for each (var role:String in roles) {
				switch ( role ) {
					case UserProxy.ROLE_ENTRY:
						view.usersListContainer.roleEntryCheckBox.selected = true;
						break;
					case UserProxy.ROLE_CLEANSING:
						view.usersListContainer.roleCleansingCheckBox.selected = true;
						break;
					case UserProxy.ROLE_ANALYSIS:
						view.usersListContainer.roleAnalysisCheckBox.selected = true;
						break;
					case UserProxy.ROLE_ADMIN:
						view.usersListContainer.roleAdminCheckBox.selected = true;
						break;
				}
			}
		}

		protected function getAllCheckBoxes():Array {
			var checkBoxes:Array = [
				view.usersListContainer.roleEntryCheckBox, 
				view.usersListContainer.roleCleansingCheckBox, 
				view.usersListContainer.roleAnalysisCheckBox, 
				view.usersListContainer.roleAdminCheckBox
			];
			return checkBoxes;
		}
		
		protected function resetRolesCheckBoxes():void {
			var checkBoxes:Array = getAllCheckBoxes();
			for each (var cb:CheckBox in checkBoxes) {
				 cb.selected = false;
			}
		}
		
		protected function getSelectedRoles():ListCollectionView {
			var roles:ListCollectionView = new ArrayCollection();
			var checkBoxes:Array = getAllCheckBoxes();
			for each (var checkBox:CheckBox in checkBoxes) {
				if ( checkBox.selected ) {
					var role:String = null;
					switch ( checkBox ) {
						case view.usersListContainer.roleEntryCheckBox:
							role = UserProxy.ROLE_ENTRY;
							break;
						case view.usersListContainer.roleCleansingCheckBox:
							role = UserProxy.ROLE_CLEANSING;
							break;
						case view.usersListContainer.roleAnalysisCheckBox:
							role = UserProxy.ROLE_ANALYSIS;
							break;
						case view.usersListContainer.roleAdminCheckBox:
							role = UserProxy.ROLE_ADMIN;
							break;
					}
					roles.addItem(role);
				}
			}
			return roles;
		}
		
		protected function validateForm():Boolean {
			//trim fields
			var name:String = view.usersListContainer.nameTextInput.text;
			name = StringUtil.trim(name);
			
			var result:ValidationResultEvent;
			var validators:Array = [view.usersListContainer.fNameV, view.usersListContainer.fPasswordV, view.usersListContainer.fRepeatedPasswordV];
			var failed:Boolean = false;
			for each (var validator:Validator in validators) {
				result = validator.validate();
				if (result.type==ValidationResultEvent.INVALID) {
					failed = true;
				}
			}
			return ! failed;
			/*
			view.usersListContainer.nameTextInput.text = name;
			if ( StringUtil.isBlank(name) ) {
				AlertUtil.showError('usersManagement.error.nameRequired');
				return false;
			}
			//TODO validate password against regexp
			var password:String = view.usersListContainer.passwordTextInput.text;
			if ( view.usersListContainer.currentState == UsersListContainer.STATE_NEW ) {
				if ( StringUtil.isBlank(password) ) {
					AlertUtil.showError('usersManagement.error.passwordRequired');
					return false;
				}
			}
			
			var repeatedPassword:String = view.usersListContainer.repeatPasswordTextInput.text;
			if ( password != repeatedPassword ) {
				AlertUtil.showError('usersManagement.error.repeatPasswordCorrectly');
				return false;
			}
			var roles:ListCollectionView = getSelectedRoles();
			if ( roles.length == 0 ) {
				AlertUtil.showError('usersManagement.error.noRolesSelected');
				return false;
			}
			*/
			return true;
		}
		
		protected function extractUserFromForm():UserProxy {
			var user:UserProxy = new UserProxy();
			var selectedUser:UserProxy = view.usersListContainer.dataGrid.selectedItem as UserProxy;
			if ( selectedUser != null ) {
				user.id = selectedUser.id;
			}
			user.enabled = view.usersListContainer.enabledCheckBox.selected;
			user.name = view.usersListContainer.nameTextInput.text;
			user.password = view.usersListContainer.passwordTextInput.text;
			var roles:ListCollectionView = getSelectedRoles();
			user.roles = roles;
			return user;
		}
		
		protected function loadAll():void {
			view.usersListContainer.currentState = UsersListContainer.STATE_LOADING;
			view.userPerRoleContainer.currentState = UserPerRoleContainer.STATE_LOADING;
			view.userPerRoleContainer.rolesList.selectedItem = null;
			
			var responder:IResponder = new AsyncResponder(loadAllResultHandler, faultHandler);
			_userClient.loadAll(responder);
		}		
		
		protected function loadAllResultHandler(event:ResultEvent, token:Object = null):void {
			view.usersListContainer.currentState = UsersListContainer.STATE_DEFAULT;
			view.userPerRoleContainer.currentState = UserPerRoleContainer.STATE_DEFAULT;
			
			_loadedUsers = event.result as IList;
			
			view.usersListContainer.dataGrid.dataProvider = _loadedUsers;
		}
		
		protected function newUserButtonClickHandler(event:MouseEvent):void {
			view.usersListContainer.currentState = UsersListContainer.STATE_NEW;
			resetForm();
		}
		
		protected function saveButtonClickHandler(event:MouseEvent):void {
			if ( validateForm() ) {
				var user:UserProxy = extractUserFromForm();
				var responder:IResponder = new AsyncResponder(saveUserResultHandler, faultHandler);
				_userClient.save(responder, user);
			} else {
				AlertUtil.showError("usersManagement.error.errorsInForm");
			}
		}
		
		protected function deleteButtonClickHandler(event:MouseEvent):void {
			var selectedUser:UserProxy = view.usersListContainer.dataGrid.selectedItem as UserProxy;
			if ( selectedUser != null ) {
				AlertUtil.showConfirm("usersManagement.delete.confirm", null, "global.confirm.delete", performDelete, [selectedUser.id]);
			} else {
				AlertUtil.showError("usersManagement.delete.selectUser");
			}
		}
		
		protected function performDelete(id:int):void {
			var responder:IResponder = new AsyncResponder(deleteUserResultHandler, faultHandler);
			_userClient.deleteUser(responder, id);
		}
		
		protected function saveUserResultHandler(event:ResultEvent, token:Object = null):void {
			var savedUser:UserProxy = event.result as UserProxy;
			var selectedUser:UserProxy = view.usersListContainer.dataGrid.selectedItem as UserProxy;
			if ( selectedUser != null ) {
				var selectedUserIndex:int = _loadedUsers.getItemIndex(selectedUser);
				_loadedUsers.setItemAt(savedUser, selectedUserIndex);
			} else {
				AlertUtil.showMessage("usersManagement.userSaved");
				loadAll();
			}
		}
		
		protected function saveRolePerUserResultHandler(event:ResultEvent, token:Object = null):void {
			var savedUser:UserProxy = event.result as UserProxy;
			
		}
		
		protected function deleteUserResultHandler(event:ResultEvent, token:Object = null):void {
			loadAll();
		}
	}
}
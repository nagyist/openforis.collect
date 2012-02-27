package org.openforis.collect.client {
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.remoting.Operation;
	
	import org.openforis.collect.remoting.service.UpdateRequest;
	
	/**
	 * 
	 * @author S. Ricci
	 * */
	public class DataClient extends AbstractClient {
		
		private var _updateQueueProcessor:RemoteCallQueueProcessor;

		private var _updateActiveRecordOperation:Operation;
		private var _saveActiveRecordOperation:Operation;
		private var _createRecordOperation:Operation;
		private var _deleteRecordOperation:Operation;
		private var _getRecordSummariesOperation:Operation;
		private var _loadRecordOperation:Operation;
		private var _promoteRecordOperation:Operation;
		private var _demoteRecordOperation:Operation;
		private var _clearActiveRecordOperation:Operation;
		private var _getCodeListItemsOperation:Operation;
		private var _findAssignableCodeListItemsOperation:Operation;
		
		public function DataClient() {
			super("dataService");
			
			this._updateQueueProcessor = new RemoteCallQueueProcessor(1);
			this._updateActiveRecordOperation = getOperation("updateActiveRecord");
			this._saveActiveRecordOperation = getOperation("saveActiveRecord");
			this._createRecordOperation = getOperation("createRecord");
			this._deleteRecordOperation = getOperation("deleteRecord");
			this._getRecordSummariesOperation = getOperation("getRecordSummaries");
			this._loadRecordOperation = getOperation("loadRecord");
			this._promoteRecordOperation = getOperation("promoteRecord");
			this._demoteRecordOperation = getOperation("demoteRecord");
			this._clearActiveRecordOperation = getOperation("clearActiveRecord");
			this._getCodeListItemsOperation = getOperation("getCodeListItems", CONCURRENCY_MULTIPLE);
			this._findAssignableCodeListItemsOperation = getOperation("findAssignableCodeListItems", CONCURRENCY_MULTIPLE);
		}
		
		public function createNewRecord(responder:IResponder, rootEntityName:String, versionName:String):void {
			var token:AsyncToken = this._createRecordOperation.send(rootEntityName, versionName);
			token.addResponder(responder);
		}
		
		public function saveActiveRecord(responder:IResponder):void {
			var token:AsyncToken = this._saveActiveRecordOperation.send();
			token.addResponder(responder);
		}
		
		public function deleteRecord(responder:IResponder, id:int):void {
			var token:AsyncToken = this._deleteRecordOperation.send(id);
			token.addResponder(responder);
		}
		
		public function getRecordSummaries(responder:IResponder, rootEntityName:String, offset:int, maxNumberOfRecords:int, orderByField:String=null, filter:String = null):void {
			var token:AsyncToken = this._getRecordSummariesOperation.send(rootEntityName, offset, maxNumberOfRecords, orderByField, filter);
			token.addResponder(responder);
		}
		
		public function loadRecord(responder:IResponder, id:int):void {
			var token:AsyncToken = this._loadRecordOperation.send(id);
			token.addResponder(responder);
		}
		
		public function clearActiveRecord(responder:IResponder):void {
			var token:AsyncToken = this._clearActiveRecordOperation.send();
			token.addResponder(responder);
		}
		
		public function updateActiveRecord(responder:IResponder, request:UpdateRequest):void {
			this._updateQueueProcessor.appendOperation(responder, this._updateActiveRecordOperation, request);
		}
		
		public function promoteRecord(responder:IResponder, id:int):void {
			var token:AsyncToken = this._promoteRecordOperation.send(id);
			token.addResponder(responder);
		}
		
		public function demoteRecord(responder:IResponder, id:int):void {
			var token:AsyncToken = this._demoteRecordOperation.send(id);
			token.addResponder(responder);
		}

		public function findAssignableCodeListItems(responder:IResponder, parentEntityId:int, attribute:String):void {
			var token:AsyncToken = this._findAssignableCodeListItemsOperation.send(parentEntityId, attribute);
			token.addResponder(responder);
		}
		
		public function getCodeListItems(responder:IResponder, parentEntityId:int, attribute:String, codes:Array):void {
			var token:AsyncToken = this._getCodeListItemsOperation.send(parentEntityId, attribute, codes);
			token.addResponder(responder);
		}
		
		protected function faultHandler(event:FaultEvent):void {
			Alert.show("Error\n\n" + event.fault.message);
		}
		
	}
}
Collect = function() {
	this.activeSurvey = null;
};

Collect.SURVEY_CHANGED = "surveyChanged";

Collect.prototype.init = function() {
	this.activeSurvey = null;
	this.sessionService = new Collect.SessionService();
	this.surveyService = new Collect.SurveyService();
	this.dataQueryService = new Collect.DataQueryService();
	this.dataErrorTypeService = new Collect.DataErrorTypeService();
	this.dataErrorQueryService = new Collect.DataErrorQueryService();
	this.dataErrorReportService = new Collect.DataErrorReportService();
	this.geoDataService = new Collect.GeoDataService();
	
	this.dataQueryTestResultDataGrid = null;
	
	this.initDataErrorTypePanel();
	this.initDataErrorQueryPanel();
	this.initDataErrorReportsPanel();
	this.mapPanelComposer = new Collect.DataCleansing.MapPanelComposer($("#map-panel"));
	
	this.initGlobalEventHandlers();
	
	this.checkActiveSurveySelected();
};

Collect.prototype.checkActiveSurveySelected = function() {
	var $this = this;
	var openSurveySelectDialog = function() {
		var surveySelectDialogController = new Collect.SurveySelectDialogController();
		surveySelectDialogController.open(null, true);
	};
	this.sessionService.getActiveSurvey(function(survey) {
		if (survey == null) {
			openSurveySelectDialog();
		} else {
			$this.activeSurvey = new Collect.Metamodel.Survey(survey);
			EventBus.dispatch(Collect.SURVEY_CHANGED, $this);
		}
	}, function() {
		openSurveySelectDialog();
	});
};

Collect.prototype.initGlobalEventHandlers = function() {
	var $this = this;
	$("#home-survey-selector-button").click(function() {
		new Collect.SurveySelectDialogController().open();
	});
	EventBus.addEventListener(Collect.SURVEY_CHANGED, function() {
		$this.initDataErrorTypeGrid();
		$this.initDataErrorQueryGrid();
		$this.initDataErrorReportGrid();
		$this.initDataQueryPanel();
		$this.initMapPanel();
		$("#home-survey-selector-button").text($this.activeSurvey.name);
	});
	EventBus.addEventListener(Collect.DataErrorTypeDialogController.DATA_ERROR_TYPE_SAVED, function() {
		$this.dataErrorTypeDataGrid.refresh();
	});
	EventBus.addEventListener(Collect.DataErrorTypeDialogController.DATA_ERROR_TYPE_DELETED, function() {
		$this.dataErrorTypeDataGrid.refresh();
	});
	EventBus.addEventListener(Collect.DataErrorQueryDialogController.DATA_ERROR_QUERY_SAVED, function() {
		$this.dataErrorQueryDataGrid.refresh();
	});
	EventBus.addEventListener(Collect.DataErrorQueryDialogController.DATA_ERROR_QUERY_DELETED, function() {
		$this.dataErrorQueryDataGrid.refresh();
	});
	EventBus.addEventListener(Collect.DataErrorReportDialogController.DATA_ERROR_REPORT_CREATED, function() {
		$this.dataErrorReportDataGrid.refresh();
	});
	EventBus.addEventListener(Collect.DataErrorReportDialogController.DATA_ERROR_REPORT_DELETED, function() {
		$this.dataErrorReportDataGrid.refresh();
	});
};

Collect.prototype.initDataQueryPanel = function() {
	var $this = this;
	var container = $("#data-query-panel");
	var form = container.find("form");
	var formController = new Collect.DataQueryFormController(form);
	formController.init(function() {
		container.find(".test-btn").click($.proxy(function() {
			var query = formController.extractJSONItem();
			$this.dataQueryService.startTest(query, function() {
				var jobDialog = new OF.UI.JobDialog();
				new OF.JobMonitor("datacleansing/dataquery/test-job.json", function() {
					jobDialog.close();
					$this.dataQueryTestResultDataGrid.refresh();
					$this.dataQueryTestResultDataGrid.$container.show();
				});
			});
		}, $this));
		container.find(".export-btn").click($.proxy(function() {
			var query = formController.extractJSONItem();
			$this.dataQueryService.startExport(query, function() {
				var jobDialog = new OF.UI.JobDialog();
				new OF.JobMonitor("datacleansing/dataquery/export-job.json", function() {
					jobDialog.close();
					$this.dataQueryService.downloadResult();
				});
			});
		}, $this));
	});
	
	var testResultGridContainer = container.find(".test-result-grid");
	testResultGridContainer.bootstrapTable({
	    url: "datacleansing/dataquery/test-result.json",
	    cache: false,
	    clickToSelect: true,
	    height: 200,
	    columns: [
			{field: "key1", title: "Key1"},
			{field: "key2", title: "Key2"},
			{field: "key3", title: "Key3"},
			{field: "nodePath", title: "Path"},
			{field: "attributeValue", title: "Value"}
		]
	});
	$this.dataQueryTestResultDataGrid = testResultGridContainer.data('bootstrap.table');
	$this.dataQueryTestResultDataGrid.$container.hide();
};

Collect.prototype.initDataErrorReportsPanel = function() {
	var $this = this;

	$('#new-data-error-report-btn').click($.proxy(function() {
		var dialogController = new Collect.DataErrorReportDialogController();
		dialogController.open();
	}, this));
	
	$('#view-data-error-report-btn').click($.proxy(function() {
		var dialogController = new Collect.DataErrorReportViewDialogController();
		dialogController.open($.proxy(getSelectedItem, this)());
	}, this));
	
	$('#delete-data-error-report-btn').click($.proxy(function() {
		var $this = this;
		var selectedItem = $.proxy(getSelectedItem, $this)();
		if (selectedItem == null) {
			return;
		}
		OF.UI.confirm("Do you want to delete this Data Error Report?", function() {
			collect.dataErrorReportService.remove(selectedItem.id, function() {
				EventBus.dispatch(Collect.DataErrorReportDialogController.DATA_ERROR_REPORT_DELETED, $this);
			});
		});
	}, this));
	
	function getSelectedItem() {
		var selections = $this.dataErrorReportDataGrid.getSelections();
		return selections.length == 0 ? null : selections[0];
	}
};

Collect.prototype.initDataErrorTypePanel = function() {
	$('#newDataErrorTypeBtn').click($.proxy(function() {
		var dialogController = new Collect.DataErrorTypeDialogController();
		dialogController.open();
	}, this));
	
	$('#editDataErrorTypeBtn').click($.proxy(function() {
		var $this = this;
		var selectedItem = $.proxy(getSelectedItem, $this)();
		if (selectedItem == null) {
			return;
		}
		var dialogController = new Collect.DataErrorTypeDialogController();
		dialogController.open(selectedItem);
	}, this));
	
	$('#deleteDataErrorTypeBtn').click($.proxy(function() {
		var $this = this;
		var selectedItem = $.proxy(getSelectedItem, $this)();
		if (selectedItem == null) {
			return;
		}
		OF.UI.confirm("Do you want to delete this Data Error Type?", function() {
			collect.dataErrorTypeService.remove(selectedItem.id, function() {
				EventBus.dispatch(Collect.DataErrorTypeDialogController.DATA_ERROR_TYPE_DELETED, $this);
			});
		});
	}, this));
	
	function getSelectedItem() {
		var $this = this;
		var selections = $this.dataErrorTypeDataGrid.getSelections();
		return selections.length == 0 ? null : selections[0];
	}
};

Collect.prototype.initDataErrorQueryPanel = function() {
	$('#new-data-error-query-btn').click($.proxy(function() {
		var dialogController = new Collect.DataErrorQueryDialogController();
		dialogController.open();
	}, this));
	
	$('#edit-data-error-query-btn').click($.proxy(function() {
		var $this = this;
		var selectedItem = $.proxy(getSelectedItem, $this)();
		if (selectedItem == null) {
			return;
		}
		var dialogController = new Collect.DataErrorQueryDialogController();
		dialogController.open(selectedItem);
	}, this));
	
	$('#delete-data-error-query-btn').click($.proxy(function() {
		var $this = this;
		var selectedItem = $.proxy(getSelectedItem, $this)();
		if (selectedItem == null) {
			return;
		}
		OF.UI.confirm("Do you want to delete this Data Error Query?", function() {
			collect.dataErrorQueryService.remove(selectedItem.id, function() {
				EventBus.dispatch(Collect.DataErrorQueryDialogController.DATA_ERROR_QUERY_DELETED, $this);
			});
		});
	}, this));
	
	function getSelectedItem() {
		var $this = this;
		var selections = $this.dataErrorQueryDataGrid.getSelections();
		return selections.length == 0 ? null : selections[0];
	}
};

Collect.prototype.initMapPanel = function() {
	this.mapPanelComposer.init();
};

Collect.prototype.initDataErrorTypeGrid = function() {
	var $this = this;
	$('#dataerrortypegrid').bootstrapTable({
	    url: "datacleansing/dataerrortypes/list.json",
	    cache: false,
	    clickToSelect: true,
	    columns: [
          	{field: "selected", title: "", radio: true},
			{field: "id", title: "Id", visible: false},
			{field: "code", title: "Code"},
			{field: "label", title: "Label"},
			{field: "description", title: "Description"}
		]
	});
	$this.dataErrorTypeDataGrid = $('#dataerrortypegrid').data('bootstrap.table');
};

Collect.prototype.initDataErrorQueryGrid = function() {
	var $this = this;
	$('#dataerrorquerygrid').bootstrapTable({
	    url: "datacleansing/dataerrorqueries/list.json",
	    cache: false,
	    clickToSelect: true,
	    columns: [
          	{field: "selected", title: "", radio: true},
			{field: "id", title: "Id", visible: false},
			{field: "typeCode", title: "Error Type"},
			{field: "title", title: "Title"},
			{field: "description", title: "Description"}
		]
	});
	$this.dataErrorQueryDataGrid = $('#dataerrorquerygrid').data('bootstrap.table');
};

Collect.prototype.initDataErrorReportGrid = function() {
	var $this = this;
	var el = $('#data-error-report-grid');
	el.bootstrapTable({
	    url: "datacleansing/dataerrorreports/list.json",
	    cache: false,
	    clickToSelect: true,
	    columns: [
          	{field: "selected", title: "", radio: true},
			{field: "id", title: "Id", visible: false},
			{field: "queryTitle", title: "Query"},
			{field: "typeCode", title: "Error Type"},
			{field: "creationDate", title: "Date"}
		]
	});
	$this.dataErrorReportDataGrid = el.data('bootstrap.table');
};

Collect.prototype.setActiveSurvey = function(surveySummary) {
	var $this = this;
	$this.surveyService.loadById(surveySummary.id, function(survey) {
		$this.activeSurvey = new Collect.Metamodel.Survey(survey);
		EventBus.dispatch(Collect.SURVEY_CHANGED, $this);
	});
};

Collect.prototype.error = function(jqXHR, status, errorThrown) {
	alert(status);
};

$(function() {
	collect = new Collect();
	collect.init();
});
Collect.DataCleansing = function() {
	
};

Collect.DataCleansing.WORKFLOW_STEPS = [{name: "ENTRY", label: "Data Entry"}, 
	                                    {name: "CLEANSING", label: "Data Cleansing"},
	                                    {name: "ANALYSIS", label: "Data Analysis"}
	                                    ];

Collect.DataCleansing.DATA_ERROR_TYPE_SAVED = "dataErrorTypeSaved";
Collect.DataCleansing.DATA_ERROR_TYPE_DELETED = "dataErrorTypeSaved";
Collect.DataCleansing.DATA_ERROR_QUERY_SAVED = "dataErrorQuerySaved";
Collect.DataCleansing.DATA_ERROR_QUERY_DELETED = "dataErrorQueryDeleted";
Collect.DataCleansing.DATA_ERROR_QUERY_GROUP_SAVED = "dataErrorQueryGroupSaved";
Collect.DataCleansing.DATA_ERROR_QUERY_GROUP_DELETED = "dataErrorQueryGroupDeleted";
Collect.DataCleansing.DATA_ERROR_REPORT_CREATED = "dataErrorReportCreated";
Collect.DataCleansing.DATA_ERROR_REPORT_DELETED = "dataErrorReportDeleted";
Collect.DataCleansing.DATA_QUERY_SAVED = "dataQuerySaved";
Collect.DataCleansing.DATA_QUERY_DELETED = "dataQueryDeleted";
Collect.DataCleansing.DATA_CLEANSING_STEP_SAVED = "dataCleansingStepSaved";
Collect.DataCleansing.DATA_CLEANSING_STEP_DELETED = "dataCleansingStepDeleted";
Collect.DataCleansing.DATA_CLEANSING_CHAIN_SAVED = "dataCleansingChainSaved";
Collect.DataCleansing.DATA_CLEANSING_CHAIN_DELETED = "dataCleansingChainDeleted";

Collect.DataCleansing.VIEW_STATE_NO_SURVEY_SELECTED = "noSurveySelected";
Collect.DataCleansing.VIEW_STATE_SURVEY_SELECTED = "surveySelected";
Collect.DataCleansing.VIEW_STATE_TEMPORARY_SURVEY_SELECTED = "temporarySurveySelected";

Collect.DataCleansing.prototype.init = function() {
	this.initDataErrorTypePanel();
	this.initDataQueryPanel();
	this.initDataErrorQueryPanel();
	this.initDataErrorQueryGroupPanel();
	this.initDataCleansingStepPanel();
	this.initDataCleansingChainPanel();
	this.initDataErrorReportPanel();
	
	//this.mapPanelComposer = new Collect.DataCleansing.MapPanelComposer($("#map-panel"));
	
	this.initGlobalEventHandlers();
	
	this.initView();
};

Collect.DataCleansing.prototype.initView = function() {
	this.checkViewState();
};

Collect.DataCleansing.prototype.checkViewState = function() {
	var state = null;
	if (collect.activeSurvey == null) {
		state = Collect.DataCleansing.VIEW_STATE_NO_SURVEY_SELECTED;
	} else if (collect.activeSurvey.temporary) {
		state = Collect.DataCleansing.VIEW_STATE_TEMPORARY_SURVEY_SELECTED;
	} else {
		state = Collect.DataCleansing.VIEW_STATE_SURVEY_SELECTED;
	}
	this.changeViewState(state);
};

Collect.DataCleansing.prototype.changeViewState = function(state) {
	switch (state) {
	case Collect.DataCleansing.VIEW_STATE_NO_SURVEY_SELECTED:
		$("#no-survey-selected-container").show();
		$("#survey-selected-container").hide();
		break;
	case Collect.DataCleansing.VIEW_STATE_SURVEY_SELECTED:
		$("#no-survey-selected-container").hide();
		$("#survey-selected-container").show();
		$("#data-error-report-tab").show();
		break;
	case Collect.DataCleansing.VIEW_STATE_TEMPORARY_SURVEY_SELECTED:
		$("#no-survey-selected-container").hide();
		$("#survey-selected-container").show();
		$("#data-error-report-tab").hide();
		break;
	};
};

Collect.DataCleansing.prototype.initGlobalEventHandlers = function() {
	var $this = this;
	$("#home-survey-selector-button").click(function() {
		new Collect.SurveySelectDialogController().open();
	});
	EventBus.addEventListener(Collect.SURVEY_CHANGED, function() {
		$this.dataErrorTypePanel.refreshDataGrid();
		$this.dataQueryPanel.refreshDataGrid();
		$this.dataErrorQueryPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorReportPanel.refreshDataGrid();
		$this.dataCleansingStepPanel.refreshDataGrid();
		$this.dataCleansingChainPanel.refreshDataGrid();
		$("#selected-survey-label").text(Collect.SurveySelectDialogController.getPrettyShortLabel(collect.activeSurvey));
		//$this.initMapPanel();
		
		$this.checkViewState();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_TYPE_SAVED, function() {
		$this.dataErrorTypePanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_TYPE_DELETED, function() {
		$this.dataErrorTypePanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_QUERY_SAVED, function() {
		$this.dataErrorQueryPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_QUERY_DELETED, function() {
		$this.dataErrorQueryPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_QUERY_GROUP_SAVED, function() {
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorReportPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_QUERY_GROUP_DELETED, function() {
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorReportPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_REPORT_CREATED, function() {
		$this.dataErrorReportPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_ERROR_REPORT_DELETED, function() {
		$this.dataErrorReportPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_QUERY_SAVED, function() {
		$this.dataQueryPanel.refreshDataGrid();
		$this.dataErrorQueryPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorReportPanel.refreshDataGrid();
		$this.dataCleansingStepPanel.refreshDataGrid();
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_QUERY_DELETED, function() {
		$this.dataQueryPanel.refreshDataGrid();
		$this.dataErrorQueryPanel.refreshDataGrid();
		$this.dataErrorQueryGroupPanel.refreshDataGrid();
		$this.dataErrorReportPanel.refreshDataGrid();
		$this.dataCleansingStepPanel.refreshDataGrid();
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_CLEANSING_STEP_SAVED, function() {
		$this.dataCleansingStepPanel.refreshDataGrid();
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_CLEANSING_STEP_DELETED, function() {
		$this.dataCleansingStepPanel.refreshDataGrid();
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_CLEANSING_CHAIN_SAVED, function() {
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
	EventBus.addEventListener(Collect.DataCleansing.DATA_CLEANSING_CHAIN_DELETED, function() {
		$this.dataCleansingChainPanel.refreshDataGrid();
	});
};

Collect.DataCleansing.prototype.initDataErrorTypePanel = function() {
	this.dataErrorTypePanel = new Collect.DataCleansing.DataErrorTypePanelController($("#data-error-type-panel"));
	this.dataErrorTypePanel.init();
};

Collect.DataCleansing.prototype.initDataQueryPanel = function() {
	this.dataQueryPanel = new Collect.DataCleansing.DataQueryPanelController($("#data-query-panel"));
	this.dataQueryPanel.init();
};

Collect.DataCleansing.prototype.initDataErrorQueryPanel = function() {
	this.dataErrorQueryPanel = new Collect.DataCleansing.DataErrorQueryPanelController($("#data-error-query-panel"));
	this.dataErrorQueryPanel.init();
};

Collect.DataCleansing.prototype.initDataErrorQueryGroupPanel = function() {
	this.dataErrorQueryGroupPanel = new Collect.DataCleansing.DataErrorQueryGroupPanelController($("#data-error-query-group-panel"));
	this.dataErrorQueryGroupPanel.init();
};

Collect.DataCleansing.prototype.initDataErrorReportPanel = function() {
	this.dataErrorReportPanel = new Collect.DataCleansing.DataErrorReportPanelController($("#data-error-report-panel"));
	this.dataErrorReportPanel.init();
};

Collect.DataCleansing.prototype.initDataCleansingStepPanel = function() {
	this.dataCleansingStepPanel = new Collect.DataCleansing.DataCleansingStepPanelController($("#data-cleansing-step-panel"));
	this.dataCleansingStepPanel.init();
};

Collect.DataCleansing.prototype.initDataCleansingChainPanel = function() {
	this.dataCleansingChainPanel = new Collect.DataCleansing.DataCleansingChainPanelController($("#data-cleansing-chain-panel"));
	this.dataCleansingChainPanel.init();
};

Collect.DataCleansing.prototype.initMapPanel = function() {
	this.mapPanelComposer.init();
};
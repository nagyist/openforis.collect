package org.openforis.collect.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.concurrency.CollectJobManager;
import org.openforis.collect.io.BackupFileExtractor;
import org.openforis.collect.io.SurveyBackupInfo;
import org.openforis.collect.io.data.DataRestoreJob;
import org.openforis.collect.manager.ConfigurationManager;
import org.openforis.collect.manager.SurveyManager;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.model.Configuration.ConfigurationItem;
import org.openforis.collect.web.controller.upload.UploadItem;
import org.openforis.commons.web.JobStatusResponse;
import org.openforis.concurrency.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @author S. Ricci
 *
 */
@Controller
public class DataRestoreController extends BasicController {

	//private static Log LOG = LogFactory.getLog(RestoreController.class);
	
	@Autowired
	private SurveyManager surveyManager;
	@Autowired
	private ConfigurationManager configurationManager;
	@Autowired
	private CollectJobManager jobManager;
	
	@RequestMapping(value = "/surveys/{surveyName}/data/restore.json", method = RequestMethod.POST)
	public @ResponseBody JobStatusResponse restoreData(UploadItem uploadItem, @PathVariable String surveyName) throws IOException {
		try {
			DataRestoreJob job = startRestoreJob(uploadItem, surveyName);
			return createResponse(job);
		} catch (Exception e) {
			JobStatusResponse response = new JobStatusResponse();
			response.setErrorStatus();
			response.setErrorMessage(e.getMessage());
			return response;
		}
	}
	
	@RequestMapping(value = "/surveys/{surveyName}/data/restore-remotely.json", method = RequestMethod.POST)
	public @ResponseBody RemoteDataRestoreResponse restoreDataRemotely(UploadItem uploadItem, @PathVariable String surveyName, @RequestParam String restoreKey) {
		RemoteDataRestoreResponse response = new RemoteDataRestoreResponse();
		String allowedRestoreKey = configurationManager.getConfiguration().get(ConfigurationItem.ALLOWED_RESTORE_KEY);
		if (StringUtils.isBlank(allowedRestoreKey) || allowedRestoreKey.equals(restoreKey)) {
			try {
				DataRestoreJob job = startRestoreJob(uploadItem, surveyName);
				response.setJobId(job.getId().toString());
			} catch (Exception e) {
				response.setErrorStatus();
				response.setErrorMessage(e.getMessage());
			}
		} else {
			response.setErrorStatus();
			response.setErrorMessage("Restore not allowed: invalid restore key");
		}
		return response;
	}
	
	@RequestMapping(value = "/surveys/data/restore/jobs/{jobId}/status.json", method = RequestMethod.GET)
	public @ResponseBody RemoteDataRestoreResponse getRestoreDataRemotelyStatus(@PathVariable String jobId) throws IOException {
		RemoteDataRestoreResponse response;
		Job job = jobManager.getJob(jobId);
		if (job == null || ! (job instanceof DataRestoreJob)) {
			response = new RemoteDataRestoreResponse();
			response.setErrorStatus();
			response.setErrorMessage("Job not found");
		} else {
			response = createRemoteDataRestoreResponse(job);
		}
		return response;
	}

	@RequestMapping(value = "/surveys/data/restore/jobs/{jobId}/abort.json", method = RequestMethod.GET)
	public @ResponseBody RemoteDataRestoreResponse abortRestoreDataRemotelyJob(@PathVariable String jobId) throws IOException {
		RemoteDataRestoreResponse response;
		Job job = jobManager.getJob(jobId);
		if (job == null || ! (job instanceof DataRestoreJob)) {
			response = new RemoteDataRestoreResponse();
			response.setErrorStatus();
			response.setErrorMessage("Job not found");
		} else {
			job.abort();
			response = createRemoteDataRestoreResponse(job);
		}
		return response;
	}
	
	private JobStatusResponse createResponse(Job job) {
		JobStatusResponse response = new JobStatusResponse();
		fillResponse(response, job);
		return response;
	}
	
	private RemoteDataRestoreResponse createRemoteDataRestoreResponse(Job job) {
		RemoteDataRestoreResponse response = new RemoteDataRestoreResponse();
		fillResponse(response, job);
		return response;
	}

	private void fillResponse(JobStatusResponse response, Job job) {
		response.setJobId(job.getId().toString());
		response.setJobStatus(job.getStatus());
		response.setJobProgress(job.getProgressPercent());
		response.setErrorMessage(job.getErrorMessage());
	}
	
	private DataRestoreJob startRestoreJob(UploadItem uploadItem, String expectedSurveyName) throws IOException,
	FileNotFoundException, ZipException {
		File tempFile = copyContentToFile(uploadItem);
		
		String surveyUri = extractSurveyUri(tempFile);
		checkValidSurvey(expectedSurveyName, surveyUri);
		
		DataRestoreJob job = jobManager.createJob(DataRestoreJob.class);
		job.setStoreRestoredFile(true);
		job.setSurveyUri(surveyUri);
		job.setFile(tempFile);
		job.setOverwriteAll(true);
		job.setRestoreUploadedFiles(true);
		
		String lockId = surveyUri;
		jobManager.start(job, lockId);
		
		return job;
	}

	private void checkValidSurvey(String surveyName, String surveyUri) {
		CollectSurvey expectedSurvey = surveyManager.get(surveyName);
		String expectedSurveyUri = expectedSurvey.getUri();
		if (! surveyUri.equals(expectedSurveyUri)) {
			throw new IllegalArgumentException("The backup file is not related to the specified survey");
		}
	}
	
	private String extractSurveyUri(File tempFile) throws ZipException,
			IOException, FileNotFoundException {
		BackupFileExtractor backupFileExtractor = new BackupFileExtractor(tempFile);
		File infoFile = backupFileExtractor.extractInfoFile();
		SurveyBackupInfo backupInfo = SurveyBackupInfo.parse(new FileInputStream(infoFile));
		String surveyUri = backupInfo.getSurveyUri();
		return surveyUri;
	}
	
	private File copyContentToFile(UploadItem uploadItem) throws IOException,
			FileNotFoundException {
		CommonsMultipartFile fileData = uploadItem.getFileData();
		InputStream is = fileData.getInputStream();
		File tempFile = File.createTempFile("collect-upload-item", ".temp");
		FileOutputStream os = new FileOutputStream(tempFile);
		IOUtils.copy(is, os);
		return tempFile;
	}
	
	public static class RemoteDataRestoreResponse extends JobStatusResponse {
		
	}
	
}

package org.openforis.collect.model;

import java.util.Date;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.Record;

/**
 * @author G. Miceli
 */
public class CollectRecord extends Record {

	public enum Step {
		ENTRY(1), CLEANSING(2), ANALYSIS(3);
		
		private int stepNumber;
		
		private Step(int stepNumber) {
			this.stepNumber = stepNumber;
		}
		
		public int getStepNumber() {
			return stepNumber;
		}
	}

	private Step step;
	// TODO Replace submitted flag with state enum
	private boolean submitted;
	private Date creationDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

	public CollectRecord(Survey survey, String rootEntity, String versionName) {
		super(survey, rootEntity, versionName);
		this.step = Step.ENTRY;
		this.submitted = false;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public boolean isSubmitted() {
		return submitted;
	}
	
	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}


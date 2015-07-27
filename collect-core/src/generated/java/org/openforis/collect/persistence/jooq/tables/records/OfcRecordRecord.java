/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.4"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcRecordRecord extends org.jooq.impl.UpdatableRecordImpl<org.openforis.collect.persistence.jooq.tables.records.OfcRecordRecord> {

	private static final long serialVersionUID = -1004057223;

	/**
	 * Setter for <code>collect.ofc_record.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>collect.ofc_record.survey_id</code>.
	 */
	public void setSurveyId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.survey_id</code>.
	 */
	public java.lang.Integer getSurveyId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>collect.ofc_record.root_entity_definition_id</code>.
	 */
	public void setRootEntityDefinitionId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.root_entity_definition_id</code>.
	 */
	public java.lang.Integer getRootEntityDefinitionId() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>collect.ofc_record.date_created</code>.
	 */
	public void setDateCreated(java.sql.Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.date_created</code>.
	 */
	public java.sql.Timestamp getDateCreated() {
		return (java.sql.Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>collect.ofc_record.created_by_id</code>.
	 */
	public void setCreatedById(java.lang.Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.created_by_id</code>.
	 */
	public java.lang.Integer getCreatedById() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>collect.ofc_record.date_modified</code>.
	 */
	public void setDateModified(java.sql.Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.date_modified</code>.
	 */
	public java.sql.Timestamp getDateModified() {
		return (java.sql.Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>collect.ofc_record.modified_by_id</code>.
	 */
	public void setModifiedById(java.lang.Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.modified_by_id</code>.
	 */
	public java.lang.Integer getModifiedById() {
		return (java.lang.Integer) getValue(6);
	}

	/**
	 * Setter for <code>collect.ofc_record.model_version</code>.
	 */
	public void setModelVersion(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.model_version</code>.
	 */
	public java.lang.String getModelVersion() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>collect.ofc_record.step</code>.
	 */
	public void setStep(java.lang.Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.step</code>.
	 */
	public java.lang.Integer getStep() {
		return (java.lang.Integer) getValue(8);
	}

	/**
	 * Setter for <code>collect.ofc_record.state</code>.
	 */
	public void setState(java.lang.String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.state</code>.
	 */
	public java.lang.String getState() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>collect.ofc_record.skipped</code>.
	 */
	public void setSkipped(java.lang.Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.skipped</code>.
	 */
	public java.lang.Integer getSkipped() {
		return (java.lang.Integer) getValue(10);
	}

	/**
	 * Setter for <code>collect.ofc_record.missing</code>.
	 */
	public void setMissing(java.lang.Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.missing</code>.
	 */
	public java.lang.Integer getMissing() {
		return (java.lang.Integer) getValue(11);
	}

	/**
	 * Setter for <code>collect.ofc_record.errors</code>.
	 */
	public void setErrors(java.lang.Integer value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.errors</code>.
	 */
	public java.lang.Integer getErrors() {
		return (java.lang.Integer) getValue(12);
	}

	/**
	 * Setter for <code>collect.ofc_record.warnings</code>.
	 */
	public void setWarnings(java.lang.Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.warnings</code>.
	 */
	public java.lang.Integer getWarnings() {
		return (java.lang.Integer) getValue(13);
	}

	/**
	 * Setter for <code>collect.ofc_record.key1</code>.
	 */
	public void setKey1(java.lang.String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.key1</code>.
	 */
	public java.lang.String getKey1() {
		return (java.lang.String) getValue(14);
	}

	/**
	 * Setter for <code>collect.ofc_record.key2</code>.
	 */
	public void setKey2(java.lang.String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.key2</code>.
	 */
	public java.lang.String getKey2() {
		return (java.lang.String) getValue(15);
	}

	/**
	 * Setter for <code>collect.ofc_record.key3</code>.
	 */
	public void setKey3(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.key3</code>.
	 */
	public java.lang.String getKey3() {
		return (java.lang.String) getValue(16);
	}

	/**
	 * Setter for <code>collect.ofc_record.count1</code>.
	 */
	public void setCount1(java.lang.Integer value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.count1</code>.
	 */
	public java.lang.Integer getCount1() {
		return (java.lang.Integer) getValue(17);
	}

	/**
	 * Setter for <code>collect.ofc_record.count2</code>.
	 */
	public void setCount2(java.lang.Integer value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.count2</code>.
	 */
	public java.lang.Integer getCount2() {
		return (java.lang.Integer) getValue(18);
	}

	/**
	 * Setter for <code>collect.ofc_record.count3</code>.
	 */
	public void setCount3(java.lang.Integer value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.count3</code>.
	 */
	public java.lang.Integer getCount3() {
		return (java.lang.Integer) getValue(19);
	}

	/**
	 * Setter for <code>collect.ofc_record.count4</code>.
	 */
	public void setCount4(java.lang.Integer value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.count4</code>.
	 */
	public java.lang.Integer getCount4() {
		return (java.lang.Integer) getValue(20);
	}

	/**
	 * Setter for <code>collect.ofc_record.count5</code>.
	 */
	public void setCount5(java.lang.Integer value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.count5</code>.
	 */
	public java.lang.Integer getCount5() {
		return (java.lang.Integer) getValue(21);
	}

	/**
	 * Setter for <code>collect.ofc_record.data1</code>.
	 */
	public void setData1(byte[] value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.data1</code>.
	 */
	public byte[] getData1() {
		return (byte[]) getValue(22);
	}

	/**
	 * Setter for <code>collect.ofc_record.data2</code>.
	 */
	public void setData2(byte[] value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.data2</code>.
	 */
	public byte[] getData2() {
		return (byte[]) getValue(23);
	}

	/**
	 * Setter for <code>collect.ofc_record.owner_id</code>.
	 */
	public void setOwnerId(java.lang.Integer value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>collect.ofc_record.owner_id</code>.
	 */
	public java.lang.Integer getOwnerId() {
		return (java.lang.Integer) getValue(24);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OfcRecordRecord
	 */
	public OfcRecordRecord() {
		super(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD);
	}

	/**
	 * Create a detached, initialised OfcRecordRecord
	 */
	public OfcRecordRecord(java.lang.Integer id, java.lang.Integer surveyId, java.lang.Integer rootEntityDefinitionId, java.sql.Timestamp dateCreated, java.lang.Integer createdById, java.sql.Timestamp dateModified, java.lang.Integer modifiedById, java.lang.String modelVersion, java.lang.Integer step, java.lang.String state, java.lang.Integer skipped, java.lang.Integer missing, java.lang.Integer errors, java.lang.Integer warnings, java.lang.String key1, java.lang.String key2, java.lang.String key3, java.lang.Integer count1, java.lang.Integer count2, java.lang.Integer count3, java.lang.Integer count4, java.lang.Integer count5, byte[] data1, byte[] data2, java.lang.Integer ownerId) {
		super(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD);

		setValue(0, id);
		setValue(1, surveyId);
		setValue(2, rootEntityDefinitionId);
		setValue(3, dateCreated);
		setValue(4, createdById);
		setValue(5, dateModified);
		setValue(6, modifiedById);
		setValue(7, modelVersion);
		setValue(8, step);
		setValue(9, state);
		setValue(10, skipped);
		setValue(11, missing);
		setValue(12, errors);
		setValue(13, warnings);
		setValue(14, key1);
		setValue(15, key2);
		setValue(16, key3);
		setValue(17, count1);
		setValue(18, count2);
		setValue(19, count3);
		setValue(20, count4);
		setValue(21, count5);
		setValue(22, data1);
		setValue(23, data2);
		setValue(24, ownerId);
	}
}

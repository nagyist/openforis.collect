/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.openforis.collect.persistence.jooq.Collect;
import org.openforis.collect.persistence.jooq.Keys;
import org.openforis.collect.persistence.jooq.tables.records.OfcDataCleansingStepRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcDataCleansingStep extends TableImpl<OfcDataCleansingStepRecord> {

	private static final long serialVersionUID = 131886009;

	/**
	 * The reference instance of <code>collect.ofc_data_cleansing_step</code>
	 */
	public static final OfcDataCleansingStep OFC_DATA_CLEANSING_STEP = new OfcDataCleansingStep();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<OfcDataCleansingStepRecord> getRecordType() {
		return OfcDataCleansingStepRecord.class;
	}

	/**
	 * The column <code>collect.ofc_data_cleansing_step.id</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.uuid</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, String> UUID = createField("uuid", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.query_id</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, Integer> QUERY_ID = createField("query_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.title</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.description</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.creation_date</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, Timestamp> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.modified_date</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, Timestamp> MODIFIED_DATE = createField("modified_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>collect.ofc_data_cleansing_step.type</code>.
	 */
	public final TableField<OfcDataCleansingStepRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.CHAR.length(1).defaulted(true), this, "");

	/**
	 * Create a <code>collect.ofc_data_cleansing_step</code> table reference
	 */
	public OfcDataCleansingStep() {
		this("ofc_data_cleansing_step", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_data_cleansing_step</code> table reference
	 */
	public OfcDataCleansingStep(String alias) {
		this(alias, OFC_DATA_CLEANSING_STEP);
	}

	private OfcDataCleansingStep(String alias, Table<OfcDataCleansingStepRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcDataCleansingStep(String alias, Table<OfcDataCleansingStepRecord> aliased, Field<?>[] parameters) {
		super(alias, Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<OfcDataCleansingStepRecord> getPrimaryKey() {
		return Keys.OFC_DATA_CLEANSING_STEP_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<OfcDataCleansingStepRecord>> getKeys() {
		return Arrays.<UniqueKey<OfcDataCleansingStepRecord>>asList(Keys.OFC_DATA_CLEANSING_STEP_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<OfcDataCleansingStepRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<OfcDataCleansingStepRecord, ?>>asList(Keys.OFC_DATA_CLEANSING_STEP__OFC_DATA_CLEANSING_STEP_QUERY_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcDataCleansingStep as(String alias) {
		return new OfcDataCleansingStep(alias, this);
	}

	/**
	 * Rename this table
	 */
	public OfcDataCleansingStep rename(String name) {
		return new OfcDataCleansingStep(name, null);
	}
}

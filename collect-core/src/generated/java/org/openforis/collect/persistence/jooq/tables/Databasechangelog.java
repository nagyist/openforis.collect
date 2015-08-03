/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;

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
public class Databasechangelog extends org.jooq.impl.TableImpl<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord> {

	private static final long serialVersionUID = 747358845;

	/**
	 * The reference instance of <code>collect.databasechangelog</code>
	 */
	public static final org.openforis.collect.persistence.jooq.tables.Databasechangelog DATABASECHANGELOG = new org.openforis.collect.persistence.jooq.tables.Databasechangelog();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord> getRecordType() {
		return org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord.class;
	}

	/**
	 * The column <code>collect.databasechangelog.id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(63).nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.author</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> AUTHOR = createField("author", org.jooq.impl.SQLDataType.VARCHAR.length(63).nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.filename</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> FILENAME = createField("filename", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.dateexecuted</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.sql.Timestamp> DATEEXECUTED = createField("dateexecuted", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.orderexecuted</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.Integer> ORDEREXECUTED = createField("orderexecuted", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.exectype</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> EXECTYPE = createField("exectype", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangelog.md5sum</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> MD5SUM = createField("md5sum", org.jooq.impl.SQLDataType.VARCHAR.length(35), this, "");

	/**
	 * The column <code>collect.databasechangelog.description</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.databasechangelog.comments</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> COMMENTS = createField("comments", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.databasechangelog.tag</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> TAG = createField("tag", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.databasechangelog.liquibase</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord, java.lang.String> LIQUIBASE = createField("liquibase", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * Create a <code>collect.databasechangelog</code> table reference
	 */
	public Databasechangelog() {
		this("databasechangelog", null);
	}

	/**
	 * Create an aliased <code>collect.databasechangelog</code> table reference
	 */
	public Databasechangelog(java.lang.String alias) {
		this(alias, org.openforis.collect.persistence.jooq.tables.Databasechangelog.DATABASECHANGELOG);
	}

	private Databasechangelog(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord> aliased) {
		this(alias, aliased, null);
	}

	private Databasechangelog(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord> getPrimaryKey() {
		return org.openforis.collect.persistence.jooq.Keys.PK_DATABASECHANGELOG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangelogRecord>>asList(org.openforis.collect.persistence.jooq.Keys.PK_DATABASECHANGELOG);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.openforis.collect.persistence.jooq.tables.Databasechangelog as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.Databasechangelog(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.openforis.collect.persistence.jooq.tables.Databasechangelog rename(java.lang.String name) {
		return new org.openforis.collect.persistence.jooq.tables.Databasechangelog(name, null);
	}
}

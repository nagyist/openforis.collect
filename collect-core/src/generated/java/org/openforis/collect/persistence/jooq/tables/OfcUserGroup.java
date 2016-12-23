/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.openforis.collect.persistence.jooq.Collect;
import org.openforis.collect.persistence.jooq.Keys;
import org.openforis.collect.persistence.jooq.tables.records.OfcUserGroupRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcUserGroup extends TableImpl<OfcUserGroupRecord> {

	private static final long serialVersionUID = -1382424797;

	/**
	 * The reference instance of <code>collect.ofc_user_group</code>
	 */
	public static final OfcUserGroup OFC_USER_GROUP = new OfcUserGroup();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<OfcUserGroupRecord> getRecordType() {
		return OfcUserGroupRecord.class;
	}

	/**
	 * The column <code>collect.ofc_user_group.id</code>.
	 */
	public final TableField<OfcUserGroupRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_user_group.name</code>.
	 */
	public final TableField<OfcUserGroupRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(63).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_user_group.label</code>.
	 */
	public final TableField<OfcUserGroupRecord, String> LABEL = createField("label", org.jooq.impl.SQLDataType.VARCHAR.length(127).nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_user_group.description</code>.
	 */
	public final TableField<OfcUserGroupRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>collect.ofc_user_group.created_by</code>.
	 */
	public final TableField<OfcUserGroupRecord, Integer> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_user_group.creation_date</code>.
	 */
	public final TableField<OfcUserGroupRecord, Timestamp> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_user_group.visibility_code</code>. P=Public, N=Private
	 */
	public final TableField<OfcUserGroupRecord, String> VISIBILITY_CODE = createField("visibility_code", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "P=Public, N=Private");

	/**
	 * Create a <code>collect.ofc_user_group</code> table reference
	 */
	public OfcUserGroup() {
		this("ofc_user_group", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_user_group</code> table reference
	 */
	public OfcUserGroup(String alias) {
		this(alias, OFC_USER_GROUP);
	}

	private OfcUserGroup(String alias, Table<OfcUserGroupRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcUserGroup(String alias, Table<OfcUserGroupRecord> aliased, Field<?>[] parameters) {
		super(alias, Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<OfcUserGroupRecord> getPrimaryKey() {
		return Keys.OFC_USER_GROUP_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<OfcUserGroupRecord>> getKeys() {
		return Arrays.<UniqueKey<OfcUserGroupRecord>>asList(Keys.OFC_USER_GROUP_PKEY, Keys.OFC_USER_GROUP_NAME_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<OfcUserGroupRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<OfcUserGroupRecord, ?>>asList(Keys.OFC_USER_GROUP__OFC_USER_GROUP_CREATED_BY_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserGroup as(String alias) {
		return new OfcUserGroup(alias, this);
	}

	/**
	 * Rename this table
	 */
	public OfcUserGroup rename(String name) {
		return new OfcUserGroup(name, null);
	}
}

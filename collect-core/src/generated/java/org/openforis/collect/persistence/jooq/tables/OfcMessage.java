/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.openforis.collect.persistence.jooq.Collect;
import org.openforis.collect.persistence.jooq.Keys;
import org.openforis.collect.persistence.jooq.tables.records.OfcMessageRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcMessage extends TableImpl<OfcMessageRecord> {

	private static final long serialVersionUID = 1763638656;

	/**
	 * The reference instance of <code>collect.ofc_message</code>
	 */
	public static final OfcMessage OFC_MESSAGE = new OfcMessage();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<OfcMessageRecord> getRecordType() {
		return OfcMessageRecord.class;
	}

	/**
	 * The column <code>collect.ofc_message.id</code>.
	 */
	public final TableField<OfcMessageRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_message.sequence_no</code>.
	 */
	public final TableField<OfcMessageRecord, Integer> SEQUENCE_NO = createField("sequence_no", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>collect.ofc_message.publication_time</code>.
	 */
	public final TableField<OfcMessageRecord, Timestamp> PUBLICATION_TIME = createField("publication_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_message.queue_id</code>.
	 */
	public final TableField<OfcMessageRecord, String> QUEUE_ID = createField("queue_id", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * The column <code>collect.ofc_message.message_string</code>.
	 */
	public final TableField<OfcMessageRecord, String> MESSAGE_STRING = createField("message_string", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>collect.ofc_message.message_bytes</code>.
	 */
	public final TableField<OfcMessageRecord, byte[]> MESSAGE_BYTES = createField("message_bytes", org.jooq.impl.SQLDataType.BLOB, this, "");

	/**
	 * Create a <code>collect.ofc_message</code> table reference
	 */
	public OfcMessage() {
		this("ofc_message", null);
	}

	/**
	 * Create an aliased <code>collect.ofc_message</code> table reference
	 */
	public OfcMessage(String alias) {
		this(alias, OFC_MESSAGE);
	}

	private OfcMessage(String alias, Table<OfcMessageRecord> aliased) {
		this(alias, aliased, null);
	}

	private OfcMessage(String alias, Table<OfcMessageRecord> aliased, Field<?>[] parameters) {
		super(alias, Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<OfcMessageRecord, Integer> getIdentity() {
		return Keys.IDENTITY_OFC_MESSAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<OfcMessageRecord> getPrimaryKey() {
		return Keys.PK_OFC_MESSAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<OfcMessageRecord>> getKeys() {
		return Arrays.<UniqueKey<OfcMessageRecord>>asList(Keys.PK_OFC_MESSAGE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcMessage as(String alias) {
		return new OfcMessage(alias, this);
	}

	/**
	 * Rename this table
	 */
	public OfcMessage rename(String name) {
		return new OfcMessage(name, null);
	}
}

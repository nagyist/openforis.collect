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
public class OfcUserRecord extends org.jooq.impl.UpdatableRecordImpl<org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord> implements org.jooq.Record4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = 2017120699;

	/**
	 * Setter for <code>collect.ofc_user.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>collect.ofc_user.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>collect.ofc_user.username</code>.
	 */
	public void setUsername(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>collect.ofc_user.username</code>.
	 */
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>collect.ofc_user.password</code>.
	 */
	public void setPassword(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>collect.ofc_user.password</code>.
	 */
	public java.lang.String getPassword() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>collect.ofc_user.enabled</code>.
	 */
	public void setEnabled(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>collect.ofc_user.enabled</code>.
	 */
	public java.lang.String getEnabled() {
		return (java.lang.String) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ENABLED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getEnabled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserRecord value2(java.lang.String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserRecord value3(java.lang.String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserRecord value4(java.lang.String value) {
		setEnabled(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcUserRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OfcUserRecord
	 */
	public OfcUserRecord() {
		super(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER);
	}

	/**
	 * Create a detached, initialised OfcUserRecord
	 */
	public OfcUserRecord(java.lang.Integer id, java.lang.String username, java.lang.String password, java.lang.String enabled) {
		super(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER);

		setValue(0, id);
		setValue(1, username);
		setValue(2, password);
		setValue(3, enabled);
	}
}

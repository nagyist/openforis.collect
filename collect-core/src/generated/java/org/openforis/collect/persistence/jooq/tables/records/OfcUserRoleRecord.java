/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.5.0"},
                            comments = "This class is generated by jOOQ")
public class OfcUserRoleRecord extends org.jooq.impl.UpdatableRecordImpl<org.openforis.collect.persistence.jooq.tables.records.OfcUserRoleRecord> {

	private static final long serialVersionUID = -287261516;

	/**
	 * The table column <code>collect.ofc_user_role.id</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	public void setId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.ID, value);
	}

	/**
	 * The table column <code>collect.ofc_user_role.id</code>
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 */
	public java.lang.Integer getId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.ID);
	}

	/**
	 * The table column <code>collect.ofc_user_role.user_id</code>
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT ofc_user_role__ofc_user_user_role_fkey
	 * FOREIGN KEY (user_id)
	 * REFERENCES collect.ofc_user (id)
	 * </pre></code>
	 */
	public void setUserId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.USER_ID, value);
	}

	/**
	 * The table column <code>collect.ofc_user_role.user_id</code>
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT ofc_user_role__ofc_user_user_role_fkey
	 * FOREIGN KEY (user_id)
	 * REFERENCES collect.ofc_user (id)
	 * </pre></code>
	 */
	public java.lang.Integer getUserId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.USER_ID);
	}

	/**
	 * Link this record to a given {@link org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord 
	 * OfcUserRecord}
	 */
	public void setUserId(org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord value) {
		if (value == null) {
			setValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.USER_ID, null);
		}
		else {
			setValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.USER_ID, value.getValue(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID));
		}
	}

	/**
	 * The table column <code>collect.ofc_user_role.user_id</code>
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT ofc_user_role__ofc_user_user_role_fkey
	 * FOREIGN KEY (user_id)
	 * REFERENCES collect.ofc_user (id)
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord fetchOfcUser() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER)
			.where(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.USER_ID)))
			.fetchOne();
	}

	/**
	 * The table column <code>collect.ofc_user_role.role</code>
	 */
	public void setRole(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.ROLE, value);
	}

	/**
	 * The table column <code>collect.ofc_user_role.role</code>
	 */
	public java.lang.String getRole() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE.ROLE);
	}

	/**
	 * Create a detached OfcUserRoleRecord
	 */
	public OfcUserRoleRecord() {
		super(org.openforis.collect.persistence.jooq.tables.OfcUserRole.OFC_USER_ROLE);
	}
}

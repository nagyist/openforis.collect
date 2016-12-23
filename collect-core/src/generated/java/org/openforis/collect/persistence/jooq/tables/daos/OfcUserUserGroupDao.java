/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;
import org.openforis.collect.persistence.jooq.tables.OfcUserUserGroup;
import org.openforis.collect.persistence.jooq.tables.records.OfcUserUserGroupRecord;


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
public class OfcUserUserGroupDao extends DAOImpl<OfcUserUserGroupRecord, org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup, Record2<Integer, Integer>> {

	/**
	 * Create a new OfcUserUserGroupDao without any configuration
	 */
	public OfcUserUserGroupDao() {
		super(OfcUserUserGroup.OFC_USER_USER_GROUP, org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup.class);
	}

	/**
	 * Create a new OfcUserUserGroupDao with an attached configuration
	 */
	public OfcUserUserGroupDao(Configuration configuration) {
		super(OfcUserUserGroup.OFC_USER_USER_GROUP, org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Record2<Integer, Integer> getId(org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup object) {
		return compositeKeyRecord(object.getUserId(), object.getGroupId());
	}

	/**
	 * Fetch records that have <code>user_id IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByUserId(Integer... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.USER_ID, values);
	}

	/**
	 * Fetch records that have <code>group_id IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByGroupId(Integer... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.GROUP_ID, values);
	}

	/**
	 * Fetch records that have <code>role_code IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByRoleCode(String... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.ROLE_CODE, values);
	}

	/**
	 * Fetch records that have <code>status_code IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByStatusCode(String... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.STATUS_CODE, values);
	}

	/**
	 * Fetch records that have <code>request_date IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByRequestDate(Timestamp... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.REQUEST_DATE, values);
	}

	/**
	 * Fetch records that have <code>member_since IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcUserUserGroup> fetchByMemberSince(Timestamp... values) {
		return fetch(OfcUserUserGroup.OFC_USER_USER_GROUP.MEMBER_SINCE, values);
	}
}

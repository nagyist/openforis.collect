/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;
import org.openforis.collect.persistence.jooq.tables.OfcDataQueryGroupQuery;
import org.openforis.collect.persistence.jooq.tables.records.OfcDataQueryGroupQueryRecord;


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
public class OfcDataQueryGroupQueryDao extends DAOImpl<OfcDataQueryGroupQueryRecord, org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery, Record2<Integer, Integer>> {

	/**
	 * Create a new OfcDataQueryGroupQueryDao without any configuration
	 */
	public OfcDataQueryGroupQueryDao() {
		super(OfcDataQueryGroupQuery.OFC_DATA_QUERY_GROUP_QUERY, org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery.class);
	}

	/**
	 * Create a new OfcDataQueryGroupQueryDao with an attached configuration
	 */
	public OfcDataQueryGroupQueryDao(Configuration configuration) {
		super(OfcDataQueryGroupQuery.OFC_DATA_QUERY_GROUP_QUERY, org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Record2<Integer, Integer> getId(org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery object) {
		return compositeKeyRecord(object.getGroupId(), object.getQueryId());
	}

	/**
	 * Fetch records that have <code>group_id IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery> fetchByGroupId(Integer... values) {
		return fetch(OfcDataQueryGroupQuery.OFC_DATA_QUERY_GROUP_QUERY.GROUP_ID, values);
	}

	/**
	 * Fetch records that have <code>query_id IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery> fetchByQueryId(Integer... values) {
		return fetch(OfcDataQueryGroupQuery.OFC_DATA_QUERY_GROUP_QUERY.QUERY_ID, values);
	}

	/**
	 * Fetch records that have <code>sort_order IN (values)</code>
	 */
	public List<org.openforis.collect.persistence.jooq.tables.pojos.OfcDataQueryGroupQuery> fetchBySortOrder(Integer... values) {
		return fetch(OfcDataQueryGroupQuery.OFC_DATA_QUERY_GROUP_QUERY.SORT_ORDER, values);
	}
}

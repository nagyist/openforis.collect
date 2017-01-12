package org.openforis.collect.persistence;

import static org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER;
import static org.openforis.collect.persistence.jooq.tables.OfcInstitution.OFC_INSTITUTION;
import static org.openforis.collect.persistence.jooq.tables.OfcUserInstitution.OFC_USER_INSTITUTION;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.openforis.collect.model.User;
import org.openforis.collect.model.Institution;
import org.openforis.collect.model.Institution.InstitutionJoinRequestStatus;
import org.openforis.collect.model.Institution.InstitutionRole;
import org.openforis.collect.persistence.jooq.tables.daos.OfcInstitutionDao;

public class InstitutionDao extends OfcInstitutionDao {

	public InstitutionDao(Configuration configuration) {
		super(configuration);
	}

	public List<Institution> loadAll() {
		return dsl()
			.selectFrom(OFC_INSTITUTION)
			.orderBy(OFC_INSTITUTION.NAME)
			.fetchInto(Institution.class);
	}

	public List<User> findUsersByInstitution(Institution institution) {
		DSLContext dsl = dsl();
		List<User> result = dsl.selectFrom(OFC_USER)
			.where(
				OFC_USER.ID.in(
					dsl.select(OFC_USER_INSTITUTION.USER_ID)
						.from(OFC_USER_INSTITUTION)
						.where(OFC_USER_INSTITUTION.INSTITUTION_ID.eq(institution.getId()))
					)
				)
			.fetchInto(User.class);
		return result;
	}
	
	public List<Institution> findInstitutionsByUser(User user) {
		DSLContext dsl = dsl();
		List<Institution> result = dsl.selectFrom(OFC_INSTITUTION)
			.where(
				OFC_INSTITUTION.ID.in(
					dsl.select(OFC_USER_INSTITUTION.INSTITUTION_ID)
						.from(OFC_USER_INSTITUTION)
						.where(OFC_USER_INSTITUTION.USER_ID.eq(user.getId()))
					)
				)
			.orderBy(OFC_INSTITUTION.LABEL)
			.fetchInto(Institution.class);
		return result;
	}

	public void acceptJoinRequest(User user, Institution institution) {
		dsl().update(OFC_USER_INSTITUTION)
			.set(OFC_USER_INSTITUTION.STATUS_CODE, String.valueOf(InstitutionJoinRequestStatus.ACCEPTED.getCode()))
			.where(OFC_USER_INSTITUTION.INSTITUTION_ID.eq(institution.getId()).and(OFC_USER_INSTITUTION.USER_ID.eq(user.getId())))
			.execute();
	}

	public Institution loadByName(String name) {
		return dsl()
			.selectFrom(OFC_INSTITUTION)
			.where(OFC_INSTITUTION.NAME.eq(name))
			.fetchOneInto(Institution.class);
	}

	public void insertRelation(User user, Institution institution, InstitutionJoinRequestStatus joinStatus, Date memberSince) {
		DSL.insertInto(OFC_USER_INSTITUTION, OFC_USER_INSTITUTION.INSTITUTION_ID, OFC_USER_INSTITUTION.USER_ID, 
				OFC_USER_INSTITUTION.REQUEST_DATE, OFC_USER_INSTITUTION.MEMBER_SINCE, 
				OFC_USER_INSTITUTION.STATUS_CODE, OFC_USER_INSTITUTION.ROLE_CODE)
			.values(institution.getId(), user.getId(), new Timestamp(System.currentTimeMillis()),
					memberSince == null ? null : new Timestamp(memberSince.getTime()), String.valueOf(joinStatus.getCode()),
					String.valueOf(InstitutionRole.OPERATOR.getCode()))
			.execute();
	}
	
	private DSLContext dsl() {
		return DSL.using(configuration());
	}

}

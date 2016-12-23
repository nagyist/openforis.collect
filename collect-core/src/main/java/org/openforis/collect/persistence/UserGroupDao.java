package org.openforis.collect.persistence;

import static org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER;
import static org.openforis.collect.persistence.jooq.tables.OfcUserGroup.OFC_USER_GROUP;
import static org.openforis.collect.persistence.jooq.tables.OfcUserUserGroup.OFC_USER_USER_GROUP;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.openforis.collect.model.User;
import org.openforis.collect.model.UserGroup;
import org.openforis.collect.model.UserGroup.UserGroupRequestStatus;
import org.openforis.collect.model.UserGroup.UserGroupRole;
import org.openforis.collect.persistence.jooq.tables.daos.OfcUserGroupDao;

public class UserGroupDao extends OfcUserGroupDao {

	public UserGroupDao(Configuration configuration) {
		super(configuration);
	}

	public List<User> findUsersByGroup(UserGroup group) {
		DSLContext dsl = dsl();
		List<User> result = dsl.selectFrom(OFC_USER)
			.where(
				OFC_USER.ID.in(
					dsl.select(OFC_USER_USER_GROUP.USER_ID)
						.from(OFC_USER_USER_GROUP)
						.where(OFC_USER_USER_GROUP.GROUP_ID.eq(group.getId()))
					)
				)
			.fetchInto(User.class);
		return result;
	}
	
	public List<UserGroup> findGroupsByUser(User user) {
		DSLContext dsl = dsl();
		List<UserGroup> result = dsl.selectFrom(OFC_USER_GROUP)
			.where(
				OFC_USER_GROUP.ID.in(
					dsl.select(OFC_USER_USER_GROUP.GROUP_ID)
						.from(OFC_USER_USER_GROUP)
						.where(OFC_USER_USER_GROUP.USER_ID.eq(user.getId()))
					)
				)
			.orderBy(OFC_USER_GROUP.LABEL)
			.fetchInto(UserGroup.class);
		return result;
	}

	private DSLContext dsl() {
		return DSL.using(configuration());
	}

	public void acceptJoinRequest(User user, UserGroup group) {
			dsl().update(OFC_USER_USER_GROUP)
			.set(OFC_USER_USER_GROUP.STATUS_CODE, String.valueOf(UserGroupRequestStatus.ACCEPTED.getCode()))
			.where(OFC_USER_USER_GROUP.GROUP_ID.eq(group.getId()).and(OFC_USER_USER_GROUP.USER_ID.eq(user.getId())))
			.execute();
	}

	public UserGroup loadByName(String userGroupName) {
		return dsl()
			.selectFrom(OFC_USER_GROUP)
			.where(OFC_USER_GROUP.NAME.eq(userGroupName))
			.fetchOneInto(UserGroup.class);
	}

	public void insertRelation(User user, UserGroup group, UserGroupRequestStatus joinStatus, Date memberSince) {
		DSL.insertInto(OFC_USER_USER_GROUP, OFC_USER_USER_GROUP.GROUP_ID, OFC_USER_USER_GROUP.USER_ID, 
				OFC_USER_USER_GROUP.REQUEST_DATE, OFC_USER_USER_GROUP.MEMBER_SINCE, 
				OFC_USER_USER_GROUP.STATUS_CODE, OFC_USER_USER_GROUP.ROLE_CODE)
			.values(group.getId(), user.getId(), new Timestamp(System.currentTimeMillis()),
					memberSince == null ? null : new Timestamp(memberSince.getTime()), String.valueOf(joinStatus.getCode()),
					String.valueOf(UserGroupRole.OPERATOR.getCode()))
			.execute();
		
	}
	
}

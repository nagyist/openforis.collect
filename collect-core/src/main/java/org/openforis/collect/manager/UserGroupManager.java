package org.openforis.collect.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openforis.collect.model.User;
import org.openforis.collect.model.UserGroup;
import org.openforis.collect.model.UserGroup.UserGroupRequestStatus;
import org.openforis.collect.model.UserGroup.Visibility;
import org.openforis.collect.persistence.UserGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author S. Ricci
 *
 */
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class UserGroupManager {

	private static final String DEFAULT_PUBLIC_GROUP_NAME = "default_group";
	private static final String DEFAULT_PRIVATE_GROUP_SUFFIX = "_default_group";
	private static final String DEFAULT_PRIVATE_GROUP_LABEL_SUFFIX = " Default Group";
	
	@Autowired
	private UserGroupDao dao;

	@Transactional(propagation=Propagation.REQUIRED)
	public UserGroup createDefaultPrivateUserGroup(User user) {
		UserGroup userGroup = new UserGroup();
		userGroup.setName(getDefaultPrivateGroupName(user));
		userGroup.setLabel(user.getName() + DEFAULT_PRIVATE_GROUP_LABEL_SUFFIX);
		userGroup.setVisibility(Visibility.PRIVATE);
		dao.insert(userGroup);
		insertRelation(user, userGroup, UserGroupRequestStatus.ACCEPTED, new Date());
		return userGroup;
	}

	public String getDefaultPrivateGroupName(User user) {
		return user.getName() + DEFAULT_PRIVATE_GROUP_SUFFIX;
	}
	
	public String getDefaultPublicGroupName() {
		return DEFAULT_PUBLIC_GROUP_NAME;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void save(UserGroup userGroup) {
		if (userGroup.getId() == null) {
			dao.insert(userGroup);
		} else {
			dao.update(userGroup);
		}
	}
	
	public UserGroup loadGroupByName(String userGroupName) {
		return dao.loadByName(userGroupName);
	}

	public Collection<User> findUsersByGroup(UserGroup group) {
		return dao.findUsersByGroup(group);
	}
	
	public List<UserGroup> findGroupsByUser(User user) {
		return dao.findGroupsByUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void requestJoin(User user, UserGroup group) {
		insertRelation(user, group, UserGroupRequestStatus.PENDING, null);
	}

	private void insertRelation(User user, UserGroup group, UserGroupRequestStatus joinStatus, Date memberSince) {
		dao.insertRelation(user, group, joinStatus, memberSince);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void acceptJoinRequest(User user, UserGroup group) {
		dao.acceptJoinRequest(user, group);
	}

}

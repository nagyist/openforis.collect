package org.openforis.collect.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openforis.collect.model.User;
import org.openforis.collect.model.Institution;
import org.openforis.collect.model.Institution.InstitutionJoinRequestStatus;
import org.openforis.collect.model.Institution.Visibility;
import org.openforis.collect.persistence.InstitutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author S. Ricci
 *
 */
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class InstitutionManager {

	private static final String DEFAULT_PUBLIC_INSTITUTION_NAME = "default_public_institution";
	private static final String DEFAULT_PRIVATE_INSTITUTION_SUFFIX = "_default_private_institution";
	private static final String DEFAULT_PRIVATE_INSTITUTION_LABEL_SUFFIX = " Default Private Institution";
	
	@Autowired
	private InstitutionDao dao;

	@Transactional(propagation=Propagation.REQUIRED)
	public Institution createDefaultPrivateInstitution(User user) {
		Institution institution = new Institution();
		institution.setName(getDefaultPrivateInstitutionName(user));
		institution.setLabel(user.getName() + DEFAULT_PRIVATE_INSTITUTION_LABEL_SUFFIX);
		institution.setVisibility(Visibility.PRIVATE);
		dao.insert(institution);
		insertRelation(user, institution, InstitutionJoinRequestStatus.ACCEPTED, new Date());
		return institution;
	}

	public String getDefaultPrivateInstitutionName(User user) {
		return user.getName() + DEFAULT_PRIVATE_INSTITUTION_SUFFIX;
	}
	
	public String getDefaultPublicInstitutionName() {
		return DEFAULT_PUBLIC_INSTITUTION_NAME;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Institution institution) {
		if (institution.getId() == null) {
			dao.insert(institution);
		} else {
			dao.update(institution);
		}
	}
	
	public List<Institution> loadAll() {
		return dao.loadAll();
	}

	public Institution loadByName(String institutionName) {
		return dao.loadByName(institutionName);
	}

	public Institution loadById(int id) {
		return dao.loadById(id);
	}

	public Collection<User> findUsersByInstitution(Institution institution) {
		return dao.findUsersByInstitution(institution);
	}
	
	public List<Institution> findByUser(User user) {
		return dao.findInstitutionsByUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void requestJoin(User user, Institution group) {
		insertRelation(user, group, InstitutionJoinRequestStatus.PENDING, null);
	}

	private void insertRelation(User user, Institution institution, InstitutionJoinRequestStatus joinStatus, Date memberSince) {
		dao.insertRelation(user, institution, joinStatus, memberSince);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void acceptJoinRequest(User user, Institution institution) {
		dao.acceptJoinRequest(user, institution);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(int id) {
		dao.deleteById(id);
	}

}

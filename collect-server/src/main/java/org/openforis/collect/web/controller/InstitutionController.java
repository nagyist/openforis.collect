package org.openforis.collect.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;
import java.util.List;

import org.openforis.collect.manager.InstitutionManager;
import org.openforis.collect.manager.UserManager;
import org.openforis.collect.model.Institution;
import org.openforis.collect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author S. Ricci
 *
 */
@Controller
@RequestMapping("/institutions/")
public class InstitutionController extends BasicController {

	@Autowired
	private InstitutionManager institutionManager;
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value="summaries.json", method=GET, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Institution> loadAll(@RequestParam(required=false) Integer userId) throws Exception {
		if (userId == null) {
			return institutionManager.loadAll();
		} else {
			User user = userManager.loadById(userId);
			return institutionManager.findByUser(user);
		}
	}
	
	@RequestMapping(value="/{institutionId}.json", method=GET, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	Institution loadById(@PathVariable Integer institutionId) throws Exception {
		return institutionManager.loadById(institutionId);
	}
	
	@Transactional
	@RequestMapping(value="/save.json", method=POST, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	Institution save(@Validated Institution institution,  BindingResult result) throws Exception {
		if (institution.getId() == null) {
			institution.setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		institutionManager.save(institution);
		return institution;
	}
	
	@RequestMapping(value="/{institutionId}.json", method=DELETE, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	boolean delete(@PathVariable Integer institutionId) throws Exception {
		institutionManager.delete(institutionId);
		return true;
	}
	
	@Transactional
	@RequestMapping(value="/{institutionId}/requestjoin.json", method=POST, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	boolean requestJoin(@PathVariable Integer institutionId, @RequestParam Integer userId) throws Exception {
		Institution institution = institutionManager.loadById(institutionId);
		User user = userManager.loadById(userId);
		institutionManager.requestJoin(user, institution);
		return true;
	}

	@Transactional
	@RequestMapping(value="/{institutionId}/acceptjoinrequest.json", method=POST, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	boolean acceptJoinRequest(@PathVariable Integer institutionId, @RequestParam Integer userId) throws Exception {
		Institution institution = institutionManager.loadById(institutionId);
		User user = userManager.loadById(userId);
		institutionManager.acceptJoinRequest(user, institution);
		return true;
	}
}

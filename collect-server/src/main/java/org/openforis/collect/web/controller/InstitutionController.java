package org.openforis.collect.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.util.List;

import org.openforis.collect.manager.InstitutionManager;
import org.openforis.collect.manager.UserManager;
import org.openforis.collect.model.Institution;
import org.openforis.collect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(method=GET, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Institution> loadAll(@RequestParam(required=false) Integer userID) throws Exception {
		if (userID == null) {
			return institutionManager.loadAll();
		} else {
			User user = userManager.loadById(userID);
			return institutionManager.findByUser(user);
		}
	}
	
	@RequestMapping(value="/{id}", method=GET, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	Institution loadById(@PathVariable Integer institutionId) throws Exception {
		return institutionManager.loadById(institutionId);
	}
	
	@Transactional
	@RequestMapping(method=POST, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	Institution save(@RequestBody Institution institution, BindingResult result) throws Exception {
		institutionManager.save(institution);
		return institution;
	}
	
	@RequestMapping(value="/{id}", method=DELETE, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	void delete(@PathVariable Integer institutionId) throws Exception {
		institutionManager.delete(institutionId);
	}
	
}

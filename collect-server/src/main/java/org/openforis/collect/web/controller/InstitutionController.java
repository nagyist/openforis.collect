package org.openforis.collect.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.openforis.collect.manager.InstitutionManager;
import org.openforis.collect.model.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value = "", method=GET, produces=APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Institution> loadInstitutions() throws Exception {
		return institutionManager.loadAll();
	}
	
}

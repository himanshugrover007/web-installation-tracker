package com.luv2code.springdemo.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.luv2code.springdemo.constants.ActivityEmail;
import com.luv2code.springdemo.entity.Installation;
import com.luv2code.springdemo.entity.InstallationUserDetails;
import com.luv2code.springdemo.entity.LoginForm;
import com.luv2code.springdemo.service.InstallationService;
import com.luv2code.springdemo.service.InstallationUserService;
import com.luv2code.springdemo.service.MailService;

@EnableWebMvc
@Controller
@RequestMapping("/installation")
public class InstallationController {

	// need to inject our installation service
	@Autowired
	private InstallationService installationService;
	
	@Autowired
	private InstallationUserService installationUserService;
	
	@Autowired
	private MailService mailService;

	@GetMapping("/list")
	public String listInstallations(Model theModel) {

		// get installations from the service
		List<Installation> theInstallations = installationService.getInstallations();
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();
		// add the installations to the model
		theModel.addAttribute("hashMapUserDetailsForEachInstallation", hashMapUserDetailsForEachInstallation);
		theModel.addAttribute("installations", theInstallations);

		return "list-installations";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Installation theInstallation = new Installation();

		theModel.addAttribute("installation", theInstallation);
		theModel.addAttribute("ipList", getIPs());
		theModel.addAttribute("environmentTypeList", getEnvironmentTypes());
		theModel.addAttribute("installationStatuses", getInstallationStatuses());
		return "installation-form";
	}

	@PostMapping("/saveInstallation")
	public String saveInstallation(@ModelAttribute("installation") @Valid Installation theInstallation,
			BindingResult bindingResult, Model theModel,HttpServletRequest httpServletRequest) {

		if (bindingResult.hasErrors()) {
			theModel.addAttribute("installation", theInstallation);
			theModel.addAttribute("ipList", getIPs());
			theModel.addAttribute("environmentTypeList", getEnvironmentTypes());
			theModel.addAttribute("installationStatuses", getInstallationStatuses());			
			return "installation-form";
		}

		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");
		theInstallation.setInstalledBy(loginform.getUsername());
		
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();
		
		// save the installation using our service
		if (theInstallation.getId() == 0) {
			installationService.saveInstallation(theInstallation);			
			mailService.sendMail(loginform,mailService.getMessage(loginform, theInstallation, hashMapUserDetailsForEachInstallation, ActivityEmail.CREATE));
			
		} else {
			// get the installation from our service
			Installation theInstallationExisting = installationService.getInstallation(theInstallation.getId());
			theInstallation.setCreatedDate(theInstallationExisting.getCreatedDate());
			installationService.updateInstallation(theInstallation);
			mailService.sendMail(loginform,mailService.getMessage(loginform, theInstallation, hashMapUserDetailsForEachInstallation, ActivityEmail.UPDATE));
			
		}
		return "redirect:/installation/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("installationId") int theId, Model theModel) {

		// get the installation from our service
		Installation theInstallation = installationService.getInstallation(theId);

		// set installation as a model attribute to pre-populate the form
		theModel.addAttribute("installation", theInstallation);
		theModel.addAttribute("ipList", getIPs());
		theModel.addAttribute("environmentTypeList", getEnvironmentTypes());
		theModel.addAttribute("installationStatuses", getInstallationStatuses());			
		// send over to our form
		return "installation-form";
	}

	@GetMapping("/delete")
	public String deleteInstallation(@RequestParam("installationId") int theInstallationId, HttpServletRequest httpServletRequest) {
		
		// get the installation from our service
		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");

		// delete the installation
		
		Installation theInstallation = installationService.getInstallation(theInstallationId);
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();
		installationService.deleteInstallation(theInstallationId);
		installationUserService.deleteUserFromInstallationUserTable(theInstallationId);
		mailService.sendMail(loginform,mailService.getMessage(loginform, theInstallation, hashMapUserDetailsForEachInstallation,  ActivityEmail.DELETE));
		return "redirect:/installation/list";
	}
	
	@GetMapping("/useInstallation")
	public String useInstallation(@RequestParam("installationId") int theId, Model theModel, HttpServletRequest httpServletRequest) {

		// get the installation from our service
		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");
		InstallationUserDetails theInstallationUser= new InstallationUserDetails();
		theInstallationUser.setInstallationId(theId);
		theInstallationUser.setInstallationUser(loginform.getUsername());
		Installation theInstallation = installationService.getInstallation(theId);
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();		
		installationUserService.saveInstallationUser(theInstallationUser);
		mailService.sendMail(loginform,mailService.getMessage(loginform, theInstallation, hashMapUserDetailsForEachInstallation,  ActivityEmail.INUSE));
		// send over to our form
		return "redirect:/installation/list";
	}
	
	@GetMapping("/releaseInstallation")
	public String releaseInstallation(@RequestParam("installationId") int theId, Model theModel, HttpServletRequest httpServletRequest) {

		// get the installation from our service
		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");
		InstallationUserDetails theInstallationUser= new InstallationUserDetails();
		theInstallationUser.setInstallationId(theId);
		theInstallationUser.setInstallationUser(loginform.getUsername());	
		Installation theInstallation = installationService.getInstallation(theId);
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();
		installationUserService.deleteDetailsFromInstallationUser(theInstallationUser);
		mailService.sendMail(loginform,mailService.getMessage(loginform, theInstallation, hashMapUserDetailsForEachInstallation,  ActivityEmail.RELEASE));
		// send over to our form
		return "redirect:/installation/list";
	}

	private Map getIPs() {
		Map<String, String> ips = new LinkedHashMap<String, String>();
		ips.put("10.30.32.16", "10.30.32.16");
		ips.put("10.30.32.76", "10.30.32.76");
		ips.put("10.30.32.83", "10.30.32.83");
		ips.put("10.30.32.101", "10.30.32.101");
		ips.put("10.30.32.126", "10.30.32.126");
		ips.put("10.30.32.147", "10.30.32.147");
		ips.put("10.30.32.166", "10.30.32.166");
		ips.put("10.30.32.167", "10.30.32.167");
		ips.put("10.30.32.168", "10.30.32.168");
		return ips;
	}
	
	private Map getEnvironmentTypes() {
		Map<String, String> environmentTypes = new LinkedHashMap<String, String>();
		environmentTypes.put("ICS IC", "ICS IC");
		environmentTypes.put("ICS EC", "ICS EC");
		environmentTypes.put("SOA 12C", "SOA 12C");
		environmentTypes.put("SOA 11G", "SOA 11G");
		return environmentTypes;
	}
	
	private Map getInstallationStatuses() {
		Map<String, String> installationStatuses = new LinkedHashMap<String, String>();
		installationStatuses.put("A", "A");
		installationStatuses.put("I", "I");
		return installationStatuses;
	}
	
}

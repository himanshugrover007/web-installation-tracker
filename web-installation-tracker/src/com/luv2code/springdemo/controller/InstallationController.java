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
import com.luv2code.springdemo.entity.custom.validator.InstallationValidator;
import com.luv2code.springdemo.service.InstallationService;
import com.luv2code.springdemo.service.InstallationUserService;
import com.luv2code.springdemo.service.MailService;
import com.luv2code.springdemo.util.InstallationUtility;

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
	
	@Autowired
	private InstallationValidator installationValidator;

	@GetMapping("/list")
	public String listInstallations(Model theModel,HttpServletRequest httpServletRequest) {

		LoginForm loginForm = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");

		// get installations from the service
		List<Installation> theInstallations = installationService.getInstallations();
		HashMap<String, String> hashMapUserDetailsForEachInstallation= installationUserService.getUserDetailsForEachInstallation();
		// add the installations to the model
		theModel.addAttribute("hashMapUserDetailsForEachInstallation", hashMapUserDetailsForEachInstallation);
		theModel.addAttribute("loginForm", loginForm);
		theModel.addAttribute("installations", theInstallations);

		return "list-installations";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Installation theInstallation = new Installation();

		theModel.addAttribute("installation", theInstallation);
		theModel.addAttribute("ipList", InstallationUtility.getIPs());
		theModel.addAttribute("environmentTypeList", InstallationUtility.getEnvironmentTypes());
		theModel.addAttribute("installationStatuses", InstallationUtility.getInstallationStatuses());
		return "installation-form";
	}

	@PostMapping("/saveInstallation")
	public String saveInstallation(@ModelAttribute("installation") @Valid Installation theInstallation,
			BindingResult bindingResult, Model theModel,HttpServletRequest httpServletRequest) {
		
		installationValidator.validate(theInstallation, bindingResult);
		if (bindingResult.hasErrors()) {
			theModel.addAttribute("installation", theInstallation);
			theModel.addAttribute("ipList", InstallationUtility.getIPs());
			theModel.addAttribute("environmentTypeList", InstallationUtility.getEnvironmentTypes());
			theModel.addAttribute("installationStatuses", InstallationUtility.getInstallationStatuses());			
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
			theInstallation.setUpdatedBy(loginform.getUsername());
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
		theModel.addAttribute("ipList", InstallationUtility.getIPs());
		theModel.addAttribute("environmentTypeList", InstallationUtility.getEnvironmentTypes());
		theModel.addAttribute("installationStatuses", InstallationUtility.getInstallationStatuses());			
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
		//installationService.deleteInstallation(theInstallationId);
		theInstallation.setCreatedDate(theInstallation.getCreatedDate());
		theInstallation.setUpdatedBy(loginform.getUsername());
		theInstallation.setDeleted(1);
		installationService.updateInstallation(theInstallation);
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

}

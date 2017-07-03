package com.luv2code.springdemo.controller;

import java.util.List;

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

import com.luv2code.springdemo.entity.IpInfoDetails;
import com.luv2code.springdemo.entity.LoginForm;
import com.luv2code.springdemo.service.IpInfoDetailsService;
import com.luv2code.springdemo.util.InstallationUtility;

@EnableWebMvc
@Controller
@RequestMapping("/ip")
public class IpDetailsController {

	@Autowired
	private IpInfoDetailsService ipInfoDetailsService;
	
	@GetMapping("/list")
	public String listInstallations(Model theModel,HttpServletRequest httpServletRequest) {

		LoginForm loginForm = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");

		// get installations from the service
		List<IpInfoDetails> ipInfoDetailsList = ipInfoDetailsService.getIpInfoDetails();
		// add the installations to the model
		theModel.addAttribute("ipInfoDetailsList", ipInfoDetailsList);

		return "list-ipinfodetails";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		IpInfoDetails ipInfoDetails = new IpInfoDetails();
		theModel.addAttribute("ipInfoDetails", ipInfoDetails);
		theModel.addAttribute("ipList", InstallationUtility.getIPs());
		return "ipinfodetails-form";
	}

	@PostMapping("/saveIpInfoDetails")
	public String saveIpInfoDetails(@ModelAttribute("ipInfoDetails") @Valid IpInfoDetails ipInfoDetails,
			BindingResult bindingResult, Model theModel,HttpServletRequest httpServletRequest) {
		
		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");
		if(bindingResult.hasErrors())
		{
			theModel.addAttribute("ipInfoDetails", ipInfoDetails);
			theModel.addAttribute("ipList", InstallationUtility.getIPs());
			return "ipinfodetails-form";
		}
		
		// save the installation using our service
		if (ipInfoDetails.getId() == 0) {
			ipInfoDetails.setCreatedBy(loginform.getUsername());
			ipInfoDetailsService.saveIpInfoDetails(ipInfoDetails);
		} 
		else {
			IpInfoDetails ipInfoDetailsExisting = ipInfoDetailsService.getIpInfoDetails(ipInfoDetails.getId());
			ipInfoDetails.setCreatedDate(ipInfoDetailsExisting.getCreatedDate());
			ipInfoDetails.setUpdatedBy(loginform.getUsername());
			ipInfoDetailsService.updateIpInfoDetails(ipInfoDetails);
		}
		return "redirect:/ip/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int theId, Model theModel) {

		// get the installation from our service
		IpInfoDetails ipInfoDetails = ipInfoDetailsService.getIpInfoDetails(theId);

		// set installation as a model attribute to pre-populate the form
		theModel.addAttribute("ipInfoDetails", ipInfoDetails);
		theModel.addAttribute("ipList", InstallationUtility.getIPs());
		// send over to our form
		return "ipinfodetails-form";
	}

	@GetMapping("/delete")
	public String deleteInstallation(@RequestParam("id") int theId, HttpServletRequest httpServletRequest) {
		
		// get the installation from our service
		LoginForm loginform = (LoginForm) httpServletRequest.getSession().getAttribute("LOGGEDIN_USER");
		// delete the installation
		//ipInfoDetailsService.deleteIpInfoDetails(theId);		
		IpInfoDetails ipInfoDetailsExisting = ipInfoDetailsService.getIpInfoDetails(theId);
		ipInfoDetailsExisting.setDeleted(1);
		ipInfoDetailsExisting.setCreatedDate(ipInfoDetailsExisting.getCreatedDate());
		ipInfoDetailsExisting.setUpdatedBy(loginform.getUsername());		
		ipInfoDetailsService.updateIpInfoDetails(ipInfoDetailsExisting);
		return "redirect:/ip/list";
	}

}

package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.bean.Mission;
import com.demo.database.DatabaseAccess;

@Controller
public class MainController {
	
	@Autowired
	DatabaseAccess da = new DatabaseAccess();
	
	@RequestMapping(value="/startSession", method = RequestMethod.POST)
	public String starSession(HttpSession session) {
		if(session.isNew()) {
			session.setAttribute("auth", "authorized");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/destroySession", method = RequestMethod.POST)
	public String destroySession(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("agents", da.getAllAgents());
		return "index";
	}
	
	@RequestMapping(value="/viewMission", method=RequestMethod.GET)
	public String view(Model model, @RequestParam String agent, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		String missionsByAgentPageTitle = "Here are the missions for "+agent;
		model.addAttribute("viewPageTitle", missionsByAgentPageTitle);
		model.addAttribute("missions", da.getMissionsByAgent(agent));
		return "view_missions";
	}
	
	@RequestMapping(value="/addMission", method=RequestMethod.GET)
	public String create(Model model, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		model.addAttribute("agents", da.getAllAgents());
		model.addAttribute("mission", new Mission());
		return "create_mission";
	}
	
	@RequestMapping(value="/addMission", method=RequestMethod.POST)
	public String store(Model model, @ModelAttribute Mission mission, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		da.addMission(mission);
		model.addAttribute("agents", da.getAllAgents());
		return "redirect:/";
	}
	
	@RequestMapping(value="/editMission/{id}", method=RequestMethod.GET)
	public String edit(Model model, @PathVariable Long id, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		Mission mission = da.getSingleMission(id);
		if(mission==null) {
			return "redirect:/";
		} else {
			model.addAttribute("editPageTitle", "Edit a mission for "+da.getSingleMission(id).getAgent());
			model.addAttribute("mission", da.getSingleMission(id));
			return "edit_mission";
		}
	}
	
	@RequestMapping(value="/updateMission", method=RequestMethod.POST)
	public String update(Model model, @ModelAttribute Mission mission, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		da.updateMission(mission);
		model.addAttribute("agents", da.getAllAgents());
		return "redirect:/";
	}
	
	@RequestMapping(value="/deleteMission/{id}", method=RequestMethod.GET)
	public String delete(Model model, @PathVariable Long id, HttpSession session) {
		if( session.getAttribute("auth") == null ) {
			return "redirect:/";
		}
		Mission mission = da.getSingleMission(id);
		if(mission!=null) {
			da.deleteMission(id);
		}
		model.addAttribute("agents", da.getAllAgents());
		return "redirect:/";
	}

}

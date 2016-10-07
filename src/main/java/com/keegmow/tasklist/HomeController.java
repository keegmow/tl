package com.keegmow.tasklist;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("tasklist")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	//Main view, retrieves task list from DB, 
	//splits it into separate active/completed views, 
	//sends both lists to main view "tasklistview.jsp"
	@RequestMapping(value = "/")
	public ModelAndView showTaskList(ModelMap model) {
		
		List <Task> tasks = DAO.getTasks();
		List <Task> activeTasks = new ArrayList <Task> ();
		List <Task> completeTasks = new ArrayList <Task> ();
		
		
	// Splits task list into separate lists based on status
		for (Task t : tasks) {
			if(t.isActive()) {
				activeTasks.add(t);
			} else {
				completeTasks.add(t);
			}
		}

		model.addAttribute("active", activeTasks);
		model.addAttribute("complete", completeTasks);

		return new ModelAndView("tasklistview","tasklist", model);
	}
	
	//Toggles a task (Active <-> Completed) and returns main view 
	@RequestMapping(value = "/toggle")
	public ModelAndView toggleTask(@RequestParam(value = "id") int id, ModelMap model) {

		DAO.toggleTask(id);
		
		return showTaskList(model);
	}
	
	//Deletes task based on ID number, returns main view
	@RequestMapping(value = "/delete")
	public ModelAndView deleteTask(@RequestParam(value = "id") int id, ModelMap model) {

		DAO.deleteTask(id);
		
		return showTaskList(model);
	}
	
	//creates new task, adds user-submitted description, returns main view
	@RequestMapping(value = "/addTask")
	public ModelAndView deleteTask(@RequestParam(value = "description") String desc, ModelMap model) {

		Task task = new Task();
		
		task.setActive(true);
		task.setTaskDesc(desc);
		
		int i = DAO.addTask(task);
	
//		System.out.println(i);
		
		return showTaskList(model);
	}
	
	//Deletes all completed tasks and returns the main view
	@RequestMapping(value = "/deleteComplete")
	public ModelAndView deleteComplete(ModelMap model) {
		DAO.deleteComplete();
		return showTaskList(model);
	}
}

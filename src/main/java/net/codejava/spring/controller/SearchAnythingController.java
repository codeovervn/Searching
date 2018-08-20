package net.codejava.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.spring.dao.SearchAnythingDAO;
import net.codejava.spring.model.SearchAnything;

/**
 * This controller routes accesses to the application to the appropriate
 * hanlder methods. 
 * @author DungLT
 *
 */
@Controller
public class SearchAnythingController {

	/** Create */
	// DungLT ADD 2018/08/18 Start
	@Autowired
	SearchAnythingDAO searchAnythingDAO;

	@RequestMapping(value="/")
	public ModelAndView init(ModelAndView model) throws IOException{
		List<SearchAnything> listSearchAnything = searchAnythingDAO.list();
		model.addObject("listSearchAnything", listSearchAnything);
		List<SearchAnything> listSearchAnythingBk = searchAnythingDAO.listBk();
		model.addObject("listSearchAnythingBk", listSearchAnythingBk);
		model.setViewName("SearchAnything");
		SearchAnything searchAnything = new SearchAnything();
		model.addObject("searchAnything", searchAnything);

		return model;
	}

	/*@RequestMapping(value = "/saveSearchAnything", method = RequestMethod.POST)
	public ModelAndView saveSearchAnything(@ModelAttribute SearchAnything searchAnything) {
		searchAnythingDAO.saveOrUpdate(searchAnything);
		return new ModelAndView("redirect:/");
	}*/

	@RequestMapping(value = "/skipSearchAnything", method = RequestMethod.GET)
	public ModelAndView skipSearchAnything(HttpServletRequest request) {
		int searchAnythingId = Integer.parseInt(request.getParameter("id"));
		searchAnythingDAO.skip(searchAnythingId);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/deleteSearchAnything", method = RequestMethod.GET)
	public ModelAndView deleteSearchAnything(HttpServletRequest request) {
		int searchAnythingId = Integer.parseInt(request.getParameter("id"));
		searchAnythingDAO.delete(searchAnythingId);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/revertSearchAnything", method = RequestMethod.GET)
	public ModelAndView revertSearchAnything(HttpServletRequest request) {
		int searchAnythingId = Integer.parseInt(request.getParameter("id"));
		searchAnythingDAO.revert(searchAnythingId);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/copySearchAnything", method = RequestMethod.GET)
	public ModelAndView copySearchAnything(HttpServletRequest request) {
		int searchAnythingId = Integer.parseInt(request.getParameter("id"));
		searchAnythingDAO.copy(searchAnythingId);
		return new ModelAndView("redirect:/");
	}

	/*@RequestMapping(value = "/editSearchAnything", method = RequestMethod.GET)
	public ModelAndView editSearchAnything(HttpServletRequest request) {
		int searchAnythingId = Integer.parseInt(request.getParameter("id"));
		SearchAnything searchAnything = searchAnythingDAO.get(searchAnythingId);
		ModelAndView model = new ModelAndView("SearchAnythingForm");
		model.addObject("searchAnything", searchAnything);

		return model;
	}

	@RequestMapping(value = "/addSearch", method = RequestMethod.GET)
	public ModelAndView addSearch(ModelAndView model) {
		SearchAnything searchAnything = new SearchAnything();
		model.addObject("searchAnything", searchAnything);
		model.setViewName("SearchAnythingForm");

		return model;
	}*/
	//SearchAnything
	// DungLT ADD 2018/08/18 End
}

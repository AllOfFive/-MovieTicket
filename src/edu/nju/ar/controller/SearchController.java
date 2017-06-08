package edu.nju.ar.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;
//import edu.nju.ar.service.SearchService;

@Controller
public class SearchController {
	
//	@Autowired
//	private SearchService searchService;
	
	
	@RequestMapping("/searchMoive")
	public String searchMovie(HttpServletRequest request,HttpSession session) throws IOException{

		String name=request.getParameter("movieName");
		String date=request.getParameter("movieDate");
		System.out.println(date);
		movie m=new movie();
		m.setId(1);
		m.setName(name);
		m.setLast(100);
		m.setType("传奇");
		m.setScore(9.6);
//		List<Hcheck> checks=new ArrayList<Hcheck>();
//		for(int i=0;i<6;i++){
//			checks.add(new Hcheck(""+i,i,i));
//		}
		session.setAttribute("date", date);
		session.setAttribute("movie", m);
        return "index";
		
	}
	

}
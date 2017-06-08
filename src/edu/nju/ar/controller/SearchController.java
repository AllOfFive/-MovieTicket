package edu.nju.ar.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.ar.model.MovieInfo;
import edu.nju.ar.model.MovieResult;
//import edu.nju.ar.service.SearchService;
import edu.nju.ar.service.impl.SearchServiceImpl;


@Controller
public class SearchController {
	
	@Autowired
	private SearchServiceImpl searchService;
	
	
	@RequestMapping("/searchMoive")
	public String searchMovie(HttpServletRequest request,HttpSession session) throws IOException{
//		searchService.test();
		
		String name=request.getParameter("movieName");
		System.out.println(name);
		List<MovieResult> movie=new ArrayList<MovieResult>();
		movie=searchService.getResult(name);
		System.out.println(movie.isEmpty());
		MovieInfo movieInfo=new MovieInfo();
		if(movie.isEmpty()){
			movieInfo.setName("暂无");
			movieInfo.setLast(0);
			movieInfo.setScore(0);
			movieInfo.setType("暂无");
			
		}else{
			MovieResult item=movie.get(0);
			movieInfo.setName(item.getName());
			movieInfo.setLast(item.getLast());
			movieInfo.setScore(getDouble2(item.getScore()));
			movieInfo.setType(item.getType());
		}
		
		
		
		session.setAttribute("movieInfo", movieInfo);
		session.setAttribute("movie", movie);
        return "index";
		
	}
	
	public static double getDouble2(double d){
		DecimalFormat df = new DecimalFormat("0.00");
		double dd=d;
		double db = Double.parseDouble(df.format(dd));
		System.out.println("double:"+db);
		return db;
	}
	
	

}
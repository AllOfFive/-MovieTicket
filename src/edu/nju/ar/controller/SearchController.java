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

import edu.nju.ar.model.CinemaInfo;
import edu.nju.ar.model.Detail;
import edu.nju.ar.model.MovieInfo;
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
		List<MovieInfo> movieList=new ArrayList<MovieInfo>();
		movieList=searchService.getMovieList(name);
//		session.setAttribute("movieList", movieList);
//		return "movieList";
		if(!movieList.isEmpty()){
			session.setAttribute("movieList", movieList);
			return "movieList";
		}else{
			List<CinemaInfo> cinemaList=new ArrayList<CinemaInfo>();
			cinemaList=searchService.getCinemaListByName(name);
			session.setAttribute("newCinemaList", cinemaList);
			return "newCinemaList";
		}
		
	}
	@RequestMapping("/searchCinemaList")
	public String searchMovieList(HttpServletRequest request,HttpSession session){
		int id=Integer.parseInt(request.getParameter("movieId"));
		session.setAttribute("movieId",id);
		System.out.println(id);
		List<CinemaInfo> cinemaList=new ArrayList<CinemaInfo>();
		cinemaList=searchService.getCinemaList(id);
		session.setAttribute("cinemaList", cinemaList);
		return "cinemaList";
	}
	
	@RequestMapping("/searchDetail")
	public String searchDetail(HttpServletRequest request,HttpSession session){
		int mid=Integer.parseInt(request.getParameter("movieId"));
		int cid=Integer.parseInt(request.getParameter("cinemaId"));
		System.out.println(mid+"!!!!!");
		
		MovieInfo movie=searchService.getMovieDetail(mid);
		CinemaInfo cinema=searchService.getCinemaDetail(cid);
		
		session.setAttribute("movieDetail", movie);
		session.setAttribute("cinemaDetail", cinema);
		
		List<Detail> detail=new ArrayList<>();
		detail=searchService.getDetail(mid,cid);
		session.setAttribute("detail", detail);
		if(!detail.isEmpty())
			session.setAttribute("today", detail.get(0).getDate());
		return "detail";
		
	}
	
	@RequestMapping("/searchMoviesInCinema")
	public String searchMoviesInCinema(HttpServletRequest request,HttpSession session){
		int id=Integer.parseInt(request.getParameter("cinemaId"));
		session.setAttribute("newCinemaId",id);
		System.out.println(id);
		List<MovieInfo> movieList=new ArrayList<MovieInfo>();
		movieList=searchService.getMovieListByCinemaId(id);
		session.setAttribute("newMovieList", movieList);
		return "newMovieList";
	}
	
	public static double getDouble2(double d){
		DecimalFormat df = new DecimalFormat("0.00");
		double dd=d;
		double db = Double.parseDouble(df.format(dd));
		System.out.println("double:"+db);
		return db;
	}
	
	

}
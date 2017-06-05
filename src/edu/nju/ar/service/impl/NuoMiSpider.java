package edu.nju.ar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nju.ar.model.Rule;
import edu.nju.ar.model.UrlVO;



public class NuoMiSpider {

	private static final String BASE_URL="http://dianying.nuomi.com";
	
	private static final String INDEX_URL_PATH="/index";
	
	private static final String MOVIE_INFO_URL_PATH="/movie/detail?movieId=";
	
	private static final String CINEMA_INFO_URL_PATH="/cinema/cinemadetail?cinemaId=";
	
	private static final String CINEMA_URL_PATH="/cinema?pagelets[]=pageletCinema&pageNum=1&sortType=2";
	
	private static final String MOVIE_CSS_PATH="div.widget-home-index-list:first-child div.img";
	
	private static final String CINEMA_CSS_PATH="p a";
	
	private static final String CINEMA_MOVIE_CSS_PATH="li.right";
	
	
	public UrlVO getAllUrl(){
		UrlVO vo=new UrlVO();
		
		List<String> moviesId=getMoviesId();
		List<String> cinemasId=getCinemasId();
		
		vo.cinemaInfos=getCinemaInfoUrl(cinemasId);
		vo.movieInfos=getMovieInfoUrl(moviesId);
		vo.shopping=getShoppingUrl(cinemasId);
		return vo;
	}
	
	
	
	private List<String> getShoppingUrl(List<String> cinemasId){
		List<String> urls=new ArrayList<String>();
		
		for(String cinemaId:cinemasId){
			
			Document doc=ExtractDataService.getDoc("https://mdianying.baidu.com/cinema/detail?cinemaId="+cinemaId);
			Elements results=doc.select("li.right");
			Elements active=doc.select("li.active");
			results.addAll(active);
			for(Element data:results){
				if(data.attr("data-movie-id").length()==0)
					continue;
				String url=BASE_URL+CINEMA_INFO_URL_PATH+cinemaId+"&movieId="+data.attr("data-movie-id");
				urls.add(url);
				
			}
			
			
		}
		
		return urls;
	}
	
	/**
	 * 获取所有电影id
	 * @return
	 */
	private List<String> getCinemasId(){
		
		Rule rule=new Rule(BASE_URL+CINEMA_URL_PATH, null, null, CINEMA_CSS_PATH, Rule.SELECTION, Rule.GET);
		ExtractDataService tool=new ExtractDataService();
		Elements results=tool.getElements(rule);
		List<String> urls=new ArrayList<String>();
		for(Element data:results){
			
			urls.add(getIdFromJson(data.attr("data-data")));
			
		}
		
		return urls;
	}
	
	private List<String> getCinemaInfoUrl(List<String> cinemasId){
		
		List<String> urls=new ArrayList<String>();	
		for(String cinemaId:cinemasId){
			urls.add(BASE_URL+CINEMA_INFO_URL_PATH+cinemaId);
		}
		return urls;
	}
	
	/**
	 * 从首页爬取电影id
	 * @return
	 */
	private List<String> getMoviesId(){
		//获取电影的url
		Rule rule=new Rule(BASE_URL+INDEX_URL_PATH, null, null, MOVIE_CSS_PATH, Rule.SELECTION, Rule.GET);
		ExtractDataService tool=new ExtractDataService();
		Elements results=tool.getElements(rule);
		List<String> urls=new ArrayList<String>();
		for(Element data:results){
			
			urls.add(getIdFromJson(data.attr("data-data")));
		}
		return urls;
	}
	
	private String getIdFromJson(String json){
		int index=json.lastIndexOf(':');
		return json.substring(index+1,json.length()-1);
	}
	
	/**
	 * 根据id获取电影信息url
	 * @param moviesId
	 * @return
	 */
	private List<String> getMovieInfoUrl(List<String> moviesId){
		List<String> urls=new ArrayList<String>();	
		for(String movieId:moviesId){
			urls.add(BASE_URL+MOVIE_INFO_URL_PATH+movieId);
		}
		return urls;
	}
	
	public static void main(String[] args) {
		NuoMiSpider s=new NuoMiSpider();
		
		
		s.getShoppingUrl(s.getCinemasId());
	}
}

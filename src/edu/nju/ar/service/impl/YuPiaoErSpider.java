package edu.nju.ar.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nju.ar.model.Rule;
import edu.nju.ar.model.UrlVO;


public class YuPiaoErSpider {

	private static final String BASE_URL="http://m.wepiao.com";
	
	private static final String MOVIE_URL_CSS_PATH="a.movie-item[href]";
	
	private static final String CINEMA_URL_CSS_PATH="a.cinema";
	
	private static final String MOVIE_URL_PATH="/movies/";
	
	private static final String CINEMA_URL_PATH="/cinemas/";
	
	private static final String MOVIE_CINIMA_URL_PATH="/cinemas?movieId=";
	
	
	private static final String CINEMA_ID_FILE="cinemasId.txt";
	
	
	
	/**
	 * 获取所有url
	 * @return
	 */
	public UrlVO getAllUrl(){
		UrlVO url=new UrlVO();
		
		List<String> cinemasId=new ArrayList<String>();
		cinemasId=getCinemasId();
		
		List<String> moviesId=getMovieIds();
		
		url.movieInfos=getMovieInfoUrl(moviesId);
		url.cinemaInfos=getCinemaInfoUrl(cinemasId);
		url.shopping=getShoppingUrl(moviesId,cinemasId);
		
		return url;
	}
	
	public static void main(String[] args) {
		YuPiaoErSpider s=new YuPiaoErSpider();
		s.getCinemasId();
	}
	
	private List<String> getCinemasId(){
		List<String> list=new ArrayList<String>();
		
		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(CINEMA_ID_FILE)));
			while(true){
				String id=reader.readLine();
				if(id==null||id.length()==0)
					break;
				
				list.add(id);
				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 获取正上映电影id
	 * @return
	 */
	private List<String> getMovieIds(){
		//获取电影的url
		Rule rule=new Rule(BASE_URL, null, null, MOVIE_URL_CSS_PATH, Rule.SELECTION, Rule.GET);
		ExtractDataService tool=new ExtractDataService();
		Elements results=tool.getElements(rule);
		List<String> urls=new ArrayList<String>();
		for(Element data:results){
			urls.add(getMovieIdFromUrl(data.attr("href")));
			
		}
		return urls;
	}
	
	/**
	 * 根据电影id获取电影信息url
	 * @param moviesId
	 * @return
	 */
	private List<String> getMovieInfoUrl(List<String> moviesId){
		List<String> list=new ArrayList<String>();
		for(String movieId:moviesId){
			String url=BASE_URL+MOVIE_URL_PATH+movieId;
			list.add(url);
		}
		
		return list;
	}
	
	/**
	 * 根据电影院id获取电影院信息url
	 * @param cinemaIdSet
	 * @return
	 */
	private List<String> getCinemaInfoUrl(List<String> cinemaIdSet){
		List<String> list=new ArrayList<String>();
		
		Iterator<String> iterator=cinemaIdSet.iterator();
		
		while(iterator.hasNext()){
			String url=BASE_URL+CINEMA_URL_PATH+iterator.next();
			list.add(url);
		}
		
		return list;
	}
	
	/**
	 * 根据电影id和电影院id获取购买电影url
	 * @param cinemaMovieMap
	 * @return
	 */
	private List<String> getShoppingUrl(List<String> moviesId,List<String> cinemasId){
		List<String> list=new ArrayList<String>();
		
		Iterator<String> iterator=moviesId.iterator();
		while(iterator.hasNext()){
			String movieId=iterator.next();
			
			for(String cinemaId:cinemasId){
				String url=BASE_URL+CINEMA_URL_PATH+cinemaId+"?movieId="+movieId;
				list.add(url);
			}
			
		}
		
		return list;
	}
	
	private String getMovieIdFromUrl(String url){
		int index=url.lastIndexOf('/');
		return url.substring(index+1);
	}
	
	
	
	
	
	
	
	
}

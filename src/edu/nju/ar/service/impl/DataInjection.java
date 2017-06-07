package edu.nju.ar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ar.mapper.baiduInsert;
import edu.nju.ar.mapper.yupiaoerInsert;
import edu.nju.ar.model.UrlVO;
import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

@Service
public class DataInjection {
	
	@Autowired
	private yupiaoerInsert yi;
	@Autowired
	private baiduInsert bi;
	@Autowired
	private ExtractDataForNumMi nuoMi;
	@Autowired
	private ExtractDataFromYupiaoer yuPiaoEr;
	@Autowired
	private NuoMiSpider ns;
	@Autowired
	private YuPiaoErSpider ys;
	
	
	private UrlVO nuomiUrls;
	private UrlVO yupiaoerUrls;
	
	
	public void insertMovieForYupiaoer() {
		if(yupiaoerUrls==null)
			yupiaoerUrls=ys.getAllUrl();
		List<String> movieUrls = yupiaoerUrls.movieInfos;
		for(String url:movieUrls) {
			movie m = yuPiaoEr.extractMovieInfo(url);
			yi.InsertMovie(m);
		}	
	}
	
	public void insertCinemaForYupiaoer() {
		if(yupiaoerUrls==null)
			yupiaoerUrls=ys.getAllUrl();
		List<String> cinemaUrls = yupiaoerUrls.cinemaInfos;
		for(String url:cinemaUrls) {
			cinema c = yuPiaoEr.extractCinemaInfo(url);
			yi.InsertCinema(c);
		}
	}
	
	public void insertSessionForYupiaoer() {
		if(yupiaoerUrls==null)
			yupiaoerUrls=ys.getAllUrl();
		
		List<String> sessionUrls = yupiaoerUrls.shopping;
		for(String url:sessionUrls) {
			List<session> sessions = yuPiaoEr.extractMovieInfoForEachCinema(url);
			if(sessions==null) continue;
			for(session s:sessions) {
				yi.InsertSession(s);
			}
		}
	}
	
	public void insertMovieForNuoMi() {
		if(nuomiUrls==null)
			nuomiUrls=ns.getAllUrl();
		List<String> movieUrls = nuomiUrls.movieInfos;
		for(String url:movieUrls) {
			movie m = nuoMi.extractMovieInfo(url);
			bi.InsertMovie(m);
		}	
	}
	
	public void insertCinemaForNuoMi() {
		if(nuomiUrls==null)
			nuomiUrls=ns.getAllUrl();
		List<String> cinemaUrls = nuomiUrls.cinemaInfos;
		for(String url:cinemaUrls) {
			cinema c = nuoMi.extractCinemaInfo(url);
			bi.InsertCinema(c);
		}
	}
	
	public void insertSessionForNuoMi() {
		if(nuomiUrls==null)
			nuomiUrls=ns.getAllUrl();
		List<String> sessionUrls = nuomiUrls.shopping;
		for(String url:sessionUrls) {
			List<session> sessions = nuoMi.extractMovieInfoForEachCinema(url);
			if(sessions==null) continue;
			for(session s:sessions) {
				bi.InsertSession(s);
			}
		}
	}
}

package edu.nju.ar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.nju.ar.mapper.baiduInsert;
import edu.nju.ar.mapper.yupiaoerInsert;
import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

public class DataInjection {
	
	@Autowired
	private yupiaoerInsert yi;
	@Autowired
	private baiduInsert bi;
	
	public void insertMovieForYupiaoer(List<movie> list) {
		for(movie m:list) {
			yi.InsertMovie(m);
		}	
	}
	
	public void insertCinemaForYupiaoer(List<cinema> list) {
		for(cinema c:list) {
			yi.InsertCinema(c);
		}	
	}
	
	public void insertSessionForYupiaoer(List<session> list) {
		for(session s:list) {
			yi.InsertSession(s);
		}	
	}
	
	public void insertMovieForNuoMi(List<movie> list) {
		for(movie m:list) {
			bi.InsertMovie(m);
		}	
	}
	
	public void insertCinemaForNuoMi(List<cinema> list) {
		for(cinema c:list) {
			bi.InsertCinema(c);
		}	
	}
	
	public void insertSessionForNuoMi(List<session> list) {
		for(session s:list) {
			bi.InsertSession(s);
		}	
	}
}

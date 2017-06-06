package edu.nju.ar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import edu.nju.ar.mapper.UserMapper;
import edu.nju.ar.mapper.dataGetter;
import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

public class DataIntegrator {

	@Resource
	public dataGetter getter;
	
	public void generateBasicData(){
		List<movie> basicMovie = getter.getAllYupiaoerMovie();
		List<cinema> basicCinema = getter.getAllYupiaoerCinema();
		List<session> basicSession = getter.getAllYupiaoerSession();
	}
	
	public void mixBaiduData(){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

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
		List<movie> yMovie = getter.getAllYupiaoerMovie();
		List<cinema> yCinema = getter.getAllYupiaoerCinema();
		List<session> ySession = getter.getAllYupiaoerSession();
		
		List<movie> bMovie = getter.getAllBaiduMovie();
		List<cinema> bCinema = getter.getAllBaiduCinema();
		List<session> bSession = getter.getAllBaiduSession();
		
		List<movie> tMovie = getter.getAllTaopiaopiaoMovie();
		List<cinema> tCinema = getter.getAllTaopiaopiaoCinema();
		List<session> tSession = getter.getAllTaopiaopiaoSession();
	}
	
	public double getAverageScore(int a,int b ,int c){
		return 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

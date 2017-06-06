package edu.nju.ar.mapper;

import java.util.List;

import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

public interface dataGetter {

	public List<movie> getAllYupiaoerMovie();
	
	public List<movie> getAllTaopiaopiaoMovie();
	
	public List<movie> getAllBaiduMovie();
	
	public List<cinema> getAllYupiaoerCinema();
	
	public List<cinema> getAllTaopiaopiaoCinema();
	
	public List<cinema> getAllBaiduCinema();
	
	public List<session> getAllYupiaoerSession();
	
	public List<session> getAllTaopiaopiaoSession();

	public List<session> getAllBaiduSession();

}

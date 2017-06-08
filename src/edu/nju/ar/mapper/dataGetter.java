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

	public movie getYupiaoerMovieByMid(int mid);
	
	public cinema getYupiaoerCinemaByCid(int cid);
	
    public movie getTaopiaopiaoMovieByMid(int mid);
	
	public cinema getTaopiaopiaoCinemaByCid(int cid);
	
    public movie getBaiduMovieByMid(int mid);
	
	public cinema getBaiduCinemaByCid(int cid);
	
	public movie getFinalMovieByName(String name);
	
	public cinema getFinalCinemaByName(String name);
}

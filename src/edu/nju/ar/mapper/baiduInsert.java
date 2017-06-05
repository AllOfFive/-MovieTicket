package edu.nju.ar.mapper;

import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

public interface baiduInsert {

	public void InsertMovie(movie m);
	
	public void InsertCinema(cinema c);
	
	public void InsertSession(session s);
}

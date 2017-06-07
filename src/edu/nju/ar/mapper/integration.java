package edu.nju.ar.mapper;

import org.apache.ibatis.annotations.Param;

import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;

public interface integration {

	public void addMovie(@Param("movie")movie m,@Param("maxScore")double maxScore,@Param("minScore")double minScore,@Param("avgScore")double avgScore);
	
	public void addCinema(@Param("cinema")cinema c,@Param("yHas")int yHas,@Param("tHas")int tHas,@Param("bHas")int bHas);
	
	public void addSession(@Param("session")session s,@Param("yPrice")double yPrice,@Param("tPrice")double tPrice,@Param("bPrcie")double bPrice);
	
	
}

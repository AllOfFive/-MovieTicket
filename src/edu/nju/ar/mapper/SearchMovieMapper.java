package edu.nju.ar.mapper;

import java.util.List;

import edu.nju.ar.model.CinemaInfo;
import edu.nju.ar.model.Detail;
import edu.nju.ar.model.MovieInfo;
import edu.nju.ar.model.MovieResult;

public interface SearchMovieMapper {
	public List<MovieResult> searchMovieByName(String name);

	public List<MovieInfo> searchMovieListByName(String name);

	public List<CinemaInfo> searchCinemaListById(int id);

	public List<Detail> searchDetailByMidAndCid(int mid, int cid);

	public MovieInfo searchMoiveById(int mid);

	public CinemaInfo searchCinemaById(int cid);
}

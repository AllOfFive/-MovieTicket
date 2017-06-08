package edu.nju.ar.mapper;

import java.util.List;

import edu.nju.ar.model.MovieResult;

public interface SearchMovieMapper {
	public List<MovieResult> searchMovieByName(String name);
}

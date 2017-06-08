package edu.nju.ar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.ar.mapper.SearchMovieMapper;
import edu.nju.ar.model.MovieResult;

//import edu.nju.ar.services.SearchService;

@Service
@Transactional
public class SearchServiceImpl{
	@Resource
	public SearchMovieMapper smm;

	public List<MovieResult> getResult(String name) {
		// TODO Auto-generated method stub
		
		return smm.searchMovieByName(name);
	}
	
}

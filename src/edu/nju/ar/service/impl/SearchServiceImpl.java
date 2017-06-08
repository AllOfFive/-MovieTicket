package edu.nju.ar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.ar.mapper.SearchMovieMapper;
import edu.nju.ar.model.CinemaInfo;
import edu.nju.ar.model.Detail;
import edu.nju.ar.model.MovieInfo;
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

	public List<MovieInfo> getMovieList(String name) {
		// TODO Auto-generated method stub
		
		return smm.searchMovieListByName(name);
	}

	public List<CinemaInfo> getCinemaList(int id) {
		// TODO Auto-generated method stub
		return smm.searchCinemaListById(id);
	}

	public List<Detail> getDetail(int mid, int cid) {
		// TODO Auto-generated method stub
		return smm.searchDetailByMidAndCid(mid,cid);
	}

	public MovieInfo getMovieDetail(int mid) {
		// TODO Auto-generated method stub
		return smm.searchMoiveById(mid);
	}

	public CinemaInfo getCinemaDetail(int cid) {
		// TODO Auto-generated method stub
		return smm.searchCinemaById(cid);
	}
	
}

package edu.nju.ar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.nju.ar.mapper.UserMapper;
import edu.nju.ar.mapper.dataGetter;
import edu.nju.ar.mapper.integration;
import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;
import edu.nju.ar.tools.Similarity;

@Service
public class DataIntegrator {

	@Resource
	private dataGetter getter;
	@Resource
	private integration integrator;
	
	private Similarity s = new Similarity();
	
	public void generateMovieData(){
		List<movie> yMovie = getter.getAllYupiaoerMovie();
		List<movie> bMovie = getter.getAllBaiduMovie();
		List<movie> tMovie = getter.getAllTaopiaopiaoMovie();
		
		for(movie m:yMovie){
			movie finalMovie = new movie();
			finalMovie.setName(m.getName());
			System.out.println(finalMovie.getName());
			finalMovie.setLast(m.getLast());
			finalMovie.setType(m.getType());
			finalMovie.setScore(0);
			double score1=m.getScore();
			double score2=0;
			double score3=0;
			for(movie m2:bMovie){
				if(m2.getName().equals(m.getName())){
					score2=m2.getScore();
				    break;
				}
			}
			for(movie m3:tMovie){
				if(m3.getName().equals(m.getName())){
					score3=m3.getScore();
					break;
				}
			}
			double avgScore=getAverageScore(score1,score2,score3);
			double minScore=getMinScoreBut0(score1,score2,score3);
			double maxScore=Math.max(Math.max(score1, score2), Math.max(score2, score3));
			
			integrator.addMovie(finalMovie, maxScore, minScore, avgScore);
		}
	}
	

	public void generateCinemaAndSessionData(){
		List<session> ySession = getter.getAllYupiaoerSession();
		List<session> tSession = getter.getAllTaopiaopiaoSession();
		List<session> bSession = getter.getAllBaiduSession();
		//娱票儿有的场次和场次中出现的电影先加进去
		for(session s1:ySession){
			int yHas=1;
			int tHas=0;
			int bHas=0;
			double yPrice=s1.getPrice();
			double tPrice=0;
			double bPrice=0;
			session finalSession=new session();
			cinema c1=getter.getYupiaoerCinemaByCid(s1.getCinemaId());
			movie m1=getter.getYupiaoerMovieByMid(s1.getMovieId());
			
			System.out.println(s1.getHall());
			System.out.println(s1.getDate());
			System.out.println(c1.getName());
			System.out.println(m1.getName());
			
			finalSession.setHall(s1.getHall());
			finalSession.setDate(s1.getDate());
			finalSession.setStartAt(s1.getStartAt());
			finalSession.setEndAt(s1.getEndAt());
			finalSession.setMovieId(getter.getFinalMovieByName(m1.getName()).getId()); //movie信息已经集成，通过name获取finalMovie表中的id
			
			for(session s2:tSession){
				cinema c2 = getter.getTaopiaopiaoCinemaByCid(s2.getCinemaId());
				if(s2.getDate().equals(s1.getDate())&&
						s2.getStartAt().equals(s1.getStartAt())&&s2.getEndAt().equals(s1.getEndAt())
						&&s.getSimilarityRatio(c1.getAddress(),c2.getAddress())>0.8){		
							tHas=1;
							tPrice=s2.getPrice();
							break;
						}
			}
			
			for(session s3:bSession){
				cinema c3=getter.getBaiduCinemaByCid(s3.getCinemaId());
				System.out.println(c3.getName());
				if(s3.getDate().equals(s1.getDate())&&
						s3.getStartAt().equals(s1.getStartAt())&&s3.getEndAt().equals(s1.getEndAt())
						&&s.getSimilarityRatio(c1.getAddress(), c3.getAddress())>0.8){
							bHas=1;
							bPrice=s3.getPrice();
							break;
						}
			}
			//此处缺少一个去重电影院的方法
			integrator.addCinema(c1, yHas, tHas, bHas);
			finalSession.setCinemaId(getter.getFinalCinemaByName(c1.getName()).getId());
			integrator.addSession(finalSession, yPrice, tPrice, bPrice);
		}
		
		//娱票儿中没有的淘票票有的电影院和场次
		for(session s1:tSession){
			int yHas=0;
			int tHas=1;
			int bHas=0;
			double yPrice=0;
			double tPrice=s1.getPrice();
			double bPrice=0;
			session finalSession=new session();
			cinema c1=getter.getTaopiaopiaoCinemaByCid(s1.getCinemaId());
			movie m1=getter.getTaopiaopiaoMovieByMid(s1.getMovieId());
			
			finalSession.setHall(s1.getHall());
			finalSession.setDate(s1.getDate());
			finalSession.setStartAt(s1.getStartAt());
			finalSession.setEndAt(s1.getEndAt());
			finalSession.setMovieId(getter.getFinalMovieByName(m1.getName()).getId()); //movie信息已经集成，通过name获取finalMovie表中的id
			
			//判断娱票儿中没有
			for(session s2:ySession){
				cinema c2 =getter.getYupiaoerCinemaByCid(s2.getCinemaId());
				if(s2.getDate().equals(s1.getDate())&&
						s2.getStartAt().equals(s1.getStartAt())&&s2.getEndAt().equals(s1.getEndAt())
						&&s.getSimilarityRatio(c1.getAddress(),c2.getAddress())>0.8){
					        yHas=1;
							break;
						}
			}
			//如果娱票儿已经有了上面已经加入了，break；
			if(yHas==1){
				break;
			}
			
			for(session s3:bSession){
				cinema c3 =getter.getBaiduCinemaByCid(s3.getCinemaId());
				if(s3.getDate().equals(s1.getDate())&&
						s3.getStartAt().equals(s1.getStartAt())&&s3.getEndAt().equals(s1.getEndAt())
						&&s.getSimilarityRatio(c1.getAddress(),c3.getAddress())>0.8){
							bHas=1;
							bPrice=s3.getPrice();
							break;
						}
			}
			integrator.addCinema(c1, yHas, tHas, bHas);
			finalSession.setCinemaId(getter.getFinalCinemaByName(c1.getName()).getId());
			integrator.addSession(finalSession, yPrice, tPrice, bPrice);
		}
		
		      //娱票儿中没有的百度有的电影院和场次
				for(session s1:bSession){
					int yHas=0;
					int tHas=0;
					int bHas=1;
					double yPrice=0;
					double tPrice=0;
					double bPrice=s1.getPrice();
					session finalSession=new session();
					cinema c1=getter.getBaiduCinemaByCid(s1.getCinemaId());
					movie m1=getter.getBaiduMovieByMid(s1.getMovieId());
					
					finalSession.setHall(s1.getHall());
					finalSession.setDate(s1.getDate());
					finalSession.setStartAt(s1.getStartAt());
					finalSession.setEndAt(s1.getEndAt());
					finalSession.setMovieId(getter.getFinalMovieByName(m1.getName()).getId()); //movie信息已经集成，通过name获取finalMovie表中的id
					
					//判断娱票儿中没有
					for(session s2:ySession){
						cinema c2 = getter.getYupiaoerCinemaByCid(s2.getCinemaId());
						if(s2.getDate().equals(s1.getDate())&&
								s2.getStartAt().equals(s1.getStartAt())&&s2.getEndAt().equals(s1.getEndAt())
								&&s.getSimilarityRatio(c1.getAddress(),c2.getAddress())>0.8){
							        yHas=1;
									break;
								}
					}
					//如果娱票儿已经有了上面已经加入了，break；
					if(yHas==1){
						break;
					}
					
					for(session s3:tSession){
						cinema c3 = getter.getTaopiaopiaoCinemaByCid(s3.getCinemaId());
						if(s3.getDate().equals(s1.getDate())&&
								s3.getStartAt().equals(s1.getStartAt())&&s3.getEndAt().equals(s1.getEndAt())
								&&s.getSimilarityRatio(c1.getAddress(),c3.getAddress())>0.8){
									tHas=1;
									tPrice=s3.getPrice();
									break;
								}
					}
					integrator.addCinema(c1, yHas, tHas, bHas);
					finalSession.setCinemaId(getter.getFinalCinemaByName(c1.getName()).getId());
					integrator.addSession(finalSession, yPrice, tPrice, bPrice);
				}
	}
	
	public double getAverageScore(double a,double b ,double c){
		double sum=a+b+c;
		int count=3;
		if(a==0)
			count--;
		if(b==0)
			count--;
		if(c==0)
			count--;
		if(count!=0)
			return sum/count;
		else
		    return 0;
	}
	
	public double getMinScoreBut0(double a,double b,double c){
			if(a==0){
				if(b==0){
					return c;
				}else if(c==0){
					return b;
				}else{
					return Math.min(b, c);
				}
			}
			//a!=0
			if(b==0){
				if(c==0){
					return a;
				}else{
					return Math.min(a, c);
				}
			}
			//a!=0&&b!=0
			if(c==0){
					return Math.min(a, b);
				}
			
		return Math.min(Math.min(a, b), Math.min(c, b));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

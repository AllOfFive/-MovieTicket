package edu.nju.ar.service.impl;

import edu.nju.ar.model.UrlVO;



public class NuoMiTest {
	public static void main(String[] args) {
		NuoMiSpider spider=new NuoMiSpider();
		
		UrlVO url=spider.getAllUrl();
		
		System.out.println("电影url "+url.movieInfos.size()+" 条");
		System.out.println("----------------------------------------------");
		for(String movieInfoUrl:url.movieInfos){
			System.out.println(movieInfoUrl);
		}
		System.out.println("----------------------------------------------");
		
		System.out.println("电影院url "+url.cinemaInfos.size()+" 条");
		System.out.println("----------------------------------------------");
		for(String cinemaInfo:url.cinemaInfos){
			System.out.println(cinemaInfo);
		}
		System.out.println("----------------------------------------------");
		
		System.out.println("购票url "+url.shopping.size()+" 条");
		System.out.println("----------------------------------------------");
		int count=0;
		for(String s:url.shopping){
			if(count>500)
				break;
			count++;
			System.out.println(s);
		}
		System.out.println("----------------------------------------------");
	}
}

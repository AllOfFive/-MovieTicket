package edu.nju.ar.service.impl;

import edu.nju.ar.model.UrlVO;



public class NuoMiTest {
	public static void main(String[] args) {
		NuoMiSpider spider=new NuoMiSpider();
		
		UrlVO url=spider.getAllUrl();
		
		System.out.println("��Ӱurl "+url.movieInfos.size()+" ��");
		System.out.println("----------------------------------------------");
		for(String movieInfoUrl:url.movieInfos){
			System.out.println(movieInfoUrl);
		}
		System.out.println("----------------------------------------------");
		
		System.out.println("��ӰԺurl "+url.cinemaInfos.size()+" ��");
		System.out.println("----------------------------------------------");
		for(String cinemaInfo:url.cinemaInfos){
			System.out.println(cinemaInfo);
		}
		System.out.println("----------------------------------------------");
		
		System.out.println("��Ʊurl "+url.shopping.size()+" ��");
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

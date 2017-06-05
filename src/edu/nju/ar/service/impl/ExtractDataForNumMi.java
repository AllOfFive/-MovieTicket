package edu.nju.ar.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;
import edu.nju.ar.tools.MyJavaScriptEngine;


public class ExtractDataForNumMi {
	/**
	 * 获取电影信息
	 * @param url
	 */
	public static movie extractMovieInfo(String url) {
        Document doc = getDoc(url);
        movie m = new movie();
        int index = url.lastIndexOf("=");
        int mid = Integer.parseInt(url.substring(index+1));
        m.setId(mid);
        Elements elements = doc.select("span.list-name");
        m.setName(elements.get(0).text());
        elements = doc.select("span.score-normal");
        m.setScore(Double.parseDouble(elements.get(0).text()));
        elements = doc.select("p.detail:eq(1)");
        m.setType(elements.get(0).text().substring(3));
        elements = doc.select("p.movie-len");        
        String temp = elements.get(0).text();
        String regex="[^0-9]";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(temp);
        m.setLast(Integer.parseInt(matcher.replaceAll("").trim()));
        System.out.println(m.getId()+" "+m.getLast()+" "+m.getName()+" "+m.getScore()+" "+m.getType());
        return m;
	}
	
	/**
	 * 获取电影院信息
	 * @param url
	 */
	public static cinema extractCinemaInfo(String url) {
        Document doc = getDoc(url);
        cinema c = new cinema();
        int index = url.lastIndexOf("=");
        int cid = Integer.parseInt(url.substring(index+1));
        c.setId(cid);
        Elements es = doc.select("span.cinema-name");
        c.setName(es.get(0).text());
        es = doc.select("p.address");
        c.setAddress(es.get(0).text());
        System.out.println(c.getId()+" "+c.getName()+" "+c.getAddress());
        return c;
	}
	
	/**
	 * 获取每个电影院当天每个电影的票价信息
	 * @param url
	 */
	public static List<session> extractMovieInfoForEachCinema(String url) {
		Document doc = getDoc(url);
		List<session> sessions = new ArrayList<>();

		int index1 = url.indexOf("=");
		int index2 = url.indexOf("&");
		int index3 = url.lastIndexOf("=");
		int cid = Integer.parseInt(url.substring(index1+1, index2));
		int mid = Integer.parseInt(url.substring(index3+1));
		System.out.println(cid);
		System.out.println(mid);
        session session = new session();
        session.setCid(cid);
        session.setMid(mid);
        Elements es = doc.select("li.active");
        if(es.text()!=null) {
        	String str  = es.text();
        	String sub = str.substring(str.length()-5);
        	session.setDate(sub);
        }
       
		Elements results1 = doc.select("div.start");
		if(results1.size()==0) return null;
		
		Elements results2 = doc.select("div.end");
		Elements results3 = doc.select("div.lan");
		Elements results4 = doc.select("div.theater");
		Elements results5 = doc.select("div.price");
		Elements results6 = doc.select("div.price s");
		ArrayList<String> starts = new ArrayList<>();
		ArrayList<String> ends = new ArrayList<>();
		ArrayList<String> props = new ArrayList<>();
		ArrayList<String> halls = new ArrayList<>();
		ArrayList<String> prices = new ArrayList<>();
		ArrayList<String> rubs = new ArrayList<>();
        for (Element result1 : results1)  
        {            	
        	String temp = result1.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	starts.add(temp);
        } 
        for (Element result2 : results2)  
        {            	
        	String temp = result2.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	temp = temp.substring(0,5);
        	ends.add(temp);
        } 
        for (Element result3 : results3)  
        {            	
        	String temp = result3.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	props.add(temp);
        } 
        for (Element result4 : results4)  
        {            	
        	String temp = result4.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	if(temp.indexOf("充足")==-1)
        		halls.add(temp);
        } 
        for (Element result5 : results5)  
        {            	
        	String temp = result5.text();
        	String regex="[^0-9]";
        	Pattern pattern = Pattern.compile(regex);
        	Matcher m = pattern.matcher(temp);   
        	prices.add( m.replaceAll("").trim());
        }       
        
        for(Element result6 : results6)
        {
        	String temp = result6.text().replace(Jsoup.parse("&nbsp;").text(), "");
//        	System.out.println(temp);
        	rubs.add(temp);
        }
        for (int i=0;i<starts.size();i++) {
        	session.setStartAt(starts.get(i));
        	session.setEndAt(ends.get(i));
        	session.setHall(halls.get(i));
        	if(rubs.get(i)!=null){
        		int rubIndex = prices.get(i).indexOf(rubs.get(i));
        		double truePrice = Double.parseDouble(prices.get(i).substring(0, rubIndex));
        		session.setPrice(truePrice);
        	}        	
        	sessions.add(session);
        	System.out.println(session.getStartAt()+"  "+session.getEndAt()+"  "+session.getDate()+"  "+session.getHall()+"  "+session.getPrice());
        }
        return sessions;
	}
	
	/**
	 * 获取url对应的document
	 * @param url
	 * @return
	 */
	private static Document getDoc(String url){
		WebClient webClient =new WebClient(BrowserVersion.INTERNET_EXPLORER);  
//		WebClient webClient =new WebClient(BrowserVersion.CHROME); 
		 webClient.getCookieManager().setCookiesEnabled(true);// 开启cookie管理
	       webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true    
	       webClient.getOptions().setCssEnabled(false); //禁用css支持        
	       webClient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常     
	       webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));//自定义JavaScript引擎，有js错误不打印
	       webClient.getOptions().setTimeout(20000);        

				HtmlPage page=null;
				try {
					page = webClient.getPage(url);
				} catch (FailingHttpStatusCodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
				//我认为这个最重要  
				       String pageXml = page.asXml(); //以xml的形式获取响应文本    
				/**jsoup解析文档*/    
			    Document doc = Jsoup.parse(pageXml, url); 
			    return doc;
	}

	public static void main(String[] args) {
		extractMovieInfoForEachCinema("https://mdianying.baidu.com/cinema/detail?cinemaId=179&movieId=15546");
//		extractMovieInfo("https://mdianying.baidu.com/movie/detail?movieId=15546");
//		extractCinemaInfo("https://mdianying.baidu.com/cinema/detail?cinemaId=179");
	}
}

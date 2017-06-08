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
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.nju.ar.model.cinema;
import edu.nju.ar.model.movie;
import edu.nju.ar.model.session;
import edu.nju.ar.tools.FormatPrice;
import edu.nju.ar.tools.GetDate;
import edu.nju.ar.tools.MyJavaScriptEngine;

@Service
public class ExtractDataForNumMi {
	/**
	 * movie information
	 * @param url
	 */
	public movie extractMovieInfo(String url) {
        Document doc = getDoc(url);
        movie m = new movie();
        int index = url.lastIndexOf("=");
        int mid = Integer.parseInt(url.substring(index+1));
        m.setMovieId(mid);
        Elements elements = doc.select("span.list-name");
        m.setName(elements.get(0).text());
        elements = doc.select("span.score-normal, dd.intrest");
        String tempScore = elements.get(0).text();
        if(tempScore.indexOf("想看")!=-1) {
        	m.setScore(0);
        } else {
        	m.setScore(Double.parseDouble(elements.get(0).text()));
        }
        elements = doc.select("p.detail:eq(1)");
        m.setType(elements.get(0).text().substring(3));
        elements = doc.select("p.movie-len");        
        String temp = elements.get(0).text();
        String regex="[^0-9]";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(temp);
        m.setLast(Integer.parseInt(matcher.replaceAll("").trim()));
        System.out.println(m.getMovieId()+" "+m.getLast()+" "+m.getName()+" "+m.getScore()+" "+m.getType());
        return m;
	}
	
	/**
	 * cinema information 
	 * @param url
	 */
	public cinema extractCinemaInfo(String url) {
        Document doc = getDoc(url);
        cinema c = new cinema();
        int index = url.lastIndexOf("=");
        int cid = Integer.parseInt(url.substring(index+1));
        c.setCinemaId(cid);
        Elements es = doc.select("span.cinema-name");
        c.setName(es.get(0).text());
        es = doc.select("p.address");
        c.setAddress(es.get(0).text());
        System.out.println(c.getCinemaId()+" "+c.getName()+" "+c.getAddress());
        return c;
	}
	
	/**
	 * session information
	 * @param url
	 */
	public List<session> extractMovieInfoForEachCinema(String url) {
		Document doc = getDoc(url);
		List<session> sessions = new ArrayList<>();

		int index1 = url.indexOf("=");
		int index2 = url.indexOf("&");
		int index3 = url.lastIndexOf("=");
		int cid = Integer.parseInt(url.substring(index1+1, index2));
		int mid = Integer.parseInt(url.substring(index3+1));
		System.out.println(cid);
		System.out.println(mid);
        
		
		Elements dateLi=doc.select("div.border-bottom li.active");
		String dateStr=dateLi.get(0).text();
		System.out.println(dateStr);
		if(dateStr==null||!dateStr.contains("今天"))
			return sessions;
       
       String active="div.active ";
		Elements results1 = doc.select(active+"div.start");
		if(results1.size()==0) return null;
		
		Elements results2 = doc.select(active+"div.end");
		Elements results3 = doc.select(active+"div.lan");
		Elements results4 = doc.select(active+"div.theater");
		Elements results5 = doc.select(active+"div.price");
		Elements results6 = doc.select(active+"div.price-info s");
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
        	if(temp.indexOf("余")==-1)
        		halls.add(temp);
        } 
        for (Element result5 : results5)  
        {            	
        	String price = FormatPrice.formatPrice(result5.text());  
        	prices.add(price);
        }       
        
        for(Element result6 : results6)
        {
        	String temp = result6.text().replace(Jsoup.parse("&nbsp;").text(), "");
//        	System.out.println(temp);
        	rubs.add(temp);
        }
        for (int i=0;i<starts.size();i++) {
        	session session = new session();
            session.setCinemaId(cid);
            session.setMovieId(mid);
            session.setDate(GetDate.getDateToday());
        	session.setStartAt(starts.get(i));
        	session.setEndAt(ends.get(i));
        	session.setHall(halls.get(i));
        	if(rubs.size()!=0&&rubs.get(i)!=null&&rubs.get(i).length()!=0){
        		
        		int priceLength=prices.get(i).length()-rubs.get(i).length();
        		if(priceLength<=0){
        			session.setPrice(Double.parseDouble(prices.get(i)));
        			
        		}else{
        			double truePrice = Double.parseDouble(prices.get(i).substring(0,priceLength));
            		session.setPrice(truePrice);
        		}
        		
        	}else {
        		session.setPrice(Double.parseDouble(prices.get(i)));
        	}
        	sessions.add(session);
        	System.out.println(session.getStartAt()+"  "+session.getEndAt()+"  "+session.getDate()+"  "+session.getHall()+"  "+session.getPrice());
        }
        return sessions;
	}
	
	/**
	 * get document
	 * @param url
	 * @return
	 */
	private static Document getDoc(String url){
		WebClient webClient =new WebClient(BrowserVersion.INTERNET_EXPLORER);  
//		WebClient webClient =new WebClient(BrowserVersion.CHROME); 
		 webClient.getCookieManager().setCookiesEnabled(true);
	       webClient.getOptions().setJavaScriptEnabled(true);    
	       webClient.getOptions().setCssEnabled(false);         
	       webClient.getOptions().setThrowExceptionOnScriptError(false);      
	       webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));
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
				 
				       String pageXml = page.asXml(); 
				
			    Document doc = Jsoup.parse(pageXml, url); 
			    return doc;
	}

	public static void main(String[] args) {
		ExtractDataForNumMi e = new ExtractDataForNumMi();
		e.extractMovieInfoForEachCinema("https://mdianying.baidu.com/cinema/detail?cinemaId=3069&active_movie_id=15546");
//		extractMovieInfo("https://mdianying.baidu.com/movie/detail?movieId=15546");
//		extractCinemaInfo("https://mdianying.baidu.com/cinema/detail?cinemaId=179");
	}
}

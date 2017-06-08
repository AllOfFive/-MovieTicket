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
public class ExtractDataFromYupiaoer {

	/**
	 * movie information
	 * @param url
	 */
	public movie extractMovieInfo(String url) {
        Document doc = getDoc(url);
        url = url.substring(7);
        String[] urlArr = url.split("/");
        int mid = Integer.parseInt(urlArr[2]);
        movie m = new movie();
        m.setMovieId(mid);
        Elements elements = doc.select("div.basic-info p span.name");
        m.setName(elements.get(0).text());
        elements = doc.select("span.c_cf616a, span.Score");
        String tempScore = elements.get(0).text();
        int index = tempScore.indexOf("分");
        if (index==-1){
        	m.setScore(0);
        } else {
        	tempScore = tempScore.substring(0, index);
        	m.setScore(Double.parseDouble(tempScore));
        }
        
        elements = doc.select("div.basic-info p:eq(3)");
        m.setType(elements.get(0).text());
        elements = doc.select("div.basic-info p:eq(4)");
        String temp = elements.get(0).text();
        String[] arr = temp.split(" ");
        int index2 = arr[2].indexOf("分钟");
        String lastTime = arr[2].substring(0, index2);
        m.setLast(Integer.parseInt(lastTime));
        System.out.println(m.getMovieId()+" "+m.getLast()+" "+m.getName()+" "+m.getScore()+" "+m.getType());
        return m;
	}
	
	/**
	 * cinema information
	 * @param url
	 */
	public cinema extractCinemaInfo(String url) {
        Document doc = getDoc(url);
        url = url.substring(7);
        String[] arr = url.split("/");
        int cid = Integer.parseInt(arr[2]);
        cinema c = new cinema();
        c.setCinemaId(cid);
        Elements es = doc.select("h3.info-name");
        c.setName(es.get(0).text());
        es = doc.select("p.info-addr");
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
        url = url.substring(7);
		String[] urlArr = url.split("/");
		String tempUrl = urlArr[2];
		int index = tempUrl.indexOf("?movieId=");		
		int cid = Integer.parseInt(tempUrl.substring(0, index));
		int mid = Integer.parseInt(tempUrl.substring(index+9));
		System.out.println(cid);
		System.out.println(mid);
        
       
		Elements results1 = doc.select("div.d-start");
		if(results1.size()==0) return null;
		
		Elements results2 = doc.select("div.d-end");
		Elements results3 = doc.select("div.d-movie-lang");
		Elements results4 = doc.select("div.d-movie-hall");
		Elements results5 = doc.select("div.d-price-calc");
		ArrayList<String> starts = new ArrayList<>();
		ArrayList<String> ends = new ArrayList<>();
		ArrayList<String> props = new ArrayList<>();
		ArrayList<String> halls = new ArrayList<>();
		ArrayList<String> prices = new ArrayList<>();
        for (Element result1 : results1)  
        {            	
        	String temp = result1.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	starts.add(temp);
        } 
        for (Element result2 : results2)  
        {            	
        	String temp = result2.text().replace(Jsoup.parse("&nbsp;").text(), "");
        	int index2 = temp.indexOf(" ");
        	temp = temp.substring(0, index2);
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
        	halls.add(temp);
        } 
        for (Element result5 : results5)  
        {            	
        	String price = FormatPrice.formatPrice(result5.text());  
        	prices.add(price);
        }       
        for (int i=0;i<starts.size();i++) {
        	session session = new session();
            session.setCinemaId(cid);
            session.setMovieId(mid);
            session.setDate(GetDate.getDateToday());
        	session.setStartAt(starts.get(i));
        	session.setEndAt(ends.get(i));
        	session.setHall(halls.get(i));
        	session.setPrice(Double.parseDouble(prices.get(i)));
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
				//����Ϊ�������Ҫ  
				       String pageXml = page.asXml(); //��xml����ʽ��ȡ��Ӧ�ı�    
				/**jsoup�����ĵ�*/    
			    Document doc = Jsoup.parse(pageXml, url); 
			    return doc;
	}

	public static void main(String[] args) {
//		extractMovieInfoForEachCinema("http://m.wepiao.com/cinemas/1011371?movieId=50205");
//		extractMovieInfo("http://m.wepiao.com/movies/50205");
//		extractCinemaInfo("http://m.wepiao.com/cinemas/1010147");
	}
}

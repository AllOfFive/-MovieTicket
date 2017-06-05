package edu.nju.ar.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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

import edu.nju.ar.tools.MyJavaScriptEngine;


public class ExtractDataForNumMi {
	/**
	 * ��ȡ��Ӱ��Ϣ
	 * @param url
	 */
	public static void extractMovieInfo(String url) {
        Document doc = getDoc(url);
        Elements elements = doc.select("h4.subtitle");
        System.out.println(elements.get(0).text());
        elements = doc.select("span.c_cf616a, span.Score");
        System.out.println(elements.get(0).text());
        elements = doc.select("div.basic-info p:eq(3)");
        System.out.println(elements.get(0).text());
        elements = doc.select("div.basic-info p:eq(4)");
        System.out.println(elements.get(0).text());
	}
	
	/**
	 * ��ȡÿ����ӰԺ����õ�Ӱ��Ʊ����Ϣ
	 * @param url
	 */
	public static void extractMovieInfoForEachCinema(String url) {
		Document doc = getDoc(url);
		
        Elements es = doc.select("h3.info-name");
        System.out.println(es.get(0).text());
        es = doc.select("p.info-addr");
        System.out.println(es.get(0).text());
        es = doc.select("li.Tabs__item.active");
        if(es.text()!=null) {
        	String str  = es.text();
        	int i = str.indexOf("(");
        	String sub = str.substring(i+1, str.length()-1);
        	System.out.println(sub);
        }
       
		Elements results1 = doc.select("div.d-start");
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
        	int index = temp.indexOf(" ");
        	temp = temp.substring(0, index);
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
        	String temp = result5.text();
        	String regex="[^0-9]";
        	Pattern pattern = Pattern.compile(regex);
        	Matcher m = pattern.matcher(temp);   
        	prices.add( m.replaceAll("").trim());
        }       
        for (int i=0;i<starts.size();i++) {
        	System.out.println(starts.get(i)+"  "+ends.get(i)+"  "+props.get(i)+"  "+halls.get(i)+"  "+prices.get(i));
        }
	}
	
	/**
	 * ��ȡurl��Ӧ��document
	 * @param url
	 * @return
	 */
	private static Document getDoc(String url){
		WebClient webClient =new WebClient(BrowserVersion.INTERNET_EXPLORER);  
//		WebClient webClient =new WebClient(BrowserVersion.CHROME); 
		 webClient.getCookieManager().setCookiesEnabled(true);// ����cookie����
	       webClient.getOptions().setJavaScriptEnabled(true); //����JS��������Ĭ��Ϊtrue    
	       webClient.getOptions().setCssEnabled(false); //����css֧��        
	       webClient.getOptions().setThrowExceptionOnScriptError(false); //js���д���ʱ���Ƿ��׳��쳣     
	       webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));//�Զ���JavaScript���棬��js���󲻴�ӡ
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

}

package edu.nju.ar.service.impl;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.nju.ar.model.LinkTypeData;
import edu.nju.ar.model.Rule;



public class ExtractDataService {

	public static List<LinkTypeData> extract(Rule rule) {
		List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
		LinkTypeData data = null;

		Elements results=getElements(rule);
		for (Element result : results) {

			data = new LinkTypeData();
			data.setContent(result.attr("href"));
			datas.add(data);
		}

		return datas;
	}
	
	public static Elements getElements(Rule rule){
		Document doc=getDoc(rule.getUrl());
	/*	try {
			BufferedWriter writer=new BufferedWriter(new FileWriter(new File("data.txt")));
			writer.append(doc.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		*///}
		Elements results = new Elements();
		
		results = doc.select(rule.getResultTagName());
			
		System.out.println("-------------------------------------------------");
		System.out.println("all results size=" + results.size());
		System.out.println("-------------------------------------------------");
		return results;
	}
	
	
	public static Document getDoc(String url){
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
		webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		webClient.getOptions().setCssEnabled(false); // 禁用css支持
		webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		webClient.getOptions().setTimeout(20000);
		

		HtmlPage page = null;
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
		// 我认为这个最重要
		String pageXml = page.asXml(); // 以xml的形式获取响应文本
		/** jsoup解析文档 */
		Document doc = Jsoup.parse(pageXml);
		return doc;
	}

}

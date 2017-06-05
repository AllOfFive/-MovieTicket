package edu.nju.ar.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
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
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
		webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		webClient.getOptions().setCssEnabled(false); // 禁用css支持
		webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		webClient.getOptions().setTimeout(20000);

		String url = rule.getUrl();
		String[] params = rule.getParams();
		String[] values = rule.getValues();
		String resultTagName = rule.getResultTagName();
		int type = rule.getType();
		int requestType = rule.getRequestMethod();

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
		Document doc = Jsoup.parse(pageXml, url);

		Elements results = new Elements();
		switch (type) {
		case Rule.CLASS:
			results = doc.getElementsByClass(resultTagName);
			break;
		case Rule.ID:
			Element result = doc.getElementById(resultTagName);
			results.add(result);
			break;
		case Rule.SELECTION:
			results = doc.select(resultTagName);
			break;
		default:
			if (resultTagName == null) {
				results = doc.getElementsByTag("body");
			}
		}
//		System.out.println("-------------------------------------------------");
//		System.out.println("all results size=" + results.size());
//		System.out.println("-------------------------------------------------");
		return results;
	}

}

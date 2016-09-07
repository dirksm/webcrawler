package com.leeward.crawler.web.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leeward.crawler.web.models.SearchResultModel;
import com.leeward.crawler.web.models.URLArrayBlockingQueue;

@Service("searchService")
public class SearchService {
	public static final int READER_QUEUE_SIZE = 50000;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private static Logger log = LoggerFactory.getLogger(SearchService.class);
    private String prefix = "";
	URLArrayBlockingQueue<String> urls = new URLArrayBlockingQueue<>(READER_QUEUE_SIZE);

	
	public List<SearchResultModel> search(String url, String searchWord) {

		TreeSet<SearchResultModel> results = new TreeSet<SearchResultModel>();
		prefix = url;
		
		String currentUrl = url;
		// Retrieve the html document to search
		// First grabbing any urls on the page.
		try {
	        List<SearchResultModel> l = crawl(currentUrl, searchWord);
	        for (SearchResultModel srm : l) {
				results.add(srm);
			}
	        System.err.println("results: " + results.size());
	        while (!urls.isEmpty()) {
				currentUrl = urls.poll();
		        l = crawl(currentUrl, searchWord);
		        for (SearchResultModel srm : l) {
					results.add(srm);
				}
			}
		} catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exception message: "+e.getMessage());
			e.printStackTrace();
//			log.error("Exception searching pages: " + e.getMessage(), e);
		}
		return new ArrayList<SearchResultModel>(results);
	}
	
	private List<SearchResultModel> crawl(String url, String searchWord) throws IOException {
		List<SearchResultModel> results = new ArrayList<SearchResultModel>();
		Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
        Document htmlDocument = connection.get();
        Response resp = connection.response();
        if(resp.statusCode() == 200 && resp.contentType().contains("text/html"))
        {
        	populateURLs(htmlDocument);
        	results = searchPage(url, htmlDocument, searchWord.split(" "));
        }
        return results;
	}
	
	private List<String> getLinks(Document htmlDocument) {
		List<String> links = new ArrayList<String>();
		Elements linksOnPage = htmlDocument.select("a[href]");
        for(Element link : linksOnPage)
        {
            links.add(link.absUrl("href"));
        }		
        return links;
	}
		
	private void populateURLs(Document htmlDocument) {
    	List<String> urlLinks = getLinks(htmlDocument);
    	for (String urlLink : urlLinks) {
    		if (urlLink.startsWith(prefix)) {
    			System.out.println("Adding " + urlLink + " to list of searches.");
        		urls.offer(urlLink);
			}
		}
    	System.out.println("List of urls: " + urls);
	}
	
	private List<SearchResultModel> searchPage(String url, Document htmlDocument, String[] words) {
		List<SearchResultModel> results = new ArrayList<SearchResultModel>();
		String bodyText = htmlDocument.body().text();
		for (String searchWord : words) {
			System.out.println("Searching " + url + " for the following word: " + searchWord);
	        if (StringUtils.lowerCase(bodyText).contains(StringUtils.lowerCase(searchWord))) {
	        	System.out.println("found");
	        	SearchResultModel model = new SearchResultModel();
	        	model.setTitle(htmlDocument.title());
	        	model.setUrl(url);
	        	model.setText(bodyText.substring(Math.max(bodyText.indexOf(searchWord)-150, 0), Math.min(bodyText.indexOf(searchWord)+150, bodyText.length())));
	        	System.out.println(model);
	        	results.add(model);
	        	break;
	        }
		}
		return results;
	}
	
}
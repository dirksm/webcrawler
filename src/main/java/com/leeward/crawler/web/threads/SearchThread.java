package com.leeward.crawler.web.threads;

import java.net.SocketTimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeward.crawler.web.constants.AppConstants;
import com.leeward.crawler.web.models.SearchResultArrayBlockingQueue;
import com.leeward.crawler.web.models.SearchResultModel;
import com.leeward.crawler.web.models.URLArrayBlockingQueue;

public class SearchThread implements Runnable {

	private static Logger log = LoggerFactory.getLogger(SearchThread.class);
	
	protected URLArrayBlockingQueue<String> urls = null;	
	protected SearchResultArrayBlockingQueue<SearchResultModel> results = null;	
	private String searchWord = "";

	public SearchThread(String s, URLArrayBlockingQueue<String> i, SearchResultArrayBlockingQueue<SearchResultModel> o) {
		this.urls = i;
		this.results = o;
		this.searchWord = s;
	}
	
	@Override
	public void run() {
		long s = System.currentTimeMillis();
		log.debug("Searching urls");
		SearchResultModel srm = null;
		String url = "";
		try {
			
			while(true) {
				url = this.urls.poll();
				if (StringUtils.isNotBlank(url)) {
					// If the queue is empty, break from this thread.
					if (AppConstants.END_PROCESSING.equals(url)) {
						this.urls.offer(url);
						break;
					}
					log.debug("Searching url '"+url+"'");
					Connection connection = Jsoup.connect(url).userAgent(AppConstants.USER_AGENT);
			        Document htmlDocument = connection.get();
			        Response resp = connection.response();
			        if(resp.statusCode() == 200 && resp.contentType().contains("text/html"))
			        {
			        	srm = searchPage(url, htmlDocument, this.searchWord);
			        	if (srm != null) {
							this.results.offer(srm);
						}
			        }
				}
			}
			
		} catch (UnsupportedMimeTypeException umte) {
			
		} catch (SocketTimeoutException ste) {
			log.error("Exception connecting to '"+url+"': " + ste.getMessage(),ste);
		} catch (Exception e) {
			log.error("Exception crawling url '"+url+"': "+e.getMessage(),e);
		}
		log.debug("finished search... took " + (System.currentTimeMillis()-s) + "ms to complete.");
	}

	private SearchResultModel searchPage(String url, Document htmlDocument, String words) {
		SearchResultModel result = null;
		htmlDocument.select("a").remove().text();
		String bodyText = htmlDocument.body().text();
        if (StringUtils.lowerCase(bodyText).contains(StringUtils.lowerCase(words))) {
        	result = new SearchResultModel();
        	result.setTitle(htmlDocument.title());
        	result.setUrl(url);
        	result.setText(bodyText.substring(Math.max(bodyText.indexOf(words)-150, 0), Math.min(bodyText.indexOf(words)+150, bodyText.length())));
        }
		return result;
	}
	
}

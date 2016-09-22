package com.leeward.crawler.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leeward.crawler.web.constants.AppConstants;
import com.leeward.crawler.web.models.SearchResultArrayBlockingQueue;
import com.leeward.crawler.web.models.SearchResultModel;
import com.leeward.crawler.web.models.URLArrayBlockingQueue;
import com.leeward.crawler.web.threads.IndexerThread;
import com.leeward.crawler.web.threads.SearchThread;

@Service("searchService")
public class SearchService {
	private static Logger log = LoggerFactory.getLogger(SearchService.class);

	private int threadCount = 10;
	URLArrayBlockingQueue<String> urls = null;
	URLArrayBlockingQueue<String> inurls = null;
	SearchResultArrayBlockingQueue<SearchResultModel> results = null;

	public List<SearchResultModel> execute(String url, String searchWord) {

		try {
			// Populate the queue for urls
			urls = new URLArrayBlockingQueue<String>(AppConstants.READER_QUEUE_SIZE);
			inurls = new URLArrayBlockingQueue<String>(AppConstants.READER_QUEUE_SIZE);
			results = new SearchResultArrayBlockingQueue<SearchResultModel>(AppConstants.READER_QUEUE_SIZE);

			// Submit thread to populate the url's
			ExecutorService fillES = Executors.newFixedThreadPool(threadCount);
			for (int i = 0; i < threadCount; i++) {
				fillES.submit(new IndexerThread(urls, inurls, url));
			}
			
			Thread.sleep(2000);
			
			// Set the end processing flag in the input queue
			urls.offer(AppConstants.END_PROCESSING, 365, TimeUnit.DAYS);

			// Submit threads for searching the url's
			ExecutorService es = Executors.newFixedThreadPool(threadCount);
			for (int i = 0; i < threadCount; i++) {
				es.submit(new SearchThread(searchWord, urls, results));
			}
			
			// Terminate the url population thread
			fillES.shutdown();
			
			// Wait until the indexer thread is complete
			while (!fillES.isTerminated()) {
				Thread.sleep(2000);
			}
			
			// Terminate the search threads
			es.shutdown();
			
			// Wait until the search threads are complete
			while (!es.isTerminated()) {
				Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			log.error("Exception executing SearchService: " + e.getMessage(), e);
		}
		return getSearchResults(results);
	}

	private List<SearchResultModel> getSearchResults(SearchResultArrayBlockingQueue<SearchResultModel> s) {
		List<SearchResultModel> l = new ArrayList<SearchResultModel>();
		if (s != null) {
			while (!s.isEmpty()) {
				l.add(s.poll());
			}
		}
		return l;
	}
	
}

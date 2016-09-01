package com.leeward.crawler.web.threads;

import java.util.List;

import org.slf4j.Logger;

import com.leeward.crawler.web.models.URLArrayBlockingQueue;

public abstract class ProducerThread implements Runnable {

	protected URLArrayBlockingQueue<String> queue = null;
	protected int count = 0;
	
	protected void process(Logger log, List<String> urls)  {
		try {
			for (String url : urls) {
				queue.offer(url);
				count++;
			}

		} catch (Exception e) {
			log.error("Exception running the ProducerThread: " + e.getMessage(), e);
		}
	}
}

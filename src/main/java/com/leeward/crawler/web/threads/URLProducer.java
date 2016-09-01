package com.leeward.crawler.web.threads;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeward.crawler.web.models.URLArrayBlockingQueue;

public class URLProducer extends ProducerThread {

	Logger log = LoggerFactory.getLogger(URLProducer.class);
	
	
	private URLProducer() {
		this.count = 0;
	}

	public URLProducer(URLArrayBlockingQueue<String> q) {
		this();
		this.queue = q;
	}
	
	@Override
	public void run() {
		log.info("Executing URLProducer thread.");
		try {
			process(log, new ArrayList<String>());
		} catch (Exception e) {
			log.error("Exception running URLProducer thread: " + e.getMessage(), e);
		}
	}
	
	public int getCount() {
		return count;
	}

}

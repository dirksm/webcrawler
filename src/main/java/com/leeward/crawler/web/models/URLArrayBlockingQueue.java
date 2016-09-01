package com.leeward.crawler.web.models;

import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is a unique ArrayBlockingQueue ensuring that no duplicate URL's are added to the queue.
 * @author Michael R Dirks
 *
 */
public class URLArrayBlockingQueue<E> extends ArrayBlockingQueue<String> {

	private static final long serialVersionUID = 6601585653297588806L;
	TreeSet<String> urls;

	
	public URLArrayBlockingQueue(int capacity) {
		super(capacity);
		urls = new TreeSet<String>();
	}
	
	@Override
	public void put(String e) throws InterruptedException {
		if (urls.add(e)) {
			super.put(e);
		}
	}

}

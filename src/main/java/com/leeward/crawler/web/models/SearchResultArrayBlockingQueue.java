package com.leeward.crawler.web.models;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SearchResultArrayBlockingQueue<E> extends ArrayBlockingQueue<SearchResultModel> {
	
	private static final long serialVersionUID = 5107806471341815557L;
	TreeSet<SearchResultModel> results;

	public SearchResultArrayBlockingQueue(int capacity) {
		super(capacity);
		results = new TreeSet<SearchResultModel>();
	}
	
	public void addAll(List<SearchResultModel> e) {
		for (SearchResultModel s : e) {
			this.offer(s);
		}
	}
	
	@Override
	public void put(SearchResultModel e) throws InterruptedException {
		if (results.add(e)) {
			super.put(e);
		}
	}
	
	@Override
	public boolean offer(SearchResultModel e) {
		boolean o = false;
		if (results.add(e)) {
			o = super.offer(e);
		}
		return o;
	}
	
	public int getSize() {
		return results.size();
	}

	@Override
	public boolean offer(SearchResultModel e, long timeout, TimeUnit unit) throws InterruptedException {
		boolean o = false;
		if (results.add(e)) {
			o = super.offer(e, timeout, unit);
		}
		return o;
	}

}

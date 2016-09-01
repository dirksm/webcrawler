package com.leeward.crawler.web.models;
import java.io.Serializable;


public class SearchResultModel implements Serializable {

	private String title;
	private String url;
	private String text;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "SearchResultModel [title=" + title + ", url=" + url + ", text="
				+ text + "]";
	}
	
	
}

package com.leeward.crawler.web.models;
import java.io.Serializable;


public class SearchResultModel implements Serializable, Comparable<SearchResultModel> {

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResultModel other = (SearchResultModel) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public int compareTo(SearchResultModel srm) {
		return srm.getUrl().compareTo(this.getUrl());
	}
	
}

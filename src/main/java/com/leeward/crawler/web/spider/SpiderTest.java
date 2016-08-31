package com.leeward.crawler.web.spider;

public class SpiderTest {

	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.search("http://www.leewardassociates.com", "web data");
	}
}

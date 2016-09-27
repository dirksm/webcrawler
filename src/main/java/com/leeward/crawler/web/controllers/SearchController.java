package com.leeward.crawler.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leeward.crawler.web.models.SearchResultModel;
import com.leeward.crawler.web.services.SitePagesService;

@RestController
@RequestMapping(path="/search")
public class SearchController {

	@Autowired
	SitePagesService sitePagesService;
	
	@RequestMapping(value="/{url}/{q}", method=RequestMethod.GET)
	public List<SearchResultModel> search(@PathVariable String url, @PathVariable String q) {
		System.out.println("url: " + url);
		System.out.println("q: " + q);
		return sitePagesService.getSitePagesList(url, q);
	}
}

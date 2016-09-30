package com.leeward.crawler.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.leeward.crawler.web.models.JsonStatusModel;

@Controller
@RequestMapping("/ip")
public class IPController {
	
	@RequestMapping(value="/location/{ip:.+}", method=RequestMethod.GET)
	public String getLocation(ModelMap map, @PathVariable String ip) {
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:8080/geoip/search/"+ip;
		JsonStatusModel json = template.getForObject(url, JsonStatusModel.class);
		System.out.println(json);
		map.addAttribute("status", json.getStatus());
		map.addAttribute("geoip", json.getResult());
		map.addAttribute("ip", ip);
		return "ip";
	}
}

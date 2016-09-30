package com.leeward.crawler.web.services;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeward.crawler.web.dao.SitePagesDAO;
import com.leeward.crawler.web.models.SearchResultModel;
import com.leeward.crawler.web.models.SitePagesModel;

 
 
@Service("sitepagesService")
public class SitePagesService {
 
    private static Logger log = LoggerFactory.getLogger(SitePagesService.class);

	 
    @Autowired
    private SitePagesDAO sitepagesDao;
	 

    @Transactional(propagation=Propagation.REQUIRED)
    public int addSitepages(SitePagesModel bean) {
        return sitepagesDao.addSitepages(bean);
    }


    public List<SitePagesModel> getSitepagesList() {
        return sitepagesDao.getSitepagesList();
    }

    public List<SearchResultModel> getSitePagesList(String siteName, String searchName) {
    	List<SitePagesModel> list = sitepagesDao.getSitePagesList(siteName, searchName);
    	List<SearchResultModel> results = new ArrayList<SearchResultModel>();
    	for (SitePagesModel spm : list) {
			results.add(new SearchResultModel(spm.getTitle(), spm.getPageUrl(), spm.getText().substring(Math.max(spm.getText().indexOf(searchName)-150, 0), Math.min(spm.getText().indexOf(searchName)+150, spm.getText().length()))));
		}
        return results;
    }
	 
    public SitePagesModel getSitepages(Integer id) {
        return sitepagesDao.getSitepages(id);
    }

	 
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateSitepages(SitePagesModel bean) {
        return sitepagesDao.updateSitepages(bean);
    }


    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteSitepages(Integer id) {
        return sitepagesDao.deleteSitepages(id);
    }

	 
}

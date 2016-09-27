package com.leeward.crawler.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.leeward.crawler.web.constants.DBConstants;
import com.leeward.crawler.web.models.SitePagesModel;


@Repository("sitepagesDao")
public class SitePagesDAO implements DBConstants {
	
    private static Logger log = LoggerFactory.getLogger(SitePagesDAO.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;
	
    public JdbcTemplate getTemplate() {
        return this.jdbcTemplate;
    }
	
    public int addSitepages(SitePagesModel bean) {
        StringBuffer sInsertStmt = new StringBuffer(200);
        sInsertStmt.append( "INSERT INTO " + SITEPAGES + " (")
            .append(" site_url " )
            .append(", title " )
            .append(", text " )
            .append(", page_url " )
            .append(") VALUES ( ")
            .append(" ?")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(")");
        Object[] args = {
            bean.getSiteUrl(), 
            bean.getTitle(), 
            bean.getText(), 
            bean.getPageUrl()};
        int numRows = getTemplate().update(sInsertStmt.toString(), args);
        return getAutoIncrementKey();
    }


    public int updateSitepages(SitePagesModel bean) {
        StringBuffer sUpdateStmt = new StringBuffer(200);
        sUpdateStmt.append("UPDATE " + SITEPAGES)
        .append(" SET ")
        .append(" site_url = ? " )
        .append(", title = ? " )
        .append(", text = ? " )
        .append(", page_url = ? " ); 
        StringBuffer sWhereStmt = new StringBuffer(100);
        sWhereStmt.append(" WHERE id = ?");
        sUpdateStmt.append( sWhereStmt );
        Object[] args = {
            bean.getSiteUrl(), 
            bean.getTitle(), 
            bean.getText(), 
            bean.getPageUrl(), 
            bean.getId()};
        int numRows = getTemplate().update(sUpdateStmt.toString(), args);
        return numRows;
    }


    public SitePagesModel getSitepages(Integer id) {
    String sqlString = "select " +
        "id" +
        ", site_url" +
        ", title" +
        ", text" +
        ", page_url" +
        ", updated" +
        " from " + SITEPAGES + " where id = ?";
        Object[] args = {id};
        List<SitePagesModel> matches = getTemplate().query(sqlString, args, new RowMapper<SitePagesModel>() {
            public SitePagesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                SitePagesModel model = new SitePagesModel();
                    model.setId(rs.getInt("id"));
                    model.setSiteUrl(rs.getString("site_url"));
                    model.setTitle(rs.getString("title"));
                    model.setText(rs.getString("text"));
                    model.setPageUrl(rs.getString("page_url"));
                    model.setUpdated(rs.getTimestamp("updated")!=null?new java.util.Date(rs.getTimestamp("updated").getTime()):null);
                return model;
            }
        });
        return matches!=null&&matches.size()>0?matches.get(0):null;
    }


    public List<SitePagesModel> getSitepagesList() {
    String sqlString = "select " +
        "id" +
        ", site_url" +
        ", title" +
        ", text" +
        ", page_url" +
        ", updated" +
        " from " + SITEPAGES;
        return getTemplate().query(sqlString, new RowMapper<SitePagesModel>() {
            public SitePagesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                SitePagesModel model = new SitePagesModel();
                    model.setId(rs.getInt("id"));
                    model.setSiteUrl(rs.getString("site_url"));
                    model.setTitle(rs.getString("title"));
                    model.setText(rs.getString("text"));
                    model.setPageUrl(rs.getString("page_url"));
                    model.setUpdated(rs.getTimestamp("updated")!=null?new java.util.Date(rs.getTimestamp("updated").getTime()):null);
                return model;
            }
        });
    }


    public List<SitePagesModel> getSitePagesList(String siteName, String searchName) {
    String sqlString = "select " +
        "id" +
        ", site_url" +
        ", title" +
        ", text" +
        ", page_url" +
        ", updated" +
        " from " + SITEPAGES + " where site_url = ? and LOWER(text) like ?";
    	Object[] args = {siteName, '%'+StringUtils.lowerCase(searchName)+'%'};
        return getTemplate().query(sqlString, args, new RowMapper<SitePagesModel>() {
            public SitePagesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                SitePagesModel model = new SitePagesModel();
                    model.setId(rs.getInt("id"));
                    model.setSiteUrl(rs.getString("site_url"));
                    model.setTitle(rs.getString("title"));
                    model.setText(rs.getString("text"));
                    model.setPageUrl(rs.getString("page_url"));
                    model.setUpdated(rs.getTimestamp("updated")!=null?new java.util.Date(rs.getTimestamp("updated").getTime()):null);
                return model;
            }
        });
    }

    public int deleteSitepages(Integer id) {
        StringBuffer sDeleteStmt = new StringBuffer(200);
        sDeleteStmt.append("DELETE FROM " + SITEPAGES);
        StringBuffer sWhereStmt = new StringBuffer(100);
        sWhereStmt.append(" WHERE id = ?");
        sDeleteStmt.append(sWhereStmt);
        Object[] args = {id};
        int numRows = getTemplate().update(sDeleteStmt.toString(), args);
        return numRows;
    }


    public int getAutoIncrementKey() {
        String sqlString = "select last_insert_id()";
        return getTemplate().queryForObject(sqlString, Integer.class);
    }


}

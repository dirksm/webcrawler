package com.leeward.crawler.web.models;

import java.io.Serializable;

public class GeoLocationModel implements Serializable {

	private String countryIsoCode;
	private String countryName;
	private Integer countryGeoNameId;

	private String cityName;
	private String zipCode;
	private Integer cityGeoNameId;

	private String state;
	private String stateAbbrev;

	private Double lat;
	private Double lon;
	private Integer accuracyRadius;
	private Integer averageIncome;
	private Integer metroCode;
	private Integer populationDensity;
	private String timeZone;

	public String getCountryIsoCode() {
		return countryIsoCode;
	}
	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCountryGeoNameId() {
		return countryGeoNameId;
	}
	public void setCountryGeoNameId(Integer countryGeoNameId) {
		this.countryGeoNameId = countryGeoNameId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Integer getCityGeoNameId() {
		return cityGeoNameId;
	}
	public void setCityGeoNameId(Integer cityGeoNameId) {
		this.cityGeoNameId = cityGeoNameId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Integer getAccuracyRadius() {
		return accuracyRadius;
	}
	public void setAccuracyRadius(Integer accuracyRadius) {
		this.accuracyRadius = accuracyRadius;
	}
	public Integer getAverageIncome() {
		return averageIncome;
	}
	public void setAverageIncome(Integer averageIncome) {
		this.averageIncome = averageIncome;
	}
	public Integer getMetroCode() {
		return metroCode;
	}
	public void setMetroCode(Integer metroCode) {
		this.metroCode = metroCode;
	}
	public Integer getPopulationDensity() {
		return populationDensity;
	}
	public void setPopulationDensity(Integer populationDensity) {
		this.populationDensity = populationDensity;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	@Override
	public String toString() {
		return "GeoLocationModel [countryIsoCode=" + countryIsoCode
				+ ", countryName=" + countryName + ", countryGeoNameId="
				+ countryGeoNameId + ", cityName=" + cityName + ", zipCode="
				+ zipCode + ", cityGeoNameId=" + cityGeoNameId + ", state="
				+ state + ", stateAbbrev=" + stateAbbrev + ", lat=" + lat
				+ ", lon=" + lon + ", accuracyRadius=" + accuracyRadius
				+ ", averageIncome=" + averageIncome + ", metroCode="
				+ metroCode + ", populationDensity=" + populationDensity
				+ ", timeZone=" + timeZone + "]";
	}

	

}

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Results for IP [${ip}]</h1>

<h2>Status: ${status}</h2>

<c:choose>
	<c:when test="${status == 'SUCCESS'}">
<h3>Location: ${geoip.cityName} ${geoip.stateAbbrev}  ${geoip.zipCode}</h3>
<h4><a href="https://www.google.com/maps/@${geoip.lat},${geoip.lon},15z" target="_blank">Map this location</a></h4>
	</c:when>
	<c:otherwise>
<h3>Location: ${geoip}</h3>
	</c:otherwise>
</c:choose>

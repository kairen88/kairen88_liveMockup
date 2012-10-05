<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> 
<html>
	<head>
		<title><%= params.getPageTitle() %></title>
		<meta http-equiv="Content-Script-Type" content="text/javascript"> 
		<base href="<%= params.getBase() %>"/>
		<script type="text/javascript" src="plugin/media?query=get-resource&name=zoom.js"></script>
	</head>
	
	<body onload="loadZoom(); fetchImages('<%= params.getPreviousMediaUrl() %>', '<%= params.getNextMediaUrl() %>')">
	
		<table >
			<tr>
				<% if (params.hasPrevious() || params.hasNext()) { %>
					<td>
						<% if (params.hasPrevious()) {%>
							<a href="<%= params.getPreviousPageUrl() %>">
								<img name="prevImage" height="32"/>
								<img src="plugin/media?query=get-resource&name=actions/previous.png"/>
							</a>
						<% } %>
						
						<% if (params.getHomeUrl() != null) {%>
							<a href="<%= params.getHomeUrl() %>">
								<img src="plugin/media?query=get-resource&name=actions/gohome.png"/>
							</a>
						<% } %>
						
						<% if (params.hasNext()) {%>
							<a href="<%= params.getNextPageUrl() %>">
								<img src="plugin/media?query=get-resource&name=actions/next.png"/>
								<img name="nextImage" height="32"/>
							</a>
						<% } %>
					</td>
				<% } %>
				
				<td width="10"/>
				<td bgcolor="black" width="2"/>
				<td width="10"/>
				
				<td>
					<img name="zoomInIcon" onclick="zoomIn()" src="plugin/media?query=get-resource&name=actions/viewmag%2B.png"/>
					<img name="zoomOutcon" onclick="zoomOut()" src="plugin/media?query=get-resource&name=actions/viewmag-.png"/>
					<img name="fitIcon" onclick="fit()" src="plugin/media?query=get-resource&name=actions/viewmagfit.png"/>
					<img name="fullSizeIcon" onclick="fullSize()" src="plugin/media?query=get-resource&name=actions/viewmag1.png"/>
				</td>
				
				<td>
					<% if (params.isHighQuality()) { %>
						<a href="<%= params.getCurrentPageUrl("lo") %>">low quality</a>
					<% } else { %>
						low quality
					<% } %>
					
					-
					
					<% if (! params.isHighQuality()) { %>
						<a href="<%= params.getCurrentPageUrl("hi") %>">high quality</a>
					<% } else { %>
						high quality
					<% } %>
				</td>
			</tr>
		</table>
		
		<img name="mainImg" style="visibility: hidden" src="<%= params.getCurrentMediaUrl() %>"/>
	</body>
</html>


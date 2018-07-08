<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@page session="false" %>
<html>
    <head>
        <title>Spittr</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/resources/style.css" />" >
    </head>
    <body>
        <div id="header">
            <!-- wstawia nag?owek. Atrybut name powinien odnosi? si? do odpowiedniego 
            atrybutu name z pliku tiles.xml ze znacznika put-attribute --> 
            <t:insertAttribute name="header" />
        </div>
        <div id="content">
            <t:insertAttribute name="body" />
        </div>
        <div id="footer">
            <t:insertAttribute name="footer" />
        </div>
    </body>
</html>
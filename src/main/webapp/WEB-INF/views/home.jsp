<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- importuje biblioteke ogolnych znacznikow springa --> 
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@page session="false" %>
<html>
    <head>
        <title>Spittr</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
    </head>
    <body>
        <!-- pobiera komunikat z podanym kodem i generuje go, przydatne w 
        internacjonalizacji, gdy chce sie wyswietlic tekst w zaleznosci od jezyka.
        Atrybut code to klucz tekstu z pliku wlasciwosci --> 
        <h1><s:message code="spittr.welcome" /></h1>
        <a href="<c:url value="/spittles" />"><s:message code="spittr.spittle" /></a> |
        <!-- Znacznik s:url tworzy adres URL podobnie jak c:url, ma jednak kilka dodatkowych 
        mozliwosci --> 
        <!-- Za pomoca s:url mozna np. adres URL przypisac do zmiennej i skorzystac z niej w innym miejscu
        - to rowniez umozliwia c:url -->
        <s:url value="/spitter/register" var="registerUrl" />
        <a href="${registerUrl}"><s:message code="spittr.registration" /></a> 
        <!-- Znacznik s:url umozliwia takze m.in. ustawienie zasiegu np. aplikacji, sesji, 
        dodanie parametrow do adresu URL za pomoca znacznika s:param - to rowniez umozliwia 
        c:url. Roznica to np. mozliwosc eskejpowania adresu URL czyli zapisana go jako 
        kod html a nie jako link albo uzycie adresu URL w kodzie JavaScript za pomoca 
        atrybutu javaScriptEnable ustawionego na true --> 
    </body>
</html>

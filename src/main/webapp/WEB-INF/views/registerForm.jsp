<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="false" contentType="text/html; charset=UTF-8"%>
<!-- biblioteka ze znacznikami formularzy JSP -->
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
    <head>
        <title>Spittr</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
    </head>
    <body>
        <h1>Rejestracja</h1>
        <!-- znacznik form nie zawiera parametru action, więc formularz po zatwierdzeniu 
        zostanie wysłany na ten sam adres URL, pod którym znajduje się formularz, 
        czyli /spitters/register -->
        <!-- <form method="POST">
            Imię: <input type="text" name="firstName" /><br/>
            Nazwisko: <input type="text" name="lastName" /><br/>
            Nazwa użytkownika <input type="text" name="username" /><br/>
            Hasło: <input type="password" name="password" /><br/>
            <input type="submit" value="Zarejestruj" />
        </form> -->
        <!-- Generuje znacznik html <form> i wystawia ścieżki wiązania, aby umożliwić 
        wiązanie danych z wewnętrznymi znacznikami. Dzięki niemu model przekazany 
        przez commandName jest wiązany z wartościami właściwości znajdujących się w 
        znacznikach wewnętrznych. W przeciwieńśtwie do powyższego, html-owego sposobu 
        zapisania formularzy, ten sposób umożliwia np. zapisane danych do modelu, przez co 
        po pojawieniu się błędu i powrocie na tą stronę, pola będą uzupełnione -->
        <sf:form method="POST" commandName="spitter" enctype="multipart/form-data">
            <!-- Jeśli path ma wartość * wtedy generuje komunikaty o błędach dla wszystkich właściwości. -->
            <sf:errors path="*" element="div" cssClass="errors"/>
            <!-- Znacznik sf:label tworzy etykietę, w tym przypadku dla pola firstName. 
            Atrybut cssErrorClass ustawia klasę css w przypadku wystąpienia błędu dla danego pola --> 
            <sf:label path="firstName" cssErrorClass="error">Imię</sf:label>
            <!-- generuje znacznik html <input> z typem ustawionym na text. Atrybut 
            path określa z jakiej właściwości modelu ma zostać pobrana wartość i ustawiona 
            jako atrybut value dla pola --> 
            <sf:input path="firstName" cssErrorClass="error"/><br/>
            <!-- znacznik sf:errors umożliwia wyświetlenie błędów jeśli występują 
            dla podanego w path pola. Będą one wyśietlone w znaczniku span. Klasę 
            styli można ustawić za pomocą atrybutu cssClass --> 
            <!-- <sf:errors path="firstName" cssClass="error"/> <br/> -->
            <sf:label path="lastName" cssErrorClass="error">Nazwisko</sf:label>
            <sf:input path="lastName" cssErrorClass="error"/><br/>
            <!-- <sf:errors path="lastName" cssClass="error"/><br/> -->
            <!-- Od Springa 3.1 ten znacznik umożliwia określenie wartości atrybutu 
            type ustawiając typy tekstowe specyficzne dla HTML 5 np. data, range czy email. 
            Pozwala to na dodatkową walidację --> 
            Adres e-mail: <sf:input path="email" type="email"/><br/>
            <sf:label path="username" cssErrorClass="error">Nazwa użytkownika</sf:label>
            <sf:input path="username" cssErrorClass="error"/><br/>
            <!-- <sf:errors path="username" cssClass="error"/><br/> -->
            <!-- generuje znacznik html <input> z typem ustawionym na password --> 
            <sf:label path="password" cssErrorClass="error">Hasło</sf:label>
            <sf:password path="password" cssErrorClass="error"/><br/>
            <!-- <sf:errors path="password" cssClass="error"/><br/> -->
            <sf:label path="profilePicture" cssErrorClass="error">Profile Picture</sf:label>
            <sf:input type="file" path="profilePicture" accept="image/jpeg,image/png,image/gif" /><br/>
            <input type="submit" value="Zarejestruj" />
        </sf:form>
    </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Shmel
  Date: 28.02.2020
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContactByType(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <dl>
            <dt><%=SectionType.PERSONAL.getTitle()%>
            </dt>
            <dd><textarea name="<%=SectionType.PERSONAL%>">${resume.getSectionByType(SectionType.PERSONAL)}</textarea>
            </dd>
        </dl>
        <dl>
            <dt><%=SectionType.OBJECTIVE.getTitle()%>
            </dt>
            <dd><textarea name="<%=SectionType.OBJECTIVE%>">${resume.getSectionByType(SectionType.OBJECTIVE)}</textarea>
            </dd>
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
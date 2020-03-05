<%--
  Created by IntelliJ IDEA.
  User: Shmel
  Date: 28.02.2020
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextListSection" %>
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
        <c:forEach var="typeSection" items="<%=SectionType.values()%>">
            <jsp:useBean id="typeSection"
                         type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
                <c:when test="<%=typeSection.equals(SectionType.PERSONAL) || typeSection.equals(SectionType.OBJECTIVE)%>">
                    <dl>
                        <dt>${typeSection.title}
                        </dt>
                        <dd><textarea
                                name="${typeSection.name()}">${resume.getSectionByType(typeSection)}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="<%=typeSection.equals(SectionType.ACHIEVEMENT) || typeSection.equals(SectionType.QUALIFICATIONS)%>">
                    <dl>
                        <dt>${typeSection.title}
                        </dt>
                        <br>
                        <c:if test="${resume.getSectionByType(typeSection)!=null}">
                            <c:forEach var="text"
                                       items="<%=
                       ((TextListSection)resume.getSectionByType(typeSection)).getTextList()%>">
                                <dd class="sec">
                                    <textarea name="${typeSection.name()}">${text}</textarea>
                                </dd>
                                <br>
                            </c:forEach>
                        </c:if>
                        <c:if test="<%=typeSection.equals(SectionType.ACHIEVEMENT)%>">
                            <div id="ach">
                            </div>
                            <br>
                            <button type="button" onclick="addAchievement()">Добавить</button>
                        </c:if>
                        <c:if test="<%=typeSection.equals(SectionType.QUALIFICATIONS)%>">
                            <div id="qua">
                            </div>
                            <br>
                            <button type="button" onclick="addQualification()">Добавить</button>
                        </c:if>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script>
    function addAchievement() {
        document.getElementById("ach").insertAdjacentHTML('beforeend',
            '<dd class="sec"><textarea name="<%=SectionType.ACHIEVEMENT%>"></textarea></dd><br>');
    }

    function addQualification() {
        document.getElementById("qua").insertAdjacentHTML('beforeend',
            '<dd class="sec"><textarea name="<%=SectionType.QUALIFICATIONS%>"></textarea></dd><br>');
    }
</script>
</body>
</html>
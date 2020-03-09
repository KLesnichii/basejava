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
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="java.util.UUID" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
                        <dd class="sec">
                            <textarea
                                    name="${typeSection.name()}"><%=HtmlUtil.textListSectionToList(resume.getSectionByType(typeSection))%></textarea>
                        </dd>
                        <br>
                    </dl>
                </c:when>
                <c:when test="<%=typeSection.equals(SectionType.EDUCATION) || typeSection.equals(SectionType.EXPERIENCE)%>">
                    <dl>
                        <dt>${typeSection.title}
                        </dt>
                        <br><br>
                        <c:if test="${resume.getSectionByType(typeSection)!=null}">
                            <c:forEach var="organization"
                                       items="<%=
                       ((OrganizationSection)resume.getSectionByType(typeSection)).getOrganizationList()%>">
                                <dt class="left">Название:</dt>
                                <dd>
                                    <input type="text" name="${typeSection.name()}" size=30
                                           value="${organization.header}">
                                </dd>
                                <br>
                                <dt class="left">Ссылка:</dt>
                                <dd>
                                    <input type="text" name="link" size=30 value="${organization.link}">
                                </dd>
                                <br>
                                <c:forEach var="eventPeriod"
                                           items="${organization.eventPeriodList}">
                                    <input type="hidden" name="${typeSection.name()}" value="#existEventPeriod#">
                                    <div class="event">
                                        <dt class="left">Деятельность:</dt>
                                        <dd>
                                            <input type="text" name="title" size=30 value="${eventPeriod.title}">
                                        </dd>
                                        <br>
                                        <c:if test="<%=typeSection.equals(SectionType.EXPERIENCE)%>">
                                            <dt class="left">Описание:</dt>
                                            <dd>
                                                <textarea name="text">${eventPeriod.text}</textarea>
                                            </dd>
                                            <br>
                                        </c:if>
                                        <dt class="left">Дата начала:</dt>
                                        <dd>
                                            <input name="startDate" type="date" value="${eventPeriod.startDate}">
                                        </dd>
                                        <br>
                                        <dt class="left">Дата окончания:</dt>
                                        <dd>
                                            <input name="endDate" type="date" value="${eventPeriod.endDate}">
                                        </dd>
                                    </div>
                                </c:forEach>
                                <c:set var="id" value="<%=UUID.randomUUID()%>"/>
                                <button id="${id}" type="button"
                                        onclick=addEventPeriod('${typeSection.name()}','${id}')>
                                    Добавить описание
                                </button>
                                <hr>
                            </c:forEach>
                        </c:if>
                        <button id="${typeSection.name()}" type="button"
                                onclick="addOrganization('${typeSection.name()}')">Добавить
                        </button>
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
<script type="text/javascript" charset="UTF-8">
    function addOrganization(type) {
        let element = document.createElement('div');
        element.innerHTML = '<dt class="left">Название:</dt><dd><input type="text" name="' + type + '" size=30></dd><br>';
        element.insertAdjacentHTML('beforeend', '<dt class="left">Ссылка:</dt><dd><input type="text" name="link" size=30 ></dd><br>');
        const id = Math.random().toString(26).slice(2);
        element.insertAdjacentHTML('beforeend', '<button id=' + id + ' type="button" onclick=addEventPeriod(' + "'" + type + "'" + ',"' + id + '")>Добавить описание</button>');
        document.getElementById(type).before(element)
    }

    function addEventPeriod(type, id) {
        let element = document.createElement('div');
        element.setAttribute("class", "event")
        element.innerHTML = '<input type="hidden" name="' + type + '" value="#existEventPeriod#">' +
            '<dt class="left">Деятельность:</dt>' +
            '<dd> <input type="text" name="title" size=30></dd><br>';
        if ('EXPERIENCE' == type) {
            element.insertAdjacentHTML('beforeend', ' <dt class="left">Описание:</dt>' +
                '<dd> <textarea name="text"></textarea> </dd> <br>');
        }
        element.insertAdjacentHTML('beforeend',
            '<dt class="left">Дата начала:</dt><dd><input name="startDate" type="date"></dd>' +
            '<br><dt class="left">Дата окончания:</dt><dd> <input name="endDate" type="date"></dd>');
        document.getElementById(id).before(element)
    }
</script>
</body>
</html>
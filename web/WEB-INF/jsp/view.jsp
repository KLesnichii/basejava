<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextFieldSection" %><%--
  Created by IntelliJ IDEA.
  User: Shmel
  Date: 28.02.2020
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <article>
            <h2>${sectionEntry.key.title}</h2>
            <c:choose>
                <c:when test="<%=sectionEntry.getKey().equals(SectionType.PERSONAL) || sectionEntry.getKey().equals(SectionType.OBJECTIVE)%>">
                    <p><%=((TextFieldSection) sectionEntry.getValue()).getText()%>
                    </p>
                </c:when>
                <c:when test="<%=sectionEntry.getKey().equals(SectionType.ACHIEVEMENT) || sectionEntry.getKey().equals(SectionType.QUALIFICATIONS)%>">
                    <ul>
                        <c:forEach var="textListSection"
                                   items="<%=((TextListSection)sectionEntry.getValue()).getTextList()%>">
                            <jsp:useBean id="textListSection"
                                         type="java.lang.String"/>
                            <li>
                                    ${textListSection}
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="<%=sectionEntry.getKey().equals(SectionType.EXPERIENCE) || sectionEntry.getKey().equals(SectionType.EDUCATION)%>">
                    <c:forEach var="organization"
                               items="<%=((OrganizationSection)sectionEntry.getValue()).getOrganizationList()%>">
                        <jsp:useBean id="organization"
                                     type="ru.javawebinar.basejava.model.Organization"/>
                        <h3><a href="${organization.link}">${organization.header}</a></h3>
                        <c:forEach var="eventPeriod"
                                   items="${organization.eventPeriodList}">
                            <jsp:useBean id="eventPeriod"
                                         type="ru.javawebinar.basejava.model.EventPeriod"/>
                            <table>
                                <tr>
                                    <td>${eventPeriod.startDateFormat} - ${eventPeriod.endDateFormat}</td>
                                    <td>
                                        <b>${eventPeriod.title}</b>
                                        <br>
                                            ${eventPeriod.text}
                                    </td>
                                </tr>
                            </table>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    No Exist
                </c:otherwise>
            </c:choose>
        </article>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
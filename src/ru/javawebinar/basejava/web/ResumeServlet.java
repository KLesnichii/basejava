package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private final static Storage storage = Config.getInstance().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid.equals("")) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        int countEventPeriod = 0;
        int countLink = 0;
        for (SectionType typeSection : SectionType.values()) {
            String value = request.getParameter(typeSection.name());
            if (value != null) {
                List<String> parameterValues = Arrays.stream(request.getParameterValues(typeSection.name()))
                        .filter((s) -> s.trim().length() != 0 && !s.equals("#existEventPeriod#"))
                        .collect(Collectors.toList());
                if (parameterValues.isEmpty()) {
                    r.getSections().remove(typeSection);
                } else
                    switch (typeSection) {
                        case PERSONAL:
                        case OBJECTIVE:
                            r.addSection(typeSection, new TextFieldSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            List<String> textLists = Arrays.asList(value.trim().split("\\n"));
                            textLists = textLists.stream().filter((s) -> s.trim().length() != 0).collect(Collectors.toList());
                            r.addSection(typeSection, new TextListSection(textLists));
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            String[] organizationSting = parameterMap.get(typeSection.name());
                            List<Organization> organizationList = new ArrayList<>();
                            Organization organization = null;
                            List<EventPeriod> eventPeriodList = new ArrayList<>();
                            String lastHeader = "";
                            for (String header : organizationSting) {
                                switch (header) {
                                    case "#existEventPeriod#":
                                        String title = parameterMap.get("title")[countEventPeriod];
                                        if (title.equals("") || lastHeader.equals("")) {
                                            countEventPeriod++;
                                            continue;
                                        }
                                        String start = parameterMap.get("startDate")[countEventPeriod];
                                        LocalDate startDate = LocalDate.parse(start.equals("") ? "0001-01-01" : start);
                                        String end = parameterMap.get("endDate")[countEventPeriod];
                                        LocalDate endDate = LocalDate.parse(end.equals("") ? "0001-01-01" : end);

                                        if (typeSection.equals(SectionType.EXPERIENCE)) {
                                            String text = parameterMap.get("text")[countEventPeriod];
                                            eventPeriodList.add(new EventPeriod(title, startDate, endDate, text));
                                        } else {
                                            eventPeriodList.add(new EventPeriod(title, startDate, endDate));
                                        }
                                        countEventPeriod++;
                                        break;
                                    case "":
                                        lastHeader = "";
                                        countLink++;
                                        break;
                                    default:
                                        if (!eventPeriodList.isEmpty()) {
                                            organization.getEventPeriodList().addAll(new ArrayList<>(eventPeriodList));
                                            eventPeriodList.clear();
                                        }
                                        if (organization != null) {
                                            organizationList.add(organization);
                                        }
                                        lastHeader = header;
                                        organization = new Organization(header, parameterMap.get("link")[countLink], new ArrayList<>(eventPeriodList));
                                        countLink++;
                                        break;
                                }
                            }
                            if (!eventPeriodList.isEmpty()) {
                                organization.getEventPeriodList().addAll(new ArrayList<>(eventPeriodList));
                                eventPeriodList.clear();
                            }
                            organizationList.add(organization);
                            r.addSection(typeSection, new OrganizationSection(organizationList));
                            break;
                    }
            } else {
                r.getSections().remove(typeSection);
            }
        }
        if (uuid.equals("")) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                request.setAttribute("resume", storage.get(uuid));
                request.getRequestDispatcher(
                        ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                ).forward(request, response);
                return;
            case "add":
                request.setAttribute("resume", new Resume());
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }
}
package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

        for (SectionType typeSection : SectionType.values()) {
            String value = request.getParameter(typeSection.name());
            if (value != null) {
                List<String> textList = Arrays.stream(request.getParameterValues(typeSection.name()))
                        .filter((s) -> !s.equals("") && s.trim().length() != 0)
                        .collect(Collectors.toList());
                if (textList.isEmpty()) {
                    r.getSections().remove(typeSection);
                } else
                    switch (typeSection) {
                        case PERSONAL:
                        case OBJECTIVE:
                            r.addSection(typeSection, new TextFieldSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(typeSection, new TextListSection(textList));
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
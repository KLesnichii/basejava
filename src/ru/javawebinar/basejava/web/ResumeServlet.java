package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Storage storage = Config.getInstance().getStorage();
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            String resumeHTML = "<html>\n" +
                    "<head>\n" +
                    "<title>Resume</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>" +
                    "<b>uuid: </b>" +
                    resume.getUuid() +
                    "</p>" +
                    "<p>" +
                    "<b>FullName: </b>" +
                    resume.getFullName() +
                    "</p>" +
                    "</body>\n" +
                    "</html>";
            response.getWriter().write(resumeHTML);
        } else {
            StringBuilder addResume = new StringBuilder();
            for (Resume r : storage.getAllSorted()) {
                addResume.append("<tr>\n<td>").append(r.getUuid()).append("</td>\n<td>").append(r.getFullName()).append("</td>\n</tr>\n");
            }
            String tableHTML = "<html>\n" +
                    "<head>\n" +
                    "<title>Resume</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table border=\"1\">\n" +
                    "<tr>\n" +
                    "<td>uuid</td>\n" +
                    "<td>full_name</td>\n" +
                    "</tr>\n" +
                    addResume +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
            response.getWriter().write(tableHTML);
        }
    }
}
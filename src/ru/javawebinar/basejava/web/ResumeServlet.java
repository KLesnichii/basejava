package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
//        List<Resume> resumeList =  new SqlStorage(Config.getInstance().getDbUrl(),
//                Config.getInstance().getDbUser(),
//                Config.getInstance().getDbPassword()).getAllSorted();
        List<Resume> resumeList = new SqlStorage("jdbc:postgresql://localhost:5432/resumes",
                "postgres",
                "postgres").getAllSorted();
        StringBuilder addResume = new StringBuilder();
        for (Resume r : resumeList) {
            addResume.append("<tr>\n<td>").append(r.getUuid()).append("</td>\n<td>").append(r.getFullName()).append("</td>\n</tr>\n");
        }
        response.getWriter().write("<html>\n" +
                "<head>\n" +
                "<title>Resume</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">\n" +
                "<tr>\n" +
                "<td>uuid</td>\n" +
                "<td>full_name</td>\n" +
                "</tr>\n"+
                addResume +
                "</table>\n" +
                "</body>\n" +
                "</html>");
    }
}
package com.notesapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.notesapp.dao.NoteDAO;
import com.notesapp.model.Note;
import com.notesapp.model.User;

@WebServlet("/getNotes")
public class GetNotesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"error\": \"User not logged in\"}");
            return;
        }

        User user = (User) session.getAttribute("user");
        NoteDAO noteDao = new NoteDAO();
        List<Note> notes = noteDao.getNotesByUserId(user.getId());

        // Convert notes to JSON format
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[");
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            jsonOutput.append("{")
                    .append("\"id\":").append(note.getId()).append(",")
                    .append("\"title\":\"").append(note.getTitle()).append("\",")
                    .append("\"content\":\"").append(note.getContent()).append("\"")
                    .append("}");
            if (i < notes.size() - 1) {
                jsonOutput.append(",");
            }
        }
        jsonOutput.append("]");

        out.write(jsonOutput.toString());
    }
}

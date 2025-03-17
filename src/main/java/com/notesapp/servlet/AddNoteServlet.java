package com.notesapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.notesapp.dao.NoteDAO;
import com.notesapp.model.Note;
import com.notesapp.model.User;

@WebServlet("/addNote")
public class AddNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);

        NoteDAO noteDAO = new NoteDAO();
        noteDAO.saveNote(note);

        response.sendRedirect("dashboard.html");
    }
}

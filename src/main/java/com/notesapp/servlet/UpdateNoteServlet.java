package com.notesapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notesapp.dao.NoteDAO;
import com.notesapp.model.Note;

@WebServlet("/updateNote")
public class UpdateNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noteId = Integer.parseInt(request.getParameter("noteId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        NoteDAO noteDAO = new NoteDAO();
        Note note = noteDAO.getNoteById(noteId);

        if (note != null) {
            note.setTitle(title);
            note.setContent(content);
            noteDAO.updateNote(note);
        }

        response.sendRedirect("dashboard.html");
    }
}

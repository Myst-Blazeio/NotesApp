package com.notesapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notesapp.dao.NoteDAO;

@WebServlet("/deleteNote")
public class DeleteNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noteId = Integer.parseInt(request.getParameter("noteId"));

        NoteDAO noteDAO = new NoteDAO();
        noteDAO.deleteNote(noteId);

        response.sendRedirect("dashboard.html");
    }
}

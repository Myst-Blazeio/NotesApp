package com.notesapp.servlet;

import com.notesapp.model.Note;
import com.notesapp.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/UpdateNoteServlet")
public class UpdateNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noteId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
            response.sendRedirect("editNote.html?id=" + noteId + "&error=Please fill in all fields");
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Note note = session.get(Note.class, noteId);
            if (note != null) {
                note.setTitle(title);
                note.setContent(content);
                note.setUpdatedAt(LocalDateTime.now());
                session.update(note);
            }

            transaction.commit();
            response.sendRedirect("notes.html?success=Note updated successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            response.sendRedirect("editNote.html?id=" + noteId + "&error=Failed to update note");
        }
    }
}

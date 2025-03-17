package com.notesapp.servlet;

import com.notesapp.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebServlet("/DeleteNoteServlet")
public class DeleteNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noteId = Integer.parseInt(request.getParameter("id"));

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Object note = session.get(Class.forName("com.notesapp.model.Note"), noteId);
            if (note != null) {
                session.delete(note);
            }

            transaction.commit();
            response.sendRedirect("notes.html?success=Note deleted successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            response.sendRedirect("notes.html?error=Failed to delete note");
        }
    }
}

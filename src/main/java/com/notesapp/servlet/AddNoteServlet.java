package com.notesapp.servlet;

import com.notesapp.model.Note;
import com.notesapp.model.User;
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

@WebServlet("/AddNoteServlet")
public class AddNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Create new note
        Note note = new Note(title, content, user);
        note.setUpdatedAt(LocalDateTime.now()); // Ensure updatedAt is set

        // Save note using Hibernate
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(note);
            tx.commit();
            response.sendRedirect("dashboard.html");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

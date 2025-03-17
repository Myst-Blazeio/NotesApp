package com.notesapp.servlet;

import com.notesapp.util.HibernateUtil;
import com.notesapp.model.Note;
import com.notesapp.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/GetNotesServlet")
public class GetNotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Note> query = session.createQuery("FROM Note WHERE user = :user", Note.class);
            query.setParameter("user", user);
            List<Note> notes = query.getResultList();
            
            out.print("[");
            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                out.print("{ \"id\": " + note.getId() + ", \"title\": \"" + note.getTitle() + "\", \"content\": \"" + note.getContent() + "\" }");
                if (i < notes.size() - 1) out.print(",");
            }
            out.print("]");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{ \"error\": \"Failed to fetch notes.\" }");
        }
    }
}

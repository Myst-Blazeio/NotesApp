package com.notesapp.dao;

import com.notesapp.util.HibernateUtil;
import com.notesapp.model.Note;
import com.notesapp.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class NoteDAO {

    public static List<Note> getNotesByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Note> query = session.createQuery("FROM Note WHERE user = :user", Note.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveNote(Note note) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(note);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNote(Note note) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(note);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteNote(int noteId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Note note = session.get(Note.class, noteId);
            if (note != null) {
                session.delete(note);
                tx.commit();
                return true;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return false;
    }
}

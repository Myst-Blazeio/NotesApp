package com.notesapp.dao;

import com.notesapp.model.Note;
import com.notesapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class NoteDAO {

    // Save a new note
    public void saveNote(Note note) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(note);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get a note by ID
    public Note getNoteById(int noteId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Note.class, noteId);
        }
    }

    // Get all notes for a specific user
    public List<Note> getNotesByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Note WHERE user.id = :userId", Note.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    // Update an existing note
    public void updateNote(Note note) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(note);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a note by ID
    public void deleteNote(int noteId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Note note = session.get(Note.class, noteId);
            if (note != null) {
                session.delete(note);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

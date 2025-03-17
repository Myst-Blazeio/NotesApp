package com.test.hibernate;

import com.notesapp.util.HibernateUtil;
import com.notesapp.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main(String[] args) {
        System.out.println("🔥 Starting Hibernate Test...");

        // Open Hibernate session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // ✅ Step 1: Create a new User object
            User user = new User("Test User", "test@example.com", "password123");
            session.save(user); // Save user to database

            tx.commit();
            System.out.println("✅ User saved successfully!");

            // ✅ Step 2: Retrieve User from DB
            User retrievedUser = session.get(User.class, user.getId());
            if (retrievedUser != null) {
                System.out.println("🎯 Retrieved User: " + retrievedUser.getName() + " | " + retrievedUser.getEmail());
            } else {
                System.out.println("⚠️ No user found with ID: " + user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown Hibernate properly
            HibernateUtil.shutdown();
        }

        System.out.println("🚀 Hibernate Test Completed.");
    }
}

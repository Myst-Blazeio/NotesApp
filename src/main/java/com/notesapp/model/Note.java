package com.notesapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
	public Note(int id, String title, String content, Date createdAt, User user) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.user = user;
	}
	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

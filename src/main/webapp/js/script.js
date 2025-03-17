document.addEventListener("DOMContentLoaded", function() {
    fetchNotes();
    
    document.getElementById("noteForm")?.addEventListener("submit", function(e) {
        e.preventDefault();
        const title = document.getElementById("title").value;
        const content = document.getElementById("content").value;
        
        fetch("AddNoteServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `title=${title}&content=${content}`
        }).then(response => response.text()).then(data => {
            alert(data);
            fetchNotes();
        });
    });

    document.getElementById("editNoteForm")?.addEventListener("submit", function(e) {
        e.preventDefault();
        const id = document.getElementById("noteId").value;
        const title = document.getElementById("editTitle").value;
        const content = document.getElementById("editContent").value;
        
        fetch("UpdateNoteServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `id=${id}&title=${title}&content=${content}`
        }).then(response => response.text()).then(data => {
            alert(data);
            window.location.href = "dashboard.html";
        });
    });
});

function fetchNotes() {
    fetch("GetNotesServlet")
    .then(response => response.json())
    .then(notes => {
        const notesList = document.getElementById("notesList");
        notesList.innerHTML = "";
        notes.forEach(note => {
            notesList.innerHTML += `<div class="note">
                <h3>${note.title}</h3>
                <p>${note.content}</p>
                <button onclick="deleteNote(${note.id})">Delete</button>
                <button onclick="goToEditPage(${note.id}, '${note.title}', '${note.content}')">Edit</button>
            </div>`;
        });
    });
}

function deleteNote(id) {
    fetch(`DeleteNoteServlet?id=${id}`, { method: "POST" })
    .then(response => response.text())
    .then(data => {
        alert(data);
        fetchNotes();
    });
}

function goToEditPage(id, title, content) {
    localStorage.setItem("noteId", id);
    localStorage.setItem("noteTitle", title);
    localStorage.setItem("noteContent", content);
    window.location.href = "edit-note.html";
}

if (window.location.pathname.includes("edit-note.html")) {
    document.getElementById("noteId").value = localStorage.getItem("noteId");
    document.getElementById("editTitle").value = localStorage.getItem("noteTitle");
    document.getElementById("editContent").value = localStorage.getItem("noteContent");
}

function logout() {
    fetch("LogoutServlet", { method: "POST" })
    .then(() => window.location.href = "index.html");
}

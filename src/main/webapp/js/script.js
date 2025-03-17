document.addEventListener("DOMContentLoaded", function () {
    fetch("/getNotes")
        .then(response => response.json())
        .then(notes => {
            let container = document.getElementById("notes-container");
            notes.forEach(note => {
                let div = document.createElement("div");
                div.innerHTML = `
                    <h4>${note.title}</h4>
                    <p>${note.content}</p>
                    <form action="deleteNote" method="POST">
                        <input type="hidden" name="noteId" value="${note.id}">
                        <button type="submit">Delete</button>
                    </form>
                `;
                container.appendChild(div);
            });
        });
});

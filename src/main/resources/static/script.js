// שליחת טופס יצירת תלמיד
document.getElementById('studentForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const formData = new FormData(this);
    const student = Object.fromEntries(formData.entries());

    const response = await fetch('http://localhost:8080/api/student', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(student)
    });

    const text = await response.text();
    document.getElementById('result').innerText = text;
});

// שליחת עדכון נקודות
async function updatePoints(action) {
    const id = document.getElementById('studentId').value;
    if (!id) return alert("אנא הזן תעודת זהות");

    const res = await fetch(`http://localhost:8080/api/student/${id}/points?action=${action}`, {
        method: 'POST'
    });
    const text = await res.text();
    document.getElementById('result').innerText = text;
}


    function filterTable() {
    console.log("Fillter Table run")
    var selectedFacultyName = document.getElementById("facultyFilter").value;
    var tableRows = document.querySelectorAll("#dataTable tbody tr");

        if (selectedFacultyName === "selectAll") {
            showAllContributions();
            return; // Exit the function early
        }

    tableRows.forEach(function(row) {
    var facultyId = row.querySelector("td:nth-child(7)").textContent.trim(); // Assuming faculty name is the 7th column
    if (facultyId === selectedFacultyName || facultyId === "selectAll") {
    row.style.display = ""; // Show the row if it matches the selected faculty or no faculty is selected
}
    else {
    row.style.display = "none"; // Hide the row if it does not match the selected faculty
}
});
}
    function showAllContributions() {
        var tableRows = document.querySelectorAll("#datatablesSimple tbody tr");

        tableRows.forEach(function(row) {
            row.style.display = ""; // Show all rows
        });
    }
    function changecontentheader(){
    var header = document.getElementById("contentHeader");
    // Check if the header exists
    if (header) {
    // Change the text content of the header
    header.textContent = "New Content"; // Change "New Content" to whatever text you want
} else {
    console.log("Header element not found.");
}
}

    document.getElementById("changeContentHeader").addEventListener("click", changecontentheader);
    // document.getElementById("selectAllButton").addEventListener("click", selectAllContributions);
    // Event listener for the select list
    document.getElementById("facultyFilter").addEventListener("change", filterTable);
    document.getElementById("contentHeader")
    // Initially filter the table based on the selected faculty
    filterTable();



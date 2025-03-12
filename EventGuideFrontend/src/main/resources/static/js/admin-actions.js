document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("event-details-modal");
    const form = document.getElementById("event-edit-form");

    window.openDetailsModal = function (button) {
        const eventRow = button.closest("tr");

        // Set form values
        document.getElementById("edit-id").value = eventRow.getAttribute("data-id");
        document.getElementById("edit-local-id").value = eventRow.getAttribute("data-local-id"); // ✅ Local ID added
        document.getElementById("edit-name").value = eventRow.getAttribute("data-name");
        document.getElementById("edit-artist").value = eventRow.getAttribute("data-artist");
        document.getElementById("edit-description").value = eventRow.getAttribute("data-description");
        document.getElementById("edit-date").value = eventRow.getAttribute("data-date");
        document.getElementById("edit-time").value = eventRow.getAttribute("data-time");
        document.getElementById("edit-location").value = eventRow.getAttribute("data-location");

        // Set reference URL
        const refUrl = eventRow.getAttribute("data-reference-url");
        document.getElementById("edit-reference-url").value = refUrl;
        document.getElementById("edit-reference").href = refUrl;

        // ✅ Set form action dynamically
        const eventId = eventRow.getAttribute("data-id");
        form.action = `/admin/edit/${eventId}`;

        modal.style.display = "flex";
    };

    window.closeDetailsModal = function () {
        modal.style.display = "none";
    };
});

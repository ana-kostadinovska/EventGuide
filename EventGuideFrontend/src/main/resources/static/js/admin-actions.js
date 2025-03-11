document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("confirmation-modal");
    const modalText = document.getElementById("modal-text");
    const confirmBtn = document.getElementById("confirm-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    let currentForm = null;

    window.confirmAction = function (action) {
        let actionText = "";

        switch (action) {
            case "approve":
                actionText = "Are you sure you want to approve this event?";
                break;
            case "reject":
                actionText = "Are you sure you want to reject this event?";
                break;
            case "discard":
                actionText = "Are you sure you want to discard this event?";
                break;
        }

        modalText.textContent = actionText;
        modal.style.display = "flex";

        return false; // Prevent form submission until confirmed
    };

    confirmBtn.addEventListener("click", function () {
        if (currentForm) {
            currentForm.submit();
        }
        modal.style.display = "none";
    });

    cancelBtn.addEventListener("click", function () {
        modal.style.display = "none";
    });
});

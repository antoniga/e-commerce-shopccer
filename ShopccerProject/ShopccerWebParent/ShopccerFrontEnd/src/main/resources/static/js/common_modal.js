function showModalDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function showErrorModal(message) {
    showModalDialog("Error", message);
}

function showWarningModal(message) {
    showModalDialog("Atención", message);
}

function showModalDialogDeleteProducto(title, message) {
    $("#modalTitleDeleteProducto").text(title);
    $("#modalBodyDelteProducto").text(message);
    $("#modalDeleteProducto").modal();
}
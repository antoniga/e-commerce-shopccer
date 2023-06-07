var returnModal;
var modalTitle;
var fieldNote;
var orderId;
var divReason;
var divMessage;
var firstButton;
var secondButton;

$(document).ready(function() {
    returnModal = $("#returnOrderModal");
    modalTitle = $("#returnOrderModalTitle");
    fieldNote = $("#returnNote");
    divReason = $("#divReason");
    divMessage = $("#divMessage");
    firstButton = $("#firstButton");
    secondButton = $("#secondButton");

    handleReturnOrderLink();
});

function showReturnModalDialog(link) {
    divMessage.hide();
    divReason.show();
    firstButton.show();
    secondButton.text("Cancelar");
    fieldNote.val("");

    orderId = link.attr("orderId");
    modalTitle.text("Devolver pedido con id: " + orderId);
    returnModal.modal("show");
}

function showMessageModal(message) {
    divReason.hide();
    firstButton.hide();
    secondButton.text("Cerrar");
    divMessage.text(message);

    divMessage.show();
}

function handleReturnOrderLink() {
    $(".linkReturnOrder").on("click", function(e) {
        e.preventDefault();
        showReturnModalDialog($(this));
    });
}

function submitReturnOrderForm() {
    reason = $("input[name='returnReason']:checked").val();
    note = fieldNote.val();

    sendReturnOrderRequest(reason, note);

    return false;
}

function sendReturnOrderRequest(reason, note) {
    requestURL = contextPath + "pedidos/return";
    requestBody = {idPedido: orderId, razon: reason, notas: note};

    $.ajax({
        type: "POST",
        url: requestURL,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(requestBody),
        contentType: 'application/json'

    }).done(function(returnResponse) {
        console.log(returnResponse);
        showMessageModal("La petición de reembolso ha sido enviada.");
        updateStatusTextAndHideReturnButton(returnResponse.orderId);
    }).fail(function(err) {
        console.log(err);
        showMessageModal(err.responseText);
    });

}

function updateStatusTextAndHideReturnButton(orderId) {
    $(".textOrderStatus" + orderId).each(function(index) {
        $(this).text("PETICION_REEMBOLSO");
    })

    $(".linkReturn" + orderId).each(function(index) {
        $(this).hide();
    })
}
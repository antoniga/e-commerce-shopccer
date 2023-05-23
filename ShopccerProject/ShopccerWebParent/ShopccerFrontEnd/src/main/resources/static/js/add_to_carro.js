$(document).ready(function() {
    $("#buttonAdd2Carro").on("click", function(evt) {
        addToCart();
    });
});

function addToCart() {
    selectElement = document.getElementById('selectTalla');
    talla = selectElement.options[selectElement.selectedIndex].getAttribute('data-key');

    cantidad = $("#cantidad" + idProducto).val();
    url = contextPath + "carro/add/" + idProducto + "/" + talla+ "/" + cantidad;


    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(response) {
        showModalDialog("Carro compra", response);
    }).fail(function() {
        showErrorModal("Error mientras añadiamos productos a tu carro de la compra.");
    });
}
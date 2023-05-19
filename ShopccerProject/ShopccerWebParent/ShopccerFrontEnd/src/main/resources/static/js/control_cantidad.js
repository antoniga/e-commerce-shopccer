$(document).ready(function() {
    $(".linkMinus").on("click", function(event) {
        event.preventDefault();
        idProducto = $(this).attr("pid");
        cantidadInput = $("#cantidad" + idProducto);
        nuevaCantidad = parseInt(cantidadInput.val()) - 1;

        if (nuevaCantidad > 0) {
            cantidadInput.val(nuevaCantidad);
        } else {
            showWarningModal('La cantidad mínima del pedido es de 1 unidad.');
        }
    });

    $(".linkPlus").on("click", function(event) {
        event.preventDefault();
        idProducto = $(this).attr("pid");
        cantidadInput = $("#cantidad" + idProducto);
        nuevaCantidad = parseInt(cantidadInput.val()) + 1;

        if (nuevaCantidad <= 5) {
            cantidadInput.val(nuevaCantidad);
        } else {
            showWarningModal('La cantidad máxima del pedido es de 5 unidades.');
        }
    });
});
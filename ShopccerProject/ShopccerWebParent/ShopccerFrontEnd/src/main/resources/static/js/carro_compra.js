$(document).ready(function() {

    $(".linkMinus").on("click", function(event) {
        event.preventDefault();
        var idProducto = $(this).closest('ul').attr('pid');
        var talla = $(this).closest('ul').attr('pta');

        stockTalla = $("#stockTalla" +  idProducto + talla);
        cantidadStock = stockTalla.val();
        cantidadInput = $("#cantidad" + idProducto + talla);
        nuevaCantidad = parseInt(cantidadInput.val()) - 1;

        console.log("STOCK anterior: " + cantidadStock)

        if (nuevaCantidad > 0) {
            cantidadInput.val(nuevaCantidad);
            stockTalla.val(parseInt(cantidadStock) +1);
            console.log("STOCK actual: " + (parseInt(cantidadStock) +1));
            updateCantidad(idProducto, talla, nuevaCantidad, "minus");
        } else {
            showWarningModal('La cantidad mínima del pedido es de 1 unidad.');
        }
    });

    $(".linkPlus").on("click", function(event) {
        event.preventDefault();
        var idProducto = $(this).closest('ul').attr('pid');
        var talla = $(this).closest('ul').attr('pta');

        stockTalla = $("#stockTalla" +  idProducto + talla);
        cantidadInput = $("#cantidad" + idProducto + talla);
        nuevaCantidad = parseInt(cantidadInput.val()) + 1;
        cantidadStock = stockTalla.val();

        console.log("STOCK anterior: " + cantidadStock)

        if(cantidadStock == 0){
            showWarningModal('No hay stock para esa talla.')
        }
        if (cantidadStock > 0 ) {
            cantidadInput.val(nuevaCantidad);
            stockTalla.val(parseInt(cantidadStock) -1);
            console.log("STOCK actual: " + (parseInt(cantidadStock) -1));
            updateCantidad(idProducto, talla, nuevaCantidad,"plus");
        } else {
            showWarningModal('La cantidad máxima del pedido no puede ser superior a su stock.');
        }
    });
});

function updateCantidad(idProducto, talla, cantidad,variacionStock) {
    url = contextPath + "carro/update/" + idProducto + "/" + talla + "/" + cantidad+ "/" + variacionStock;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(updatedSubtotal) {
        updateSubtotal(updatedSubtotal, idProducto, talla);
        updateTotal();
    }).fail(function() {
        showErrorModal("Error al actualizar la cantidad de productos.");
    });
}

function updateSubtotal(updatedSubtotal, idProducto, talla) {
    //formattedSubtotal = $.number(updatedSubtotal, 2, ',','.');
    $("#subtotal" + idProducto + talla).text(updatedSubtotal+ ' €');
}

function updateTotal() {
    total = 0;

    $(".subtotal").each(function(index, element) {
        total += parseFloat(element.innerHTML.replaceAll(",", ""));
    });

    //formattedTotal = $.number(total, 2, ',','.');
    $("#totalEstimado").text(total.toFixed(2) + ' €');
}
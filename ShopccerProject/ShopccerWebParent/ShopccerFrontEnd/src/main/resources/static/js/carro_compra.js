$(document).ready(function() {

    $(".linkMinus").on("click", function(event) {
        event.preventDefault();
        var idProducto = $(this).closest('ul').attr('pid');
        var talla = $(this).closest('ul').attr('pta');

        cantidadInput = $("#cantidad" + idProducto + talla);
        nuevaCantidad = parseInt(cantidadInput.val()) - 1;

        if (nuevaCantidad > 0) {
            cantidadInput.val(nuevaCantidad);
        } else {
            showWarningModal('La cantidad mínima del pedido es de 1 unidad.');
        }
    });

    $('.linkPlus').click(function() {
        var idProducto = $(this).closest('ul').attr('pid');
        var talla = $(this).closest('ul').attr('pta');
        console.log('LinkPlus pulsado para el producto con ID: ' + idProducto);
        console.log('LinkPlus pulsado para el producto con talla: ' + talla);
    });


    $(".linkPlus").on("click", function(event) {
        event.preventDefault();
        var idProducto = $(this).closest('ul').attr('pid');
        var talla = $(this).closest('ul').attr('pta');

        stockTalla = $("#stockTalla" +  idProducto);

        cantidadInput = $("#cantidad" + idProducto + talla);
        nuevaCantidad = parseInt(cantidadInput.val()) + 1;

        cantidadStock = stockTalla.val();
        console.log("STOCK" + cantidadStock)

        if(cantidadStock == 0){
            showWarningModal('No hay stock para esa talla.')
        }
        if (nuevaCantidad <= cantidadStock) {
            cantidadInput.val(nuevaCantidad);
        } else {
            showWarningModal('La cantidad máxima del pedido no puede ser superior a su stock.');
        }
    });


});
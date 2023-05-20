$(document).ready(function() {

    var selectTalla = document.getElementById("selectTalla");
    var cantidadStock = selectTalla.value;
    console.log(cantidadStock);

    var options = selectTalla.options;
    for (var i = 0; i < options.length; i++) {
        var option = options[i];
        if (option.value === '0') {
            option.disabled = true;
        }
    }

    selectTalla.addEventListener("change", function() {
        cantidadStock = this.value;
        console.log(cantidadStock);
        $("#cantidad" + $("#idProductoHidden").val()).val(1);
    });

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
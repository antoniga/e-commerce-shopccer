var botonCarga;
var desplegablePais;

$(document).ready(function(){
    botonCarga = $("#botonCargaPaises");
    desplegablePais = $("#desplegablePais");

    botonCarga.click(function (){
       cargaPaises();
    });
});

function cargaPaises(){
    url=  "/ShopccerAdmin/paises/list";
    $.get(url, function (responseJSON){
        desplegablePais.empty();

        $.each(responseJSON,function (index,pais){
            valorOpcion = pais.idPais + " - "+pais.codigo;
            $("<option>").val(valorOpcion).text(pais.nombre).appendTo(desplegablePais);
        });
    }).done(function (){
        botonCarga.val("Recarga lista países");
        showToastMsg("Todos los países han sido cargados");
    }).fail(function (){
        showToastMsg("ERROR: No se ha podido conectar con el servidor");
    });
}

function showToastMsg(msg){
    $("#toastMsg").text(msg);
    $(".toast").toast('show');
}
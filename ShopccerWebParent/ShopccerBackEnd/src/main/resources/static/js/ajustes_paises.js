var botonCarga;
var desplegablePais;
var addPais;
var updatePais;
var deletePais;
var nombrePais;
var campoNombrePais;
var campoCodigoPais;
const SERVER_ERROR = "ERROR: No se ha podido conectar con el servidor";

$(document).ready(function(){
    botonCarga = $("#botonCargaPaises");
    desplegablePais = $("#desplegablePais");
    addPais = $("#addPais");
    updatePais = $("#updatePais");
    deletePais = $("#deletePais");
    nombrePais = $("#nombrePais");
    campoNombrePais = $("#campoNombrePais");
    campoCodigoPais = $("#campoCodigoPais");

    botonCarga.click(function (){
       cargaPaises();
    });

    desplegablePais.on("change", function() {
        changeFormComunidadToSelectedPais();
    });

    addPais.click(function() {
        if (addPais.val() == "Añadir") {
            savePais();
        } else {
            changeFormComunidadToNewPais();
        }
    });

    updatePais.click(function() {
        updateCountry();
    });

    deletePais.click(function() {
        deleteCountry();
    });

});

function validateFormCountry() {
    formPais = document.getElementById("formPais");
    if (!formPais.checkValidity()) {
        formPais.reportValidity();
        return false;
    }

    return true;
}


function deleteCountry() {
    optionValue = desplegablePais.val();
    countryId = optionValue.split("-")[0];

    url = "/paises/delete/" + countryId;

    $.ajax({
        type: 'DELETE',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function() {
        $("#desplegablePais option[value='" + optionValue + "']").remove();
        showToastMsg("El país ha sido eliminado");
        changeFormComunidadToNewPais();
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function updateCountry() {
    if (!validateFormCountry()) return;

    url = "/paises/save";
    countryName = campoNombrePais.val();
    countryCode = campoCodigoPais.val();

    countryId = desplegablePais.val().split("-")[0];

    jsonData = {idPais: countryId, nombre: countryName, codigo: countryCode};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(countryId) {
        $("#desplegablePais option:selected").val(countryId + "-" + countryCode);
        $("#desplegablePais option:selected").text(countryName);
        showToastMsg("El país ha sido actualizado");

        changeFormComunidadToNewPais();
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function savePais() {

   if (!validateFormCountry()) return;

    url = "/paises/save";
    countryName = campoNombrePais.val();
    countryCode = campoCodigoPais.val();
    jsonData = {nombre: countryName, codigo: countryCode};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(countryId) {
        selectNewlyAddedPais(countryId, countryCode, countryName);
        showToastMsg("Se ha añadido un nuevo país");
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function selectNewlyAddedPais(countryId, countryCode, countryName) {
    optionValue = countryId + "-" + countryCode;
    $("<option>").val(optionValue).text(countryName).appendTo(desplegablePais);

    $("#dropDownCountries option[value='" + optionValue + "']").prop("selected", true);

    campoCodigoPais.val("");
    campoNombrePais.val("").focus();
}

function changeFormComunidadToNewPais() {
    addPais.val("Añadir");
    campoNombrePais.text("Nombre país:");

    updatePais.prop("disabled", true);
    deletePais.prop("disabled", true);

    campoCodigoPais.val("");
    campoNombrePais.val("").focus();
}

function changeFormComunidadToSelectedPais() {
    addPais.prop("value", "Nuevo");
    updatePais.prop("disabled", false);
    deletePais.prop("disabled", false);

    nombrePais.text("País seleccionado:");

    nombrePaisSeleccionado = $("#desplegablePais option:selected").text();
    campoNombrePais.val(nombrePaisSeleccionado);

    codigoPaisVal = desplegablePais.val().split("-")[1];
    campoCodigoPais.val(codigoPaisVal);

}

function cargaPaises(){
    url=  "/paises/list";
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
        showToastMsg(SERVER_ERROR);
    });
}

function showToastMsg(msg){
    $("#toastMsg").text(msg);
    $(".toast").toast('show');
}

var dropDownPais;
var dataListComunidad;
var fieldComunidad;

$(document).ready(function() {
    dropDownPais = $("#pais");
    dataListComunidad = $("#listaComunidades");
    fieldComunidad = $("#comunidad");

    dropDownPais.on("change", function() {
        loadComunidadesDePais();
        fieldComunidad.val("").focus();
    });
});

function loadComunidadesDePais() {
    selectedPais = $("#pais option:selected");
    paisId = selectedPais.val();
    url = contextPath + "comunidades/list_comunidades_by_pais/" + paisId;

    $.get(url, function(responseJSON) {
        dataListComunidad.empty();

        $.each(responseJSON, function(index, comunidad) {
            $("<option>").val(comunidad.nombre).text(comunidad.nombre).appendTo(dataListComunidad);
        });

    }).fail(function() {
        alert('Error al conectar con el servidor.');
    });
}

function checkPasswordMatch(confirmPassword) {
    if (confirmPassword.value != $("#password").val()) {
        confirmPassword.setCustomValidity("Las contraseñas no son iguales.");
    } else {
        confirmPassword.setCustomValidity("");
    }
}
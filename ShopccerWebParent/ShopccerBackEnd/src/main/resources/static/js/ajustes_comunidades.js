var buttonLoad4States;
var dropDownCountry4States;
var dropDownStates;
var buttonAddState;
var buttonUpdateState;
var buttonDeleteState;
var labelStateName;
var fieldStateName;

$(document).ready(function() {
    buttonLoad4States = $("#buttonLoadCountriesForStates");
    dropDownCountry4States = $("#dropDownCountriesForStates");
    dropDownStates = $("#dropDownStates");
    buttonAddState = $("#buttonAddState");
    buttonUpdateState = $("#buttonUpdateState");
    buttonDeleteState = $("#buttonDeleteState");
    labelStateName = $("#labelStateName");
    fieldStateName = $("#fieldStateName");

    buttonLoad4States.click(function() {
        loadCountries4States();
    });

    dropDownCountry4States.on("change", function() {
        loadStates4Country();
    });

    dropDownStates.on("change", function() {
        changeFormStateToSelectedState();
    });

    buttonAddState.click(function() {
        if (buttonAddState.val() == "Añadir") {
            addState();
        } else {
            changeFormStateToNew();
        }
    });

    buttonUpdateState.click(function() {
        updateState();
    });

    buttonDeleteState.click(function() {
        deleteState();
    });
});

function deleteState() {
    stateId = dropDownStates.val();

    url = "/comunidades/delete/" + stateId;

    $.ajax({
        type: 'DELETE',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function() {
        $("#dropDownStates option[value='" + stateId + "']").remove();
        changeFormStateToNew();
        showToastMsg("La comunidad ha sido eliminada");
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function updateState() {
    if (!validateFormState()) return;

    url = "/comunidades/save";
    stateId = dropDownStates.val();
    stateName = fieldStateName.val();

    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    countryName = selectedCountry.text();

    jsonData = {idComunidad: stateId, nombre: stateName, pais: {idPais: countryId, nombre: countryName}};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(stateId) {
        $("#dropDownStates option:selected").text(stateName);
        showToastMsg("La comunidad ha sido actualizada");
        changeFormStateToNew();
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function addState() {
    if (!validateFormState()) return;

    url ="/comunidades/save";
    stateName = fieldStateName.val();

    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    countryName = selectedCountry.text();

    jsonData = {nombre: stateName, pais: {idPais: countryId, nombre: countryName}};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(stateId) {
        selectNewlyAddedState(stateId, stateName);
        showToastMsg("La nueva comunidad ha sido añadida");
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });

}

function validateFormState() {
    formState = document.getElementById("formState");
    if (!formState.checkValidity()) {
        formState.reportValidity();
        return false;
    }

    return true;
}

function selectNewlyAddedState(stateId, stateName) {
    $("<option>").val(stateId).text(stateName).appendTo(dropDownStates);

    $("#dropDownStates option[value='" + stateId + "']").prop("selected", true);

    fieldStateName.val("").focus();
}

function changeFormStateToNew() {
    buttonAddState.val("Añadir");
    labelStateName.text("Nombre comunidad/provincia:");

    buttonUpdateState.prop("disabled", true);
    buttonDeleteState.prop("disabled", true);

    fieldStateName.val("").focus();
}

function changeFormStateToSelectedState() {
    buttonAddState.prop("value", "Nuevo");
    buttonUpdateState.prop("disabled", false);
    buttonDeleteState.prop("disabled", false);

    labelStateName.text("Comunidad/Provincia seleccionada:");

    selectedStateName = $("#dropDownStates option:selected").text();
    fieldStateName.val(selectedStateName);

}

function loadStates4Country() {
    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    url = "/comunidades/list_by_pais/" + countryId;

    $.get(url, function(responseJSON) {
        dropDownStates.empty();

        $.each(responseJSON, function(index, state) {
            $("<option>").val(state.idComunidadDto).text(state.nombre).appendTo(dropDownStates);
        });

    }).done(function() {
        changeFormStateToNew();
        showToastMsg("Todas las comunidades han sido cargadas para el país " + selectedCountry.text());
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}

function loadCountries4States() {
    url = "/paises/list";
    $.get(url, function(responseJSON) {
        dropDownCountry4States.empty();

        $.each(responseJSON, function(index, country) {
            $("<option>").val(country.idPais).text(country.nombre).appendTo(dropDownCountry4States);
        });

    }).done(function() {
        buttonLoad4States.val("Actualiza lista países");
        showToastMsg("Todos los países han sido cargados");
    }).fail(function() {
        showToastMsg(SERVER_ERROR);
    });
}
document.getElementById('horaEntrada').addEventListener('change', function () {
    var entrada = this.value;
    var selectSalida = document.getElementById('horaSalida');
    var opciones = selectSalida.options;

    for (var i = 0; i < opciones.length; i++) {
        opciones[i].disabled = opciones[i].value <= entrada;
    }

    selectSalida.value = '';
});
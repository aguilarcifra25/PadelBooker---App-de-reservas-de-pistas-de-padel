const inputFecha = document.querySelector('input[name="fecha"]');
const selectEntrada = document.getElementById('horaEntrada');
const selectSalida = document.getElementById('horaSalida');

function actualizarHorasOcupadas() {

    const fecha = inputFecha.value;

    // Resetear opciones
    Array.from(selectEntrada.options).forEach(opt => {
        opt.disabled = false;
        opt.text = opt.text.replace(' (ocupada)', '');
    });

    if (!fecha) return;

    // Filtrar tramos del día seleccionado
    const tramosDelDia = reservasPista.filter(r => r.fecha === fecha);

    // Deshabilitar horas ocupadas
    Array.from(selectEntrada.options).forEach(opt => {
        if (!opt.value) return;
        const ocupada = tramosDelDia.some(t => opt.value >= t.entrada && opt.value < t.salida);
        if (ocupada) {
            opt.disabled = true;
            opt.text = opt.value + ' (ocupada)';
        }
    });

    selectEntrada.value = '';
    selectSalida.value = '';
}

inputFecha.addEventListener('change', actualizarHorasOcupadas);

// Control hora salida
selectEntrada.addEventListener('change', function () {
    const entrada = this.value;
    Array.from(selectSalida.options).forEach(opt => {
        opt.disabled = opt.value <= entrada;
    });
    selectSalida.value = '';
});
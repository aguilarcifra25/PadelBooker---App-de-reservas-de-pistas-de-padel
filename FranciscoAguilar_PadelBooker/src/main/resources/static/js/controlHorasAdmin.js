const inputFecha = document.getElementById('fecha');
const selectEntrada = document.getElementById('horaEntrada');
const selectSalida = document.getElementById('horaSalida');
const selectPistas = document.getElementById('numeros');

function actualizarHorasOcupadas() {

    const fecha = inputFecha.value;
    const pistasSeleccionadas = Array.from(selectPistas.selectedOptions).map(o => o.value);

    // Resetear opciones
    Array.from(selectEntrada.options).forEach(opt => {
        opt.disabled = false;
        opt.text = opt.text.replace(' (ocupada)', '');
    });

    if (!fecha || pistasSeleccionadas.length === 0) return;

    // Filtrar tramos ocupados para las pistas y fecha seleccionadas
    const tramosOcupados = todasReservas.filter(r =>
        r.fecha === fecha && pistasSeleccionadas.includes(r.pista)
    );

    // Deshabilitar horas ocupadas
    Array.from(selectEntrada.options).forEach(opt => {
        if (!opt.value) return;
        const ocupada = tramosOcupados.some(t => opt.value >= t.entrada && opt.value < t.salida);
        if (ocupada) {
            opt.disabled = true;
            opt.text = opt.value + ' (ocupada)';
        }
    });

    selectEntrada.value = '';
    selectSalida.value = '';
}

inputFecha.addEventListener('change', actualizarHorasOcupadas);
selectPistas.addEventListener('change', actualizarHorasOcupadas);

// Control hora salida
selectEntrada.addEventListener('change', function () {
    const entrada = this.value;
    Array.from(selectSalida.options).forEach(opt => {
        opt.disabled = opt.value <= entrada;
    });
    selectSalida.value = '';
});
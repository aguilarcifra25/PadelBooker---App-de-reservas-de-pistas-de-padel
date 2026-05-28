document.addEventListener('DOMContentLoaded', () => {
    const contenedor = document.getElementById('contenedorReserva');
    const pLuz = parseFloat(contenedor.getAttribute('data-precio-luz'));
    const pPalas = parseFloat(contenedor.getAttribute('data-precio-raqueta'));

    const selPista = document.getElementById('numero'); // Capturamos el select de pistas
    const selEntrada = document.getElementById('horaEntrada');
    const selSalida = document.getElementById('horaSalida');
    const chkLuz = document.getElementById('usaLuz');
    const numPalas = document.getElementById('cantRaquetas');

    const seccion = document.getElementById('seccionDesglose');
    const txtTiempo = document.getElementById('txtDuracion');
    const fLuz = document.getElementById('filaExtraLuz');
    const fPalas = document.getElementById('filaExtraPalas');
    const txtTotal = document.getElementById('precioDinamico');

    function calcular() {
        if (!selEntrada.value || !selSalida.value) return;

        let pBase = 0;
        if (selPista && selPista.selectedIndex >= 0) {
            const opcionSeleccionada = selPista.options[selPista.selectedIndex];
            const textoOpcion = opcionSeleccionada.text;

            const coincidencia = textoOpcion.match(/([\d.]+)(?=€\/h)/);
            if (coincidencia) {
                pBase = parseFloat(coincidencia[1]);
            }
        }

        if (!pBase || isNaN(pBase)) {
            pBase = parseFloat(contenedor.getAttribute('data-precio-base')) || 0.0;
        }

        const [hE, mE] = selEntrada.value.split(':').map(Number);
        const [hS, mS] = selSalida.value.split(':').map(Number);

        const horas = (hS * 60 + mS - (hE * 60 + mE)) / 60;

        if (horas <= 0) {
            seccion.classList.add('d-none');
            return;
        }

        let total = horas * pBase;

        if (chkLuz.checked) {
            total += horas * pLuz;
            fLuz.classList.remove('d-none');
            document.getElementById('txtPrecioLuz').textContent = (horas * pLuz).toFixed(2) + '€';
        } else {
            fLuz.classList.add('d-none');
        }

        const cantPalas = parseInt(numPalas.value) || 0;
        if (cantPalas > 0) {
            total += cantPalas * pPalas;
            fPalas.classList.remove('d-none');
            document.getElementById('txtPrecioPalas').textContent = (cantPalas * pPalas).toFixed(2) + '€';
        } else {
            fPalas.classList.add('d-none');
        }

        txtTiempo.textContent = horas + ' horas';
        txtTotal.textContent = total.toFixed(2) + '€';
        seccion.classList.remove('d-none');
    }

    if (selPista) {
        selPista.addEventListener('change', calcular);
    }
    selEntrada.addEventListener('change', calcular);
    selSalida.addEventListener('change', calcular);
    chkLuz.addEventListener('change', calcular);
    numPalas.addEventListener('input', calcular);
});
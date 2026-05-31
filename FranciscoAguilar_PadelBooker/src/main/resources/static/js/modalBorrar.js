document.getElementById('modalConfirmarBorrar').addEventListener('show.bs.modal', function (event) {
    const boton = event.relatedTarget;
    const url = boton.getAttribute('data-url');
    document.getElementById('btnConfirmarBorrar').setAttribute('href', url);
});
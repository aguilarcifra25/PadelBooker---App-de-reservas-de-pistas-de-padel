document.addEventListener("DOMContentLoaded", function () {
    const pw = document.getElementById('password');
    const pwRepe = document.getElementById('repetirPassword');
    const error = document.getElementById('repetirError');
    const formulario = pw.closest('form');

    const btnTogglePw = document.getElementById('togglePassword');

    function comprobar() {
        // Me sirve para que no se active si el campo de repetir la pw esta vacio
        if (pwRepe.value === "") {
            pwRepe.classList.remove('is-invalid');
            error.style.display = 'none';
            return false;
        }

        // Muestra y quita el error
        if (pw.value !== pwRepe.value) {
            pwRepe.classList.add('is-invalid');
            error.style.display = 'block';
            return false;
        } else {
            pwRepe.classList.remove('is-invalid');
            error.style.display = 'none';
            return true;
        }
    }

    // Lo ejecuta cuando se va escribiendo
    pw.addEventListener('input', comprobar);
    pwRepe.addEventListener('input', comprobar);

    if (formulario) {
        formulario.addEventListener('submit', function (event) {
            // Esto obliga a que se rellene el comprobar contraseña
            if (comprobar() === false) {
                event.preventDefault();
                pwRepe.classList.add('is-invalid');
                pwRepe.focus();
            }
        });
    }

    if (btnTogglePw) {
        btnTogglePw.addEventListener('click', function () {
            const esPassword = pw.type === 'password';

            // Cambia el tipo de ambos inputs en una sola línea
            pw.type = pwRepe.type = esPassword ? 'text' : 'password';

        });
    }
});
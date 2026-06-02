# PadelBooker - App de reservas de pistas de pádel

## Datos para demo

Usuario estándar (tiene datos creados) --> Nombre: user   Contraseña: user  
Admin estándar (tiene datos creados) --> Nombre: admin   Contraseña: admin 

## Contexto de la aplicación

Se trata de una aplicación web que tiene como finalidad la gestión de clientes y reservas de pistas de pádel. Se presenta con una página principal hablando de las características del club y otra de información acerca del club y su contacto.

Más alla de eso, se le permite a los clientes realizar sus reservas y a los administradores gestionar las reservas, los clientes, y las pistas.

También se ha creado un sistema de cupones, distinguiendo entre los promocionales y los de fidelidad.

## Funcionalidades de la app

### Como usuario

- Crear un nuevo usuario*
- Iniciar sesión
- Reservas de pistas**
- Edición de datos de usuario*
- Edición de reservas del usuario
- Cancelar reservas del usuario
- Dar de baja
- Conseguir cupones de fidelidad (cada 15 horas jugadas un cupón del 15%)
- Cerrar sesión

*Al crear el usuario el nombre y correo deben ser distintos a los de los usuarios ya registrados  
**La reserva de pistas incluye evitar solapamientos, evitar una hora de salida anterior a la de entrada, elegir fechas posteriores a este año y cálculo dinámico del precio, además de aplicación de cupones de descuento.

### Como administrador

- Crear usuarios, editarlos y borrarlos
- Filtrar usuarios por nombre, email o rol
- Crear pistas, editarlas y borrarlas
- Crear reservas (incluye varias pistas y añadir observaciones), editarlas y borrarlas
- Filtrar reservas por usuario, fecha y hora de entrada
- Crear cupones de promoción y borrarlos
- Ver estadísticas

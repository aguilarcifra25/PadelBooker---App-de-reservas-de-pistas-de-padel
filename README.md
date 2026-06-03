# PadelBooker - App de Gestión y Reserva de Pistas de Pádel

PadelBooker es una aplicación web diseñada para centralizar la gestión de clientes y la reserva automatizada de pistas de pádel. Ofrece una interfaz pública informativa sobre el club y un sistema privado con control de acceso basado en roles (**USER / ADMIN**), destacando por su cálculo dinámico de precios y un sistema de fidelización de usuarios.

---

# Índice

1. [Datos para Demo (Acceso Rápido)](#datos-para-demo-acceso-rápido)
2. [Funcionalidades del Sistema](#funcionalidades-del-sistema)
   - [Como Usuario (USER)](#como-usuario-user)
   - [Como Administrador (ADMIN)](#como-administrador-admin)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Configuración y Persistencia](#configuración-y-persistencia)
6. [Puesta en Marcha](#puesta-en-marcha)
   - [Requisitos Previos](#requisitos-previos)
   - [Instrucciones de Ejecución](#instrucciones-de-ejecución)
7. [Matriz de Control de Acceso (Rutas)](#matriz-de-control-de-acceso-rutas)

---

## Datos para Demo (Acceso Rápido)

Al arrancar, la aplicación se puebla automáticamente con datos de prueba a través de `DataSeed.java`.

Puede utilizar las siguientes credenciales para las pruebas:

| Rol | Usuario / Nombre | Contraseña | Estado en Demo |
|------|-----------------|------------|---------------|
| **Usuario Estándar** | user | user | Con historial y datos creados |
| **Administrador** | admin | admin | Con acceso total al panel |

---

## Funcionalidades del Sistema

### Como Usuario (USER)

- **Registro Autónomo**
  - Creación de cuenta con validación de unicidad (nombre y correo electrónico únicos).

- **Gestión de Perfil**
  - Edición de datos personales.
  - Opción de dar de baja la cuenta.

- **Sistema de Reservas Inteligente**
  - Consulta de pistas y reserva online.
  - Validaciones estrictas:
    - Bloqueo de solapamientos horarios.
    - Control de coherencia temporal.
    - Restricciones de fecha válidas.
  - Cálculo automático del coste según parámetros de la pista.
  - Gestión de reservas propias (editar y cancelar).

- **Sistema de Fidelidad**
  - Obtención automática de un cupón del **15% de descuento** por cada **15 horas de juego completadas**.

### Como Administrador (ADMIN)

- **Control de Usuarios**
  - CRUD completo de usuarios.
  - Filtros por nombre, email o rol.

- **Gestión del Catálogo**
  - CRUD completo de pistas.
  - Modificación de:
    - Tipo de suelo.
    - Modalidad indoor/outdoor.
    - Precio.

- **Gestión Avanzada de Reservas**
  - Creación manual de reservas complejas.
  - Asociación de múltiples pistas a una misma reserva.
  - Inclusión de observaciones.
  - Edición y cancelación global de cualquier reserva.
  - Filtros avanzados por:
    - Usuario.
    - Fecha.
    - Hora de entrada.

- **Gestión de Cupones**
  - Creación de cupones promocionales.
  - Retirada de cupones existentes.

- **Módulo de Estadísticas**
  - Panel visual con métricas clave del rendimiento del club.

---

## Tecnologías Utilizadas

| Categoría | Tecnología |
|------------|------------|
| Lenguaje | Java 21 |
| Framework Back-End | Spring Boot 3.4.x |
| Seguridad | Spring Security |
| Persistencia / ORM | Hibernate + JPA |
| Motor de Plantillas | Thymeleaf |
| Base de Datos | H2 |
| Utilidades | Lombok |
| Framework Front-End | Bootstrap 5.x |
| Gestor de Dependencias | Maven (Wrapper incluido) |

---

## Estructura del Proyecto

```text
src/
└── main/
    ├── java/com/salesianostriana/dam/franciscoaguilar_padelbooker/
    │   ├── controller/        # Controladores MVC
    │   ├── excepciones/       # Excepciones personalizadas y controlador global
    │   ├── model/             # Entidades JPA
    │   ├── repository/        # Repositorios Spring Data JPA
    │   ├── security/          # Configuración de seguridad y roles
    │   ├── service/           # Lógica de negocio
    │   └── DataSeed.java      # Datos iniciales de la demo
    │
    └── resources/
        ├── static/            # CSS, JS e imágenes
        ├── templates/         # Vistas Thymeleaf
        └── application.properties
```

---

## Configuración y Persistencia

El comportamiento del servidor y de la base de datos se configura en:

```text
src/main/resources/application.properties
```

### Properties

```properties
server.port=9000

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=jdbc:h2:./db/basedatos;DB_CLOSE_ON_EXIT=FALSE
```

### Nota de Desarrollo

La base de datos guarda la información de manera persistente en:

```text
./db/basedatos
```

Al utilizar:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

el esquema se elimina y reconstruye en cada reinicio, garantizando que los datos de demostración se carguen siempre desde un estado limpio.

---

## Puesta en Marcha

### Requisitos Previos

- Java 21 o superior.
- Terminal compatible con Maven Wrapper (`mvnw`).

### Instrucciones de Ejecución

#### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd FranciscoAguilar_PadelBooker
```

#### 2. Lanzar la aplicación

##### Linux / macOS

```bash
./mvnw spring-boot:run
```

##### Windows

```cmd
mvnw.cmd spring-boot:run
```

#### 3. Acceder a la aplicación

**Aplicación Web**

```text
http://localhost:9000
```

**Consola H2**

```text
http://localhost:9000/h2-console
```

Asegúrese de utilizar la siguiente URL JDBC:

```text
jdbc:h2:./db/basedatos
```

---

## Matriz de Control de Acceso (Rutas)

| Ruta | Permiso Requerido | Descripción |
|--------|-----------------|-------------|
| `/`, `/home`, `/nosotros`, `/pistas` | Público | Información general y catálogo de pistas |
| `/login`, `/crearUsuario` | Público | Autenticación y registro |
| `/detallesPista/`, `/perfil/`, `/reserva/**` | USER / ADMIN | Gestión de perfil y reservas |
| `/admin/**` | Solo ADMIN | CRUD de mantenimiento, cupones y estadísticas |
| `/h2-console/**` | Desarrollo | Consola de administración de la base de datos |

---

## Resumen

PadelBooker es una aplicación de gestión integral para clubes de pádel basada en Spring Boot que incorpora:

- Sistema de autenticación y autorización por roles.
- Gestión completa de usuarios, pistas y reservas.
- Cálculo dinámico de precios.
- Sistema de fidelización mediante cupones.
- Panel administrativo avanzado.
- Persistencia con H2 y JPA/Hibernate.
- Interfaz web desarrollada con Thymeleaf y Bootstrap.

---

## Autor

Francisco Aguilar

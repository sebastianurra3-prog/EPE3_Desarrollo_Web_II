# EducaParaTodos — Plataforma Educativa en Línea (EPE3)

Proyecto desarrollado para la actividad evaluativa **"Desarrollo de Plataforma Educativa en
Línea para Comunidades Desfavorecidas"** — IPChile, Desarrollo Web II.

## Stack tecnológico

- Java 11 + Jakarta EE (Servlet 5 + JSP + JSTL)
- JPA con **Hibernate** como proveedor
- **MySQL** como base de datos
- Maven para gestión de dependencias y empaquetado (WAR)
- Arquitectura en capas: **Modelo (JPA) → DAO → Controlador (Servlet) → Vista (JSP)**

## Estructura del proyecto

```
src/main/java/com/educaparatodos/
├── model/        Entidades JPA: Usuario, Curso, Leccion
├── dao/          Acceso a datos: CRUD + JPQL + operaciones masivas
├── controller/   Servlets (controladores CRUD)
└── util/         JPAUtil (EntityManagerFactory)

src/main/resources/META-INF/persistence.xml   Configuración de la unidad de persistencia
src/main/webapp/                              Vistas JSP + CSS responsivo
```

## Modelo de datos (Mapeo Objeto-Relacional)

- **Usuario** `1..N ↔ N..1` **Curso** vía relación **N:M** `usuario_curso` (inscripción).
- **Curso** `1:N` **Leccion** (contenidos del curso, con `orden` y `cascade=ALL`).

## Consultas JPQL implementadas

Ubicadas en `CursoDAO` y `UsuarioDAO`:

- Búsqueda de cursos por **tema** (`LIKE`), por **nivel de dificultad** y por **popularidad**
  (orden descendente), además de una búsqueda combinada tema + nivel.
- Búsqueda de usuario por email.

## Operaciones masivas (UPDATE / DELETE)

- `CursoDAO.actualizarPopularidadCursosRecientes(fecha)` → `UPDATE Curso ... WHERE fechaCreacion >= :desde`
- `CursoDAO.eliminarCursosPocoPopulares(popMin, fecha)` → `DELETE FROM Curso ... WHERE popularidad < :popMin AND fechaCreacion < :antes`
- `UsuarioDAO.actualizarRolUsuariosAntiguos(fecha, rol)` → `UPDATE Usuario ... WHERE fechaRegistro < :antesDe`
- `UsuarioDAO.eliminarUsuariosRegistradosAntesDe(fecha)` → `DELETE FROM Usuario ... WHERE fechaRegistro < :fecha`

Se pueden probar desde la página de inicio (botones "Masivo cursos" / "Masivo usuarios") o
navegando a `cursos?accion=masivo` y `usuarios?accion=masivo`.

## Controladores (Servlets)

- `CursoServlet` (`/cursos`): listar, buscar, crear, editar, eliminar, ejecutar operaciones masivas.
- `UsuarioServlet` (`/usuarios`): listar, crear, editar, eliminar, ver perfil, inscribir en curso, masivo.
- `LeccionServlet` (`/lecciones`): crear, editar, eliminar lecciones de un curso.

## Vistas

- `index.jsp` — página de inicio.
- `cursos.jsp` — búsqueda/listado de cursos con filtros.
- `curso-detalle.jsp` — detalle de curso + lecciones.
- `usuarios.jsp` / `perfil.jsp` — listado y perfil de usuario con cursos inscritos.
- Formularios: `curso-form.jsp`, `usuario-form.jsp`, `leccion-form.jsp`.

Todas las vistas usan un único `css/style.css` con diseño responsivo (grid + media query).

## Cómo ejecutar el proyecto localmente

1. Instala MySQL y crea la base de datos (o deja que se cree sola, ver `persistence.xml`).
2. Ajusta usuario/contraseña de MySQL en `src/main/resources/META-INF/persistence.xml`.
3. Compila el WAR:
   ```
   mvn clean package
   ```
4. Despliega `target/epe3-educaparatodos.war` en Tomcat 10+ (o cualquier servidor Jakarta EE 9+).
5. Abre `http://localhost:8080/epe3-educaparatodos/`.

## Autor

Sebastián Urra — Ingeniería en Informática, IPChile (modalidad telemática).

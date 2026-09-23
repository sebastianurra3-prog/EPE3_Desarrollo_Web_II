<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Perfil de ${usuario.nombre}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <div class="card">
            <h2>👤 ${usuario.nombre}</h2>
            <p>Email: ${usuario.email}</p>
            <p>Rol: ${usuario.rol}</p>
            <p>Registrado desde: ${usuario.fechaRegistro}</p>
        </div>

        <h3>Cursos inscritos</h3>
        <div class="card-grid">
            <c:forEach var="curso" items="${usuario.cursosInscritos}">
                <div class="card">
                    <h3>${curso.titulo}</h3>
                    <span class="badge">${curso.tema}</span>
                    <span class="badge">${curso.nivelDificultad}</span>
                </div>
            </c:forEach>
            <c:if test="${empty usuario.cursosInscritos}">
                <p>Este usuario aún no se ha inscrito en ningún curso.</p>
            </c:if>
        </div>

        <h3>Inscribirse en un curso</h3>
        <form class="filtros" method="get" action="usuarios">
            <input type="hidden" name="accion" value="inscribir">
            <input type="hidden" name="usuarioId" value="${usuario.id}">
            <select name="cursoId" required>
                <c:forEach var="curso" items="${todosLosCursos}">
                    <option value="${curso.id}">${curso.titulo}</option>
                </c:forEach>
            </select>
            <button class="btn" type="submit">Inscribir</button>
        </form>
    </div>
</body>
</html>

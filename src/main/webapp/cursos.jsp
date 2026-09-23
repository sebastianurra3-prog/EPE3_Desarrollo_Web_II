<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cursos - EducaParaTodos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav>
            <a href="index.jsp">Inicio</a>
            <a href="cursos">Cursos</a>
            <a href="usuarios">Usuarios</a>
        </nav>
    </header>

    <div class="container">
        <h2>Búsqueda de cursos</h2>

        <c:if test="${not empty mensajeMasivo}">
            <div class="alert">${mensajeMasivo}</div>
        </c:if>

        <form class="filtros" method="get" action="cursos">
            <input type="text" name="tema" placeholder="Buscar por tema..." value="${tema}">
            <select name="nivel">
                <option value="">Cualquier nivel</option>
                <option value="BASICO" ${nivel == 'BASICO' ? 'selected' : ''}>Básico</option>
                <option value="INTERMEDIO" ${nivel == 'INTERMEDIO' ? 'selected' : ''}>Intermedio</option>
                <option value="AVANZADO" ${nivel == 'AVANZADO' ? 'selected' : ''}>Avanzado</option>
            </select>
            <button class="btn" type="submit">Buscar</button>
            <a class="btn secondary" href="cursos?accion=nuevo">+ Nuevo curso</a>
        </form>

        <div class="card-grid">
            <c:forEach var="curso" items="${cursos}">
                <div class="card">
                    <h3>${curso.titulo}</h3>
                    <p>${curso.descripcion}</p>
                    <span class="badge">${curso.tema}</span>
                    <span class="badge">${curso.nivelDificultad}</span>
                    <p>⭐ Popularidad: ${curso.popularidad}</p>
                    <a class="btn" href="curso-detalle.jsp?id=${curso.id}">Ver detalle</a>
                    <a class="btn secondary" href="cursos?accion=editar&id=${curso.id}">Editar</a>
                    <a class="btn danger" href="cursos?accion=eliminar&id=${curso.id}"
                       onclick="return confirm('¿Eliminar este curso?');">Eliminar</a>
                </div>
            </c:forEach>
            <c:if test="${empty cursos}">
                <p>No se encontraron cursos con esos filtros.</p>
            </c:if>
        </div>
    </div>
</body>
</html>

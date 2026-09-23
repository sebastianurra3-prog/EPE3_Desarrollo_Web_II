<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Usuarios - EducaParaTodos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <h2>Usuarios registrados</h2>

        <c:if test="${not empty mensajeMasivo}">
            <div class="alert">${mensajeMasivo}</div>
        </c:if>

        <a class="btn" href="usuarios?accion=nuevo">+ Nuevo usuario</a>

        <table>
            <thead><tr><th>Nombre</th><th>Email</th><th>Rol</th><th>Registro</th><th>Acciones</th></tr></thead>
            <tbody>
            <c:forEach var="u" items="${usuarios}">
                <tr>
                    <td>${u.nombre}</td>
                    <td>${u.email}</td>
                    <td>${u.rol}</td>
                    <td>${u.fechaRegistro}</td>
                    <td>
                        <a class="btn" href="usuarios?accion=perfil&id=${u.id}">Perfil</a>
                        <a class="btn secondary" href="usuarios?accion=editar&id=${u.id}">Editar</a>
                        <a class="btn danger" href="usuarios?accion=eliminar&id=${u.id}"
                           onclick="return confirm('¿Eliminar este usuario?');">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.educaparatodos.dao.CursoDAO" %>
<%@ page import="com.educaparatodos.dao.LeccionDAO" %>
<%@ page import="com.educaparatodos.model.Curso" %>
<%@ page import="com.educaparatodos.model.Leccion" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    Long cursoId = Long.valueOf(request.getParameter("id"));
    Curso curso = new CursoDAO().buscarPorId(cursoId);
    List<Leccion> lecciones = new LeccionDAO().listarPorCurso(cursoId);
    request.setAttribute("curso", curso);
    request.setAttribute("lecciones", lecciones);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${curso.titulo}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <div class="card">
            <h2>${curso.titulo}</h2>
            <p>${curso.descripcion}</p>
            <span class="badge">${curso.tema}</span>
            <span class="badge">${curso.nivelDificultad}</span>
            <p>⭐ Popularidad: ${curso.popularidad}</p>
        </div>

        <h3>Lecciones</h3>
        <a class="btn" href="lecciones?accion=nueva&cursoId=${curso.id}">+ Nueva lección</a>

        <table>
            <thead><tr><th>Orden</th><th>Título</th><th>Acciones</th></tr></thead>
            <tbody>
            <c:forEach var="leccion" items="${lecciones}">
                <tr>
                    <td>${leccion.orden}</td>
                    <td>${leccion.titulo}</td>
                    <td>
                        <a class="btn secondary" href="lecciones?accion=editar&id=${leccion.id}">Editar</a>
                        <a class="btn danger" href="lecciones?accion=eliminar&id=${leccion.id}&cursoId=${curso.id}"
                           onclick="return confirm('¿Eliminar esta lección?');">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

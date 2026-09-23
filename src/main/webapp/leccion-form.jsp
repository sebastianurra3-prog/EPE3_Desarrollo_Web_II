<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Formulario de lección</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <div class="card" style="max-width:500px;margin:auto;">
            <h2>${leccion != null ? 'Editar lección' : 'Nueva lección'}</h2>
            <form method="post" action="lecciones">
                <input type="hidden" name="id" value="${leccion.id}">
                <input type="hidden" name="cursoId" value="${cursoId}">
                <p><input type="text" name="titulo" placeholder="Título" value="${leccion.titulo}" required style="width:100%"></p>
                <p><textarea name="contenido" placeholder="Contenido">${leccion.contenido}</textarea></p>
                <p><input type="number" name="orden" placeholder="Orden" value="${leccion.orden}" min="1" required></p>
                <button class="btn" type="submit">Guardar</button>
                <a class="btn secondary" href="curso-detalle.jsp?id=${cursoId}">Cancelar</a>
            </form>
        </div>
    </div>
</body>
</html>

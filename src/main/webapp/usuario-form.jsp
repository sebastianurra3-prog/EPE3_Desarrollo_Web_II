<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Formulario de usuario</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <div class="card" style="max-width:500px;margin:auto;">
            <h2>${usuario != null ? 'Editar usuario' : 'Nuevo usuario'}</h2>
            <form method="post" action="usuarios">
                <input type="hidden" name="id" value="${usuario.id}">
                <p><input type="text" name="nombre" placeholder="Nombre completo" value="${usuario.nombre}" required style="width:100%"></p>
                <p><input type="email" name="email" placeholder="Email" value="${usuario.email}" required style="width:100%"></p>
                <p><input type="password" name="password" placeholder="Contraseña" required style="width:100%"></p>
                <button class="btn" type="submit">Guardar</button>
                <a class="btn secondary" href="usuarios">Cancelar</a>
            </form>
        </div>
    </div>
</body>
</html>

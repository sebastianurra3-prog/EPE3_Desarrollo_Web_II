<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Formulario de curso</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">🎓 EducaParaTodos</div>
        <nav><a href="index.jsp">Inicio</a><a href="cursos">Cursos</a><a href="usuarios">Usuarios</a></nav>
    </header>

    <div class="container">
        <div class="card" style="max-width:500px;margin:auto;">
            <h2>${curso != null ? 'Editar curso' : 'Nuevo curso'}</h2>
            <form method="post" action="cursos">
                <input type="hidden" name="id" value="${curso.id}">
                <p><input type="text" name="titulo" placeholder="Título" value="${curso.titulo}" required style="width:100%"></p>
                <p><textarea name="descripcion" placeholder="Descripción">${curso.descripcion}</textarea></p>
                <p><input type="text" name="tema" placeholder="Tema (ej: Programación)" value="${curso.tema}" required style="width:100%"></p>
                <p>
                    <select name="nivelDificultad" required style="width:100%">
                        <option value="">-- Nivel de dificultad --</option>
                        <option value="BASICO" ${curso.nivelDificultad == 'BASICO' ? 'selected' : ''}>Básico</option>
                        <option value="INTERMEDIO" ${curso.nivelDificultad == 'INTERMEDIO' ? 'selected' : ''}>Intermedio</option>
                        <option value="AVANZADO" ${curso.nivelDificultad == 'AVANZADO' ? 'selected' : ''}>Avanzado</option>
                    </select>
                </p>
                <button class="btn" type="submit">Guardar</button>
                <a class="btn secondary" href="cursos">Cancelar</a>
            </form>
        </div>
    </div>
</body>
</html>

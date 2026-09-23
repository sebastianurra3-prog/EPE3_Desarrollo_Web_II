<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>EducaParaTodos</title>
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
        <section class="hero">
            <h1>Educación gratuita para todos</h1>
            <p>Cursos en línea abiertos para comunidades desfavorecidas.</p>
            <a href="cursos" class="btn">Explorar cursos</a>
        </section>

        <div class="card-grid">
            <div class="card">
                <h3>📚 Cursos</h3>
                <p>Busca cursos por tema, nivel de dificultad o popularidad.</p>
                <a href="cursos" class="btn">Ver cursos</a>
            </div>
            <div class="card">
                <h3>👤 Usuarios</h3>
                <p>Administra usuarios y revisa sus cursos inscritos.</p>
                <a href="usuarios" class="btn">Ver usuarios</a>
            </div>
            <div class="card">
                <h3>⚙️ Mantenimiento</h3>
                <p>Ejecuta operaciones masivas (UPDATE / DELETE) de ejemplo.</p>
                <a href="cursos?accion=masivo" class="btn secondary">Masivo cursos</a>
                <a href="usuarios?accion=masivo" class="btn secondary">Masivo usuarios</a>
            </div>
        </div>
    </div>
</body>
</html>

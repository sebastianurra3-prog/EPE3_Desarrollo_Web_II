package com.educaparatodos.controller;

import com.educaparatodos.dao.CursoDAO;
import com.educaparatodos.dao.UsuarioDAO;
import com.educaparatodos.model.Curso;
import com.educaparatodos.model.Usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador CRUD de Usuario + inscripción a cursos + operaciones masivas.
 *
 * Rutas:
 *  GET  /usuarios                    -> lista de usuarios
 *  GET  /usuarios?accion=nuevo       -> formulario de registro
 *  GET  /usuarios?accion=editar&id=  -> formulario de edición
 *  GET  /usuarios?accion=eliminar&id=-> elimina usuario
 *  GET  /usuarios?accion=perfil&id=  -> ver perfil (cursos inscritos)
 *  GET  /usuarios?accion=inscribir&usuarioId=&cursoId= -> inscribe en curso
 *  GET  /usuarios?accion=masivo      -> operaciones UPDATE/DELETE masivas de ejemplo
 *  POST /usuarios                    -> guarda (crear o actualizar)
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                mostrarFormulario(req, resp, null);
                break;
            case "editar":
                Long id = Long.valueOf(req.getParameter("id"));
                mostrarFormulario(req, resp, usuarioDAO.buscarPorId(id));
                break;
            case "eliminar":
                usuarioDAO.eliminar(Long.valueOf(req.getParameter("id")));
                resp.sendRedirect("usuarios");
                break;
            case "perfil":
                mostrarPerfil(req, resp);
                break;
            case "inscribir":
                Long usuarioId = Long.valueOf(req.getParameter("usuarioId"));
                Long cursoId = Long.valueOf(req.getParameter("cursoId"));
                usuarioDAO.inscribirEnCurso(usuarioId, cursoId);
                resp.sendRedirect("usuarios?accion=perfil&id=" + usuarioId);
                break;
            case "masivo":
                int actualizados = usuarioDAO.actualizarRolUsuariosAntiguos(LocalDate.now().minusYears(1), "VETERANO");
                int eliminados = usuarioDAO.eliminarUsuariosRegistradosAntesDe(LocalDate.now().minusYears(5));
                req.setAttribute("mensajeMasivo",
                    "Actualizados: " + actualizados + " usuario(s). Eliminados: " + eliminados + " usuario(s).");
                listar(req, resp);
                break;
            default:
                listar(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("usuarios", usuarioDAO.listarTodos());
        RequestDispatcher rd = req.getRequestDispatcher("usuarios.jsp");
        rd.forward(req, resp);
    }

    private void mostrarPerfil(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Usuario usuario = usuarioDAO.buscarPorIdConCursos(id);
        List<Curso> todosLosCursos = cursoDAO.listarTodos();
        req.setAttribute("usuario", usuario);
        req.setAttribute("todosLosCursos", todosLosCursos);
        RequestDispatcher rd = req.getRequestDispatcher("perfil.jsp");
        rd.forward(req, resp);
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws ServletException, IOException {
        req.setAttribute("usuario", usuario);
        RequestDispatcher rd = req.getRequestDispatcher("usuario-form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Usuario usuario = (idParam == null || idParam.isBlank())
                ? new Usuario()
                : usuarioDAO.buscarPorId(Long.valueOf(idParam));

        usuario.setNombre(req.getParameter("nombre"));
        usuario.setEmail(req.getParameter("email"));
        usuario.setPassword(req.getParameter("password"));

        if (usuario.getId() == null) {
            usuarioDAO.crear(usuario);
        } else {
            usuarioDAO.actualizar(usuario);
        }
        resp.sendRedirect("usuarios");
    }
}

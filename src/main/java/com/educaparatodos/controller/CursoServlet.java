package com.educaparatodos.controller;

import com.educaparatodos.dao.CursoDAO;
import com.educaparatodos.model.Curso;

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
 * Controlador CRUD de Curso + endpoint de búsqueda (JPQL) y
 * operaciones masivas (UPDATE/DELETE) exigidas en la actividad.
 *
 * Rutas:
 *  GET  /cursos                -> lista / búsqueda de cursos
 *  GET  /cursos?accion=nuevo   -> formulario de creación
 *  GET  /cursos?accion=editar&id=  -> formulario de edición
 *  GET  /cursos?accion=eliminar&id= -> elimina un curso
 *  GET  /cursos?accion=masivo  -> ejecuta operaciones UPDATE/DELETE masivas de ejemplo
 *  POST /cursos                -> guarda (crear o actualizar)
 */
@WebServlet("/cursos")
public class CursoServlet extends HttpServlet {

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
                mostrarFormulario(req, resp, cursoDAO.buscarPorId(id));
                break;
            case "eliminar":
                cursoDAO.eliminar(Long.valueOf(req.getParameter("id")));
                resp.sendRedirect("cursos");
                break;
            case "masivo":
                // Ejemplo de operaciones masivas pedidas por la rúbrica
                int actualizados = cursoDAO.actualizarPopularidadCursosRecientes(LocalDate.now().minusMonths(1));
                int eliminados = cursoDAO.eliminarCursosPocoPopulares(1, LocalDate.now().minusYears(2));
                req.setAttribute("mensajeMasivo",
                    "Actualizados: " + actualizados + " curso(s). Eliminados: " + eliminados + " curso(s).");
                listarConBusqueda(req, resp);
                break;
            default:
                listarConBusqueda(req, resp);
        }
    }

    private void listarConBusqueda(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String tema = req.getParameter("tema");
        String nivel = req.getParameter("nivel");

        List<Curso> cursos;
        if ((tema != null && !tema.isBlank()) || (nivel != null && !nivel.isBlank())) {
            cursos = cursoDAO.buscarPorTemaYNivel(tema, nivel);
        } else {
            cursos = cursoDAO.listarTodos();
        }

        req.setAttribute("cursos", cursos);
        req.setAttribute("tema", tema);
        req.setAttribute("nivel", nivel);
        RequestDispatcher rd = req.getRequestDispatcher("cursos.jsp");
        rd.forward(req, resp);
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp, Curso curso)
            throws ServletException, IOException {
        req.setAttribute("curso", curso);
        RequestDispatcher rd = req.getRequestDispatcher("curso-form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Curso curso = (idParam == null || idParam.isBlank())
                ? new Curso()
                : cursoDAO.buscarPorId(Long.valueOf(idParam));

        curso.setTitulo(req.getParameter("titulo"));
        curso.setDescripcion(req.getParameter("descripcion"));
        curso.setTema(req.getParameter("tema"));
        curso.setNivelDificultad(req.getParameter("nivelDificultad"));

        if (curso.getId() == null) {
            cursoDAO.crear(curso);
        } else {
            cursoDAO.actualizar(curso);
        }
        resp.sendRedirect("cursos");
    }
}

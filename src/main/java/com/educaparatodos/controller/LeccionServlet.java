package com.educaparatodos.controller;

import com.educaparatodos.dao.CursoDAO;
import com.educaparatodos.dao.LeccionDAO;
import com.educaparatodos.model.Curso;
import com.educaparatodos.model.Leccion;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador CRUD de Leccion, siempre en el contexto de un Curso.
 *
 * Rutas:
 *  GET  /lecciones?accion=nueva&cursoId=      -> formulario de nueva lección
 *  GET  /lecciones?accion=editar&id=          -> formulario de edición
 *  GET  /lecciones?accion=eliminar&id=&cursoId= -> elimina lección
 *  POST /lecciones                            -> guarda (crear o actualizar)
 */
@WebServlet("/lecciones")
public class LeccionServlet extends HttpServlet {

    private final LeccionDAO leccionDAO = new LeccionDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "nueva";

        switch (accion) {
            case "editar":
                Leccion leccion = leccionDAO.buscarPorId(Long.valueOf(req.getParameter("id")));
                mostrarFormulario(req, resp, leccion, leccion.getCurso().getId());
                break;
            case "eliminar":
                leccionDAO.eliminar(Long.valueOf(req.getParameter("id")));
                resp.sendRedirect("curso-detalle.jsp?id=" + req.getParameter("cursoId"));
                break;
            default: // "nueva"
                Long cursoId = Long.valueOf(req.getParameter("cursoId"));
                mostrarFormulario(req, resp, null, cursoId);
        }
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp, Leccion leccion, Long cursoId)
            throws ServletException, IOException {
        req.setAttribute("leccion", leccion);
        req.setAttribute("cursoId", cursoId);
        RequestDispatcher rd = req.getRequestDispatcher("leccion-form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Long cursoId = Long.valueOf(req.getParameter("cursoId"));

        Leccion leccion = (idParam == null || idParam.isBlank())
                ? new Leccion()
                : leccionDAO.buscarPorId(Long.valueOf(idParam));

        leccion.setTitulo(req.getParameter("titulo"));
        leccion.setContenido(req.getParameter("contenido"));
        leccion.setOrden(Integer.valueOf(req.getParameter("orden")));

        if (leccion.getCurso() == null) {
            Curso curso = cursoDAO.buscarPorId(cursoId);
            leccion.setCurso(curso);
        }

        if (leccion.getId() == null) {
            leccionDAO.crear(leccion);
        } else {
            leccionDAO.actualizar(leccion);
        }
        resp.sendRedirect("curso-detalle.jsp?id=" + cursoId);
    }
}

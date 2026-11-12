/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.time.Year;
import java.util.List;

public class ServicioLibros {

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void registrar(Libro libro) {
        validarLibro(libro);

        if (libroDAO.buscarPorIsbn(libro.getIsbn()) != null) {
            throw new IllegalArgumentException("El ISBN ya está registrado.");
        }

        if (autorDAO.buscarPorId(libro.getAutorId()) == null) {
            throw new IllegalArgumentException("El autor no existe.");
        }

        libroDAO.insertar(libro);
    }

    public Libro buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }

        return libroDAO.buscarPorId(id);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorDAO.buscarPorId(autorId) == null) {
            throw new IllegalArgumentException("El autor no existe.");
        }

        return libroDAO.listarPorAutor(autorId);
    }

    public void actualizar(Libro libro) {
        validarLibro(libro);

        Libro existente = libroDAO.buscarPorIsbn(libro.getIsbn());

        if (existente != null && existente.getId() != libro.getId()) {
            throw new IllegalArgumentException("El ISBN ya pertenece a otro libro.");
        }

        if (autorDAO.buscarPorId(libro.getAutorId()) == null) {
            throw new IllegalArgumentException("El autor no existe.");
        }

        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }

        libroDAO.eliminar(id);
    }

    private void validarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro es obligatorio.");
        }

        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es obligatorio.");
        }

        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }

        if (libro.getAnio() < 1450 || libro.getAnio() > Year.now().getValue()) {
            throw new IllegalArgumentException("El año del libro no es válido.");
        }

        if (libro.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }
}

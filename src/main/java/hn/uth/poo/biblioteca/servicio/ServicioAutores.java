/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;

    public ServicioAutores(AutorDAO autorDAO) {
        this.autorDAO = autorDAO;
    }

    public void registrar(Autor autor) {
        if (autor == null) {
            throw new IllegalArgumentException("El autor es obligatorio.");
        }

        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor es obligatorio.");
        }

        autorDAO.insertar(autor);
    }

    public Autor buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }

        return autorDAO.buscarPorId(id);
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public void actualizar(Autor autor) {
        if (autor == null || autor.getId() <= 0) {
            throw new IllegalArgumentException("Debe indicar un autor válido.");
        }

        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor es obligatorio.");
        }

        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }

        autorDAO.eliminar(id);
    }
}


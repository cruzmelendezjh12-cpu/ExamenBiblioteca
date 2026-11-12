/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.ConexionBD;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void insertar(Libro libro) {

        String sql = "INSERT INTO libros "
                + "(isbn, titulo, anio, precio, autor_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar libro", e);
        }
    }

    @Override
    public Libro buscarPorId(int id) {

        String sql = "SELECT * FROM libros WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al buscar libro", e);
        }

        return null;
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {

        String sql = "SELECT * FROM libros WHERE isbn = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al buscar libro por ISBN", e);
        }

        return null;
    }

    @Override
    public List<Libro> listar() {

        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT * FROM libros";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                libros.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new DatosException("Error al listar libros", e);
        }

        return libros;
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {

        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT * FROM libros WHERE autor_id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al listar libros del autor", e);
        }

        return libros;
    }

    @Override
    public void actualizar(Libro libro) {

        String sql = "UPDATE libros SET isbn = ?, titulo = ?, "
                + "anio = ?, precio = ?, autor_id = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());
            ps.setInt(6, libro.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar libro", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar libro", e);
        }
    }

    private Libro mapear(ResultSet rs) throws SQLException {

        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                rs.getInt("autor_id")
        );
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.ConexionBD;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public void insertar(Autor autor) {

        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor", e);
        }
    }

    @Override
    public Autor buscarPorId(int id) {

        String sql = "SELECT * FROM autores WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor", e);
        }

        return null;
    }

    @Override
    public List<Autor> listar() {

        List<Autor> autores = new ArrayList<>();

        String sql = "SELECT * FROM autores";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                autores.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new DatosException("Error al listar autores", e);
        }

        return autores;
    }

    @Override
    public void actualizar(Autor autor) {

        String sql = "UPDATE autores SET nombre = ?, nacionalidad = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM autores WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException(
                    "No se pudo eliminar el autor. Puede tener libros asociados.", e);
        }
    }

    private Autor mapear(ResultSet rs) throws SQLException {

        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad")
        );
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection obtenerConexion() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            // Activar llaves foráneas en SQLite
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            return conn;
        } catch (SQLException e) {
            throw new DatosException("Error al conectar con la base de datos SQLite", e);
        }
    }
}

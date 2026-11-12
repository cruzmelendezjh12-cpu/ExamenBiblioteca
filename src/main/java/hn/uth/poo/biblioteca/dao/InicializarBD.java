/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializarBD {

    public static void inicializar() {

        String tablaAutores = """
            CREATE TABLE IF NOT EXISTS autores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                nacionalidad TEXT
            )
        """;

        String tablaLibros = """
            CREATE TABLE IF NOT EXISTS libros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                isbn TEXT NOT NULL UNIQUE,
                titulo TEXT NOT NULL,
                anio INTEGER,
                precio REAL NOT NULL,
                autor_id INTEGER NOT NULL,
                FOREIGN KEY (autor_id) REFERENCES autores(id)
            )
        """;

        try (Connection con = ConexionBD.obtenerConexion();
             Statement st = con.createStatement()) {

            st.execute(tablaAutores);
            st.execute(tablaLibros);

            System.out.println("Base de datos y tablas listas.");

        } catch (SQLException e) {
            throw new DatosException("Error al crear las tablas", e);
        }
    }
}


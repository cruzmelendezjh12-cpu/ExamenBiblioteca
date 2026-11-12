/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.uth.examenbiblioteca;

import hn.uth.poo.biblioteca.dao.InicializarBD;
import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;
import java.util.Scanner;

public class ExamenBiblioteca {

    private static final Scanner sc = new Scanner(System.in);

    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {

        try {
            InicializarBD.inicializar();

            AutorDAO autorDAO = new AutorDAOImpl();
            LibroDAO libroDAO = new LibroDAOImpl();

            servicioAutores = new ServicioAutores(autorDAO);
            servicioLibros = new ServicioLibros(libroDAO, autorDAO);

            int opcion;

            do {
                System.out.println("\n================================");
                System.out.println("       SISTEMA DE BIBLIOTECA");
                System.out.println("================================");
                System.out.println("1. Gestionar autores");
                System.out.println("2. Gestionar libros");
                System.out.println("0. Salir");

                opcion = leerEntero("Seleccione una opción: ");

                switch (opcion) {
                    case 1:
                        menuAutores();
                        break;
                    case 2:
                        menuLibros();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void menuAutores() {

        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE AUTORES ==========");
            System.out.println("1. Registrar autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Buscar autor");
            System.out.println("4. Actualizar autor");
            System.out.println("5. Eliminar autor");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarAutor();
                        break;
                    case 2:
                        listarAutores();
                        break;
                    case 3:
                        buscarAutor();
                        break;
                    case 4:
                        actualizarAutor();
                        break;
                    case 5:
                        eliminarAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void registrarAutor() {
        System.out.println("\n--- REGISTRAR AUTOR ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nacionalidad: ");
        String nacionalidad = sc.nextLine();

        Autor autor = new Autor(nombre, nacionalidad);
        servicioAutores.registrar(autor);

        System.out.println("Autor registrado correctamente.");
        System.out.println("ID asignado: " + autor.getId());
    }

    private static void listarAutores() {
        System.out.println("\n--- LISTA DE AUTORES ---");

        for (Autor autor : servicioAutores.listar()) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor() {
        int id = leerEntero("Ingrese el ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(id);

        if (autor != null) {
            System.out.println(autor);
        } else {
            System.out.println("No se encontró el autor.");
        }
    }

    private static void actualizarAutor() {
        int id = leerEntero("Ingrese el ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(id);

        if (autor == null) {
            System.out.println("No se encontró el autor.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        autor.setNombre(sc.nextLine());

        System.out.print("Nueva nacionalidad: ");
        autor.setNacionalidad(sc.nextLine());

        servicioAutores.actualizar(autor);

        System.out.println("Autor actualizado correctamente.");
    }

    private static void eliminarAutor() {
        int id = leerEntero("Ingrese el ID del autor: ");

        servicioAutores.eliminar(id);

        System.out.println("Autor eliminado correctamente.");
    }

    private static void menuLibros() {

        int opcion;

        do {
            System.out.println("\n========== GESTIÓN DE LIBROS ==========");
            System.out.println("1. Registrar libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Buscar libro");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Eliminar libro");
            System.out.println("6. Listar libros por autor");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        buscarLibro();
                        break;
                    case 4:
                        actualizarLibro();
                        break;
                    case 5:
                        eliminarLibro();
                        break;
                    case 6:
                        listarLibrosPorAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");

        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        System.out.print("Título: ");
        String titulo = sc.nextLine();

        int anio = leerEntero("Año de publicación: ");
        double precio = leerDouble("Precio: ");
        int autorId = leerEntero("ID del autor: ");

        Libro libro = new Libro(isbn, titulo, anio, precio, autorId);

        servicioLibros.registrar(libro);

        System.out.println("Libro registrado correctamente.");
        System.out.println("ID asignado: " + libro.getId());
    }

    private static void listarLibros() {
        System.out.println("\n--- LISTA DE LIBROS ---");

        for (Libro libro : servicioLibros.listar()) {
            System.out.println(libro);
        }
    }

    private static void buscarLibro() {
        int id = leerEntero("Ingrese el ID del libro: ");

        Libro libro = servicioLibros.buscarPorId(id);

        if (libro != null) {
            System.out.println(libro);
        } else {
            System.out.println("No se encontró el libro.");
        }
    }

    private static void actualizarLibro() {
        int id = leerEntero("Ingrese el ID del libro: ");

        Libro libro = servicioLibros.buscarPorId(id);

        if (libro == null) {
            System.out.println("No se encontró el libro.");
            return;
        }

        System.out.print("Nuevo ISBN: ");
        libro.setIsbn(sc.nextLine());

        System.out.print("Nuevo título: ");
        libro.setTitulo(sc.nextLine());

        libro.setAnio(leerEntero("Nuevo año: "));
        libro.setPrecio(leerDouble("Nuevo precio: "));
        libro.setAutorId(leerEntero("Nuevo ID del autor: "));

        servicioLibros.actualizar(libro);

        System.out.println("Libro actualizado correctamente.");
    }

    private static void eliminarLibro() {
        int id = leerEntero("Ingrese el ID del libro: ");

        servicioLibros.eliminar(id);

        System.out.println("Libro eliminado correctamente.");
    }

    private static void listarLibrosPorAutor() {
        int autorId = leerEntero("Ingrese el ID del autor: ");

        for (Libro libro : servicioLibros.listarPorAutor(autorId)) {
            System.out.println(libro);
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un precio válido.");
            }
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.excepciones;

public class DatosException extends RuntimeException {
    public DatosException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatosException(String message) {
        super(message);
    }
}
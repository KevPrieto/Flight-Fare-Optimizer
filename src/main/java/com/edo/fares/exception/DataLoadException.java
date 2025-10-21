package com.edo.fares.exception;

/**
 * Excepción personalizada para errores relacionados con la carga de datos.
 * 
 * Se utiliza cuando ocurre un problema al leer, parsear o acceder
 * a los datos de vuelos (ya sea desde un archivo local o una fuente remota).
 */
public class DataLoadException extends Exception {

    /**
     * Crea una nueva excepción con un mensaje descriptivo.
     *
     * @param message Descripción del error ocurrido.
     */
    public DataLoadException(String message) {
        super(message);
    }

    /**
     * Crea una nueva excepción con un mensaje y una causa original.
     *
     * @param message Descripción del error.
     * @param cause   Excepción original que causó el error.
     */
    public DataLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.example.dao;

/**
 * Исключение, возникающее при работе с DAO
 */
public class DAOException extends RuntimeException {
    
    /**
     * Создает новый экземпляр исключения с указанным сообщением
     * 
     * @param message сообщение об ошибке
     */
    public DAOException(String message) {
        super(message);
    }
    
    /**
     * Создает новый экземпляр исключения с указанным сообщением и причиной
     * 
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Создает новый экземпляр исключения с указанной причиной
     * 
     * @param cause причина исключения
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
} 
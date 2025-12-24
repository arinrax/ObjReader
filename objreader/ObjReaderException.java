package com.cgvsu.objreader;

public class ObjReaderException extends RuntimeException {
    public ObjReaderException(String errorMessage, int lineInd) {
        super("Ошибка парсинга obj в строке: " + lineInd + ". " + errorMessage);
    }
}

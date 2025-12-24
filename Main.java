package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileObj = "WrapBody";
        Path dataDir = Path.of("").toAbsolutePath().resolve("data");
        Path fileName = dataDir.resolve(fileObj + ".obj");

        String fileContent = Files.readString(fileName);

        System.out.println("Модель загружается...");
        Model model = ObjReader.read(fileContent);

        System.out.println("Модель загружена.");
        System.out.println("Вершин: " + model.getVertices().size());
        System.out.println("Текстурных вершин: " + model.getTextureVertices().size());
        System.out.println("Нормалей: " + model.getNormals().size());
        System.out.println("Полигонов: " + model.getPolygons().size());
    }
}
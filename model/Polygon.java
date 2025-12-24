package com.cgvsu.model;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Integer> vertexIndices;
    private ArrayList<Integer> textureVertexIndices;
    private ArrayList<Integer> normalIndices;


    public Polygon() {
        vertexIndices = new ArrayList<Integer>();
        textureVertexIndices = new ArrayList<Integer>();
        normalIndices = new ArrayList<Integer>();
    }

    public void setVertexIndices(ArrayList<Integer> vertexIndices) {
        if (vertexIndices == null) {
            throw new IllegalArgumentException("Индексы вершин не должны быть нулевыми");
        }
        if (vertexIndices.size() < 3) {
            throw new IllegalArgumentException("Полигон должен иметь как минимум 3 вершины, а получено: " + vertexIndices.size());
        }
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(ArrayList<Integer> textureVertexIndices) {
        if (textureVertexIndices != null && textureVertexIndices.size() < 3) {
            throw new IllegalArgumentException("Должно быть не менее 3 текстурных вершин, а получено: " + textureVertexIndices.size());
        }
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(ArrayList<Integer> normalIndices) {
        if (normalIndices != null && normalIndices.size() < 3) {
            throw new IllegalArgumentException("Должно быть не менее 3х индексов нормалей, а получено: " + normalIndices.size());
        }
        this.normalIndices = normalIndices;
    }

    public ArrayList<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public ArrayList<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public ArrayList<Integer> getNormalIndices() {
        return normalIndices;
    }
}
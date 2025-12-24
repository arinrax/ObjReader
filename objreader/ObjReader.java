package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;

public class ObjReader {

    public static Model read(String fileContent) {
        Model model = new Model();
        int lineNum = 0;

        try (Scanner scanner = new Scanner(fileContent)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] rawTokens = line.split("\\s+");
                List<String> tokens = new ArrayList<>();
                for (String t : rawTokens) {
                    if (!t.isEmpty()) tokens.add(t);
                }

                if (tokens.isEmpty()) continue;

                String command = tokens.remove(0);
                lineNum++;

                switch (command) {
                    case "v" -> model.getVertices().add(parseVector3(tokens, lineNum));
                    case "vt" -> model.getTextureVertices().add(parseVector2(tokens, lineNum));
                    case "vn" -> model.getNormals().add(parseVector3(tokens, lineNum));
                    case "f" -> model.getPolygons().add(parseFace(tokens, model, lineNum));
                }
            }
        }

        return model;
    }

    private static Vector3f parseVector3(List<String> tokens, int lineNum) {
        return new Vector3f(
                parseFloat(tokens, 0, lineNum),
                parseFloat(tokens, 1, lineNum),
                parseFloat(tokens, 2, lineNum)
        );
    }

    private static Vector2f parseVector2(List<String> tokens, int lineNum) {
        return new Vector2f(
                parseFloat(tokens, 0, lineNum),
                parseFloat(tokens, 1, lineNum)
        );
    }

    private static float parseFloat(List<String> tokens, int index, int lineNum) {
        try {
            return Float.parseFloat(tokens.get(index));
        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Слишком мало аргументов", lineNum);
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Неверное число: '" + tokens.get(index) + "'", lineNum);
        }
    }

    private static Polygon parseFace(List<String> faceTokens, Model model, int lineNum) {
        ArrayList<Integer> vIdx = new ArrayList<>();
        ArrayList<Integer> vtIdx = new ArrayList<>();
        ArrayList<Integer> vnIdx = new ArrayList<>();

        for (String token : faceTokens) {
            parseFaceToken(token, vIdx, vtIdx, vnIdx, lineNum);
        }

        if (vIdx.size() < 3) {
            throw new ObjReaderException("Полигон должен иметь ≥3 вершин", lineNum);
        }

        checkBounds(vIdx, model.getVertices().size(), "вершины", lineNum);
        if (!vtIdx.isEmpty()) checkBounds(vtIdx, model.getTextureVertices().size(), "текстурные координаты", lineNum);
        if (!vnIdx.isEmpty()) checkBounds(vnIdx, model.getNormals().size(), "нормали", lineNum);

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(vIdx);
        polygon.setTextureVertexIndices(vtIdx.isEmpty() ? null : vtIdx);
        polygon.setNormalIndices(vnIdx.isEmpty() ? null : vnIdx);
        return polygon;
    }

    private static void parseFaceToken(String token, List<Integer> v, List<Integer> vt, List<Integer> vn, int lineNum) {
        if (token == null || token.trim().isEmpty()) {
            return;
        }
        String[] parts = token.split("/");
        int vertex = parsePositiveIndex(parts[0], "вершины", lineNum) - 1;
        v.add(vertex);

        if (parts.length >= 2 && !parts[1].isEmpty()) {
            int tex = parsePositiveIndex(parts[1], "текстурной координаты", lineNum) - 1;
            vt.add(tex);
        }

        if (parts.length >= 3 && !parts[2].isEmpty()) {
            int normal = parsePositiveIndex(parts[2], "нормали", lineNum) - 1;
            vn.add(normal);
        }
    }

    private static int parsePositiveIndex(String str, String type, int lineNum) {
        try {
            int idx = Integer.parseInt(str);
            if (idx <= 0) {
                throw new ObjReaderException("Индекс " + type + " должен быть ≥1, получено: " + idx, lineNum);
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Невозможно прочитать индекс " + type + ": '" + str + "'", lineNum);
        }
    }

    private static void checkBounds(List<Integer> indices, int size, String type, int lineNum) {
        for (int i = 0; i < indices.size(); i++) {
            int idx = indices.get(i);
            if (idx >= size) {
                throw new ObjReaderException(
                        "Индекс " + type + " #" + (i + 1) + " = " + (idx + 1) +
                                " выходит за границы (всего: " + size + ")", lineNum);
            }
        }
    }
}
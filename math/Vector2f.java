package com.cgvsu.math;

import java.util.Objects;

public final class Vector2f {
    private static final float EPSILON = 1e-6f;

    public final float x;
    public final float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //арифметика
    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    public Vector2f subtract(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    public Vector2f scale(float s) {
        return new Vector2f(x * s, y * s);
    }

    //скалярное произведение
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        float len = length();
        if (len < EPSILON) return new Vector2f(0, 0);  //нулевой вектор остается нулевым
        return new Vector2f(x / len, y / len);
    }

    //расстояние до другой точки
    public float distanceTo(Vector2f v) {
        return subtract(v).length();
    }

    //расстояние до отрезк
    public float distanceToSegment(Vector2f a, Vector2f b) {
        Vector2f ab = b.subtract(a);
        Vector2f ap = subtract(a);
        float t = ap.dot(ab) / ab.lengthSquared();  //проекция
        t = Math.max(0, Math.min(1, t));            //clamp к [0, 1]
        Vector2f closest = a.add(ab.scale(t));
        return distanceTo(closest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f)) return false;
        Vector2f v = (Vector2f) o;
        return Math.abs(x - v.x) < EPSILON && Math.abs(y - v.y) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Vector2f(%.3f, %.3f)", x, y);
    }
}
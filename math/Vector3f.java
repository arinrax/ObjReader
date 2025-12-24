package com.cgvsu.math;

import java.util.Objects;

public final class Vector3f {

    private static final float EPSILON = 1e-6f;

    public final float x;
    public final float y;
    public final float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //арифметика

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x, y + v.y, z + v.z);
    }

    public Vector3f subtract(Vector3f v) {
        return new Vector3f(x - v.x, y - v.y, z - v.z);
    }

    public Vector3f scale(float s) {
        return new Vector3f(x * s, y * s, z * s);
    }

    //покомпонентное умножение
    public Vector3f multiply(Vector3f v) {
        return new Vector3f(x * v.x, y * v.y, z * v.z);
    }

    //скалярные и векторные операции

    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;  // a · b
    }

    //векторное произведение
    public Vector3f cross(Vector3f v) {
        return new Vector3f(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    //длина и нормализация
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector3f normalize() {
        float len = length();
        if (len < EPSILON) return new Vector3f(0, 0, 0);
        return new Vector3f(x / len, y / len, z / len);
    }

    //геометрия
    public float distanceTo(Vector3f v) {
        return subtract(v).length();
    }

    //угол между векторами
    public float angleTo(Vector3f v) {
        float dot = this.dot(v);
        float lenProduct = this.length() * v.length();
        if (lenProduct < EPSILON) return 0.0f;  //один из векторов нулевой
        float cos = dot / lenProduct;
        //ограничение косинуса из-за погрешности флоат
        cos = Math.max(-1.0f, Math.min(1.0f, cos));
        return (float) Math.acos(cos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3f)) return false;
        Vector3f v = (Vector3f) o;
        return Math.abs(x - v.x) < EPSILON &&
                Math.abs(y - v.y) < EPSILON &&
                Math.abs(z - v.z) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Vector3f(%.3f, %.3f, %.3f)", x, y, z);
    }
}
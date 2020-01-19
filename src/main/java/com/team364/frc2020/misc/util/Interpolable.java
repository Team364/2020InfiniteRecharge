package com.team364.frc2020.misc.util;

public interface Interpolable<T> {
    T interpolate(T other, double t);
}

package com.team364.frc2020.misc.util;

public interface InverseInterpolable<T> {
    double inverseInterpolate(T upper, T query);
}

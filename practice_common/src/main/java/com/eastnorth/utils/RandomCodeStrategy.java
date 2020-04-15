package com.eastnorth.utils;

public interface RandomCodeStrategy {
    void init();

    int prefix();

    int next();

    void release();
}

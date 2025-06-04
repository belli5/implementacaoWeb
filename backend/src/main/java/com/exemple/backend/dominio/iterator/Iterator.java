package com.exemple.backend.dominio.iterator;

public interface Iterator<T> {
    boolean hasNext();
    T next();
}
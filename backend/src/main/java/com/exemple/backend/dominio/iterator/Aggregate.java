package com.exemple.backend.dominio.iterator;

public interface Aggregate<T> {
    Iterator<T> createIterator();
}
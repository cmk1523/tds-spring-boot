package com.techdevsolutions.springBoot.service;

import com.techdevsolutions.springBoot.beans.Search;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface CrudServiceInterface<T> {
    List<T> search(final Search search) throws IllegalArgumentException;
    List<T> getAll(final Search search) throws IllegalArgumentException;
    Optional<T> get(final String id) throws IllegalArgumentException;
    T create(final String id, final T item) throws IllegalArgumentException, NoSuchElementException;
    void remove(final String id) throws IllegalArgumentException, NoSuchElementException;
    void delete(final String id) throws IllegalArgumentException, NoSuchElementException;
    T update(final String id, final T item) throws IllegalArgumentException, NoSuchElementException;
    T upsert(final String id, final T item) throws IllegalArgumentException, NoSuchElementException;
    void install();
}

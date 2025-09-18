package com.apress.my.retro.persistence;

import java.util.Optional;
@SuppressWarnings(value = "unused")
public interface Repository<D, ID> {
    D save(D domain);
    Optional<D> findById(ID id);
    Iterable<D> findAll();
    void delete(ID id);
}

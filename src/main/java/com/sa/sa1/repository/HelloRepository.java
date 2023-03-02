package com.sa.sa1.repository;

import com.sa.sa1.data.Hello;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HelloRepository extends CrudRepository<Hello, Long> {
    Iterable<Hello> findByName(String name);
}

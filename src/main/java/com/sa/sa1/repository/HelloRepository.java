package com.sa.sa1.repository;

import com.sa.sa1.data.Hello;
import com.sa.sa1.data.HelloDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface HelloRepository extends JpaRepository<Hello, Long> {
    Page<Hello> findByName(String name, Pageable pageable);
    Page<Hello> findAll(Pageable pageable);
}

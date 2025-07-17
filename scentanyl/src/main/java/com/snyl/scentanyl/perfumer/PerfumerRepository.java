package com.snyl.scentanyl.perfumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfumerRepository extends JpaRepository<Perfumer, Long> {

    Optional<Perfumer> findByNameIgnoreCase(String name);


}

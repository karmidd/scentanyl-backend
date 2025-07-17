package com.snyl.scentanyl.accord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccordRepository extends JpaRepository<Accord, Long> {

    Optional<Accord> findByNameIgnoreCase(String name);


}

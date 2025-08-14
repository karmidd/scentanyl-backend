package com.snyl.scentanyl.accord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccordRepository extends JpaRepository<Accord, Long> {
}

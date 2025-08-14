package com.snyl.scentanyl.perfumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfumerRepository extends JpaRepository<Perfumer, Long> {
}

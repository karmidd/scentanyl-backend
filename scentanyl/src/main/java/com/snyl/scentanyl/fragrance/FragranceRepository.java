package com.snyl.scentanyl.fragrance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, String> {
    void deleteByUrl(String url);
    Optional<Fragrance> findByUrl(String url);
}

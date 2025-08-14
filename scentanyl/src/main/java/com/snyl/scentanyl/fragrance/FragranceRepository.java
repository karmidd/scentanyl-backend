package com.snyl.scentanyl.fragrance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, Long> {
    Optional<Fragrance> findByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);

    Optional<Fragrance> findByBrandIgnoreCaseAndNameIgnoreCaseAndId(String brand, String name, Long id);

    List<Fragrance> findAllByBrandIgnoreCase(String brand);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.topNotes) LIKE %:note%
""")
    List<Fragrance> findByTopNotesContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.middleNotes) LIKE %:note%
""")
    List<Fragrance> findByMiddleNotesContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.baseNotes) LIKE %:note%
""")
    List<Fragrance> findByBaseNotesContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.accords) LIKE %:accord%
""")
    List<Fragrance> findByAccordsContaining(@Param("accord") String accord);

    @Query(value = "SELECT * FROM fragrances_table ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Fragrance> getRandomFragrance();

    @Query(value = "SELECT * FROM fragrances_table ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Fragrance> getRandomFragrances(@Param("count") int count);

}

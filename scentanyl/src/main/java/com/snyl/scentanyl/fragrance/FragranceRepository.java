package com.snyl.scentanyl.fragrance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, Long> {
    Optional<Fragrance> findByName(String name);

    Optional<Fragrance> findByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);

    List<Fragrance> findAllByBrandIgnoreCase(String brand);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE
        LOWER(f.topNotes) LIKE %:note%
        OR LOWER(f.middleNotes) LIKE %:note%
        OR LOWER(f.baseNotes) LIKE %:note%
        OR LOWER(f.uncategorizedNotes) LIKE %:note%
""")
    List<Fragrance> findByAnyNotesContaining(@Param("note") String note);

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

    @Query("SELECT DISTINCT f.brand FROM Fragrance f WHERE f.brand IS NOT NULL")
    List<String> findAllDistinctBrands();

    @Query("SELECT DISTINCT f.perfumerNames FROM Fragrance f WHERE f.perfumerNames IS NOT NULL")
    List<String> findAllDistinctPerfumers();

    @Query("SELECT DISTINCT f.accords FROM Fragrance f WHERE f.accords IS NOT NULL")
    List<String> findAllDistinctAccords();

    @Query("SELECT DISTINCT f.topNotes FROM Fragrance f WHERE f.topNotes IS NOT NULL")
    List<String> findAllDistinctTopNotes();

    @Query("SELECT DISTINCT f.middleNotes FROM Fragrance f WHERE f.middleNotes IS NOT NULL")
    List<String> findAllDistinctMiddleNotes();

    @Query("SELECT DISTINCT f.baseNotes FROM Fragrance f WHERE f.baseNotes IS NOT NULL")
    List<String> findAllDistinctBaseNotes();

    @Query("SELECT DISTINCT f.uncategorizedNotes FROM Fragrance f WHERE f.uncategorizedNotes IS NOT NULL")
    List<String> findAllDistinctUncategorizedNotes();

    @Query(value = "SELECT * FROM fragrances ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Fragrance> getRandomFragrance();

}

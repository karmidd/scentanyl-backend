package com.snyl.scentanyl.fragrance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, Long>, JpaSpecificationExecutor<Fragrance> {
    Optional<Fragrance> findByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);

    Optional<Fragrance> findByBrandIgnoreCaseAndNameIgnoreCaseAndId(String brand, String name, Long id);

    List<Fragrance> findAllByBrandIgnoreCase(String brand);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.topNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    List<Fragrance> findByTopNotesContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.middleNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    List<Fragrance> findByMiddleNotesContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.baseNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    List<Fragrance> findByBaseNotesContaining(@Param("note") String note);

    @Query("""
    SELECT COUNT(f) FROM Fragrance f
    WHERE f.topNotes IS NOT NULL 
    AND LOWER(f.topNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    Long countByTopNoteContaining(@Param("note") String note);

    @Query("""
    SELECT COUNT(f) FROM Fragrance f
    WHERE f.middleNotes IS NOT NULL 
    AND LOWER(f.middleNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    Long countByMiddleNoteContaining(@Param("note") String note);

    @Query("""
    SELECT COUNT(f) FROM Fragrance f
    WHERE f.baseNotes IS NOT NULL 
    AND LOWER(f.baseNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    Long countByBaseNoteContaining(@Param("note") String note);

    @Query("""
    SELECT COUNT(f) FROM Fragrance f
    WHERE f.uncategorizedNotes IS NOT NULL 
    AND LOWER(f.uncategorizedNotes) LIKE LOWER(CONCAT('%', :note, '%'))
""")
    Long countByUncategorizedNoteContaining(@Param("note") String note);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE LOWER(f.accords) LIKE LOWER(CONCAT('%', :accord, '%'))
""")
    List<Fragrance> findByAccordsContaining(@Param("accord") String accord);

    @Query(value = "SELECT * FROM fragrances_table ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Fragrance> getRandomFragrance();

    @Query(value = "SELECT * FROM fragrances_table ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Fragrance> getRandomFragrances(@Param("count") int count);

    @Query("SELECT MIN(f.year) FROM Fragrance f WHERE f.year IS NOT NULL")
    Long findMinYear();

    @Query("SELECT MAX(f.year) FROM Fragrance f WHERE f.year IS NOT NULL")
    Long findMaxYear();

    @Query("""
    SELECT f FROM Fragrance f
    WHERE f.perfumerNames IS NOT NULL
      AND f.perfumerNames <> 'N/A'
      AND LOWER(f.perfumerNames) LIKE LOWER(CONCAT('%', :perfumer, '%'))
""")
    List<Fragrance> findByPerfumer(@Param("perfumer") String perfumer);

    @Query("""
    SELECT f FROM Fragrance f
    WHERE (f.uncategorizedNotes IS NOT NULL AND LOWER(f.uncategorizedNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.topNotes IS NOT NULL AND LOWER(f.topNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.middleNotes IS NOT NULL AND LOWER(f.middleNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.baseNotes IS NOT NULL AND LOWER(f.baseNotes) LIKE LOWER(CONCAT('%', :note, '%')))
""")
    List<Fragrance> findByAnyNoteContaining(@Param("note") String note);

    Long countByGenderIgnoreCase(String gender);

    @Query("SELECT MIN(f.year) FROM Fragrance f WHERE LOWER(f.accords) LIKE LOWER(CONCAT('%', :accord, '%'))")
    Integer findMinYearByAccord(@Param("accord") String accord);

    @Query("SELECT MAX(f.year) FROM Fragrance f WHERE LOWER(f.accords) LIKE LOWER(CONCAT('%', :accord, '%'))")
    Integer findMaxYearByAccord(@Param("accord") String accord);

    @Query("""
    SELECT MIN(f.year) FROM Fragrance f 
    WHERE (f.uncategorizedNotes IS NOT NULL AND LOWER(f.uncategorizedNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.topNotes IS NOT NULL AND LOWER(f.topNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.middleNotes IS NOT NULL AND LOWER(f.middleNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.baseNotes IS NOT NULL AND LOWER(f.baseNotes) LIKE LOWER(CONCAT('%', :note, '%')))
""")
    Integer findMinYearByNote(@Param("note") String note);

    @Query("""
    SELECT MAX(f.year) FROM Fragrance f 
    WHERE (f.uncategorizedNotes IS NOT NULL AND LOWER(f.uncategorizedNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.topNotes IS NOT NULL AND LOWER(f.topNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.middleNotes IS NOT NULL AND LOWER(f.middleNotes) LIKE LOWER(CONCAT('%', :note, '%')))
       OR (f.baseNotes IS NOT NULL AND LOWER(f.baseNotes) LIKE LOWER(CONCAT('%', :note, '%')))
""")
    Integer findMaxYearByNote(@Param("note") String note);

    @Query("SELECT COUNT(f) FROM Fragrance f WHERE LOWER(f.accords) LIKE LOWER(CONCAT('%', :accord, '%')) AND LOWER(f.gender) = LOWER(:gender)")
    Long countByAccordAndGender(@Param("accord") String accord, @Param("gender") String gender);

    @Query("SELECT COUNT(f) FROM Fragrance f WHERE LOWER(f.accords) LIKE LOWER(CONCAT('%', :accord, '%'))")
    Long countByAccord(@Param("accord") String accord);
}

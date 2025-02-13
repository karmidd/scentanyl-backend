package com.snyl.scentanyl.fragrance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, String> {
    Optional<Fragrance> findByUrl(String url);
    Optional<Fragrance> findByFragranceName(String fragranceName);
    List<Fragrance> findAllByFragranceName(String fragranceName);
    List<Fragrance> findAllByBrand(String brand);
    List<Fragrance> findAllByGender(String gender);
    List<Fragrance> findAllByPerfumer1(String perfumer1);
    List<Fragrance> findAllByPerfumer2(String perfumer2);
    List<Fragrance> findAllByTopNotes(String topNotes);
    List<Fragrance> findAllByMiddleNotes(String middleNotes);
    List<Fragrance> findAllByBaseNotes(String baseNotes);
    List<Fragrance> findAllByMainaccord1(String mainaccord1);
    List<Fragrance> findAllByMainaccord2(String mainaccord2);
    List<Fragrance> findAllByMainaccord3(String mainaccord3);
    List<Fragrance> findAllByMainaccord4(String mainaccord4);
    List<Fragrance> findAllByMainaccord5(String mainaccord5);
    List<Fragrance> findAllByCountry(String country);
    List<Fragrance> findAllByYear(Integer year);
    @Query("SELECT DISTINCT f.brand FROM Fragrance f")
    List<String> findAllDistinctBrands();
}

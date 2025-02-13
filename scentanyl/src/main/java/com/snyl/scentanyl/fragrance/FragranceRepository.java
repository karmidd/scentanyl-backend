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
    @Query("SELECT DISTINCT f.brand FROM Fragrance f WHERE f.brand IS NOT NULL")
    List<String> findAllDistinctBrands();
    @Query("SELECT DISTINCT f.country FROM Fragrance f WHERE f.country IS NOT NULL")
    List<String> findAllDistinctCountries();
    @Query("SELECT DISTINCT f.perfumer1 FROM Fragrance f WHERE f.perfumer1 IS NOT NULL")
    List<String> findAllDistinctPerfumer1();
    @Query("SELECT DISTINCT f.perfumer2 FROM Fragrance f WHERE f.perfumer2 IS NOT NULL")
    List<String> findAllDistinctPerfumer2();
    @Query("SELECT DISTINCT f.topNotes FROM Fragrance f WHERE f.topNotes IS NOT NULL")
    List<String> findAllDistinctTopNotes();
    @Query("SELECT DISTINCT f.middleNotes FROM Fragrance f WHERE f.middleNotes IS NOT NULL")
    List<String> findAllDistinctMiddleNotes();
    @Query("SELECT DISTINCT f.baseNotes FROM Fragrance f WHERE f.baseNotes IS NOT NULL")
    List<String> findAllDistinctBaseNotes();
    @Query("SELECT DISTINCT f.mainaccord1 FROM Fragrance f WHERE f.mainaccord1 IS NOT NULL")
    List<String> findAllDistinctMainAccord1();
    @Query("SELECT DISTINCT f.mainaccord2 FROM Fragrance f WHERE f.mainaccord2 IS NOT NULL")
    List<String> findAllDistinctMainAccord2();
    @Query("SELECT DISTINCT f.mainaccord3 FROM Fragrance f WHERE f.mainaccord3 IS NOT NULL")
    List<String> findAllDistinctMainAccord3();
    @Query("SELECT DISTINCT f.mainaccord4 FROM Fragrance f WHERE f.mainaccord4 IS NOT NULL")
    List<String> findAllDistinctMainAccord4();
    @Query("SELECT DISTINCT f.mainaccord5 FROM Fragrance f WHERE f.mainaccord5 IS NOT NULL")
    List<String> findAllDistinctMainAccord5();
}

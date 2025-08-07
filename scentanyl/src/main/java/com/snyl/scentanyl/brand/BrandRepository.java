package com.snyl.scentanyl.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByNameIgnoreCase(String name);

    @Query(value = "SELECT * FROM brands_mat_view ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Brand> getRandomBrand();

    @Query(value = "SELECT * FROM brands_mat_view ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Brand> getRandomBrands(@Param("count") int count);

    List<Brand> findAllByCountry(String country);

    List<Brand> findAllByParent(String brandParent);

}

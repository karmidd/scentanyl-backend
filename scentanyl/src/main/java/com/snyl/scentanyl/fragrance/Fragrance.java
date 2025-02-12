package com.snyl.scentanyl.fragrance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="fragrances")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Fragrance {
    @Id
    @Column(name = "url", unique = true, nullable = false)
    private String url;                     // URL of the perfume (String)
    private String fragranceName;    // Perfume name
    private String brand;          // Brand name
    private String country;        // Country of origin
    private String gender;          // Gender classification (Male, Female, Unisex, etc.)
    private Double ratingValue;        // Rating value (numerical)
    private Integer ratingCount;        // Rating count (number of people who rated)
    private Integer year;                // Year of release
    private String topNotes;             // Top notes of the perfume (text)
    private String middleNotes;          // Middle notes of the perfume (text)
    private String baseNotes;             // Base notes of the perfume (text)
    private String perfumer1;      // Perfumer 1 (name of the perfumer)
    private String perfumer2;      // Perfumer 2 (name of the perfumer)
    private String mainaccord1;   // Main accord 1
    private String mainaccord2;    // Main accord 2
    private String mainaccord3;    // Main accord 3
    private String mainaccord4;     // Main accord 4
    private String mainaccord5;     // Main accord 5
}

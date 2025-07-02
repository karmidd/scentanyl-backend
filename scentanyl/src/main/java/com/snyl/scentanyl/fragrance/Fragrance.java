package com.snyl.scentanyl.fragrance;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private String url;                     // URL of the perfume (String)
    private Long id;
    private String fragranceName;    // Perfume name
    private String brand;          // Brand name
    //private String country;        // Country of origin
    //private String gender;          // Gender classification (Male, Female, Unisex, etc.)
    //private Double ratingValue;        // Rating value (numerical)
    //private Integer ratingCount;        // Rating count (number of people who rated)
    //private Integer year;                // Year of release
    private String perfumer;      // Perfumer (name of the perfumer)
    private String perfumerImgURL;      // Perfumer Image URL
    private String mainAccords;   // Main accords
    private String topNotes;             // Top notes of the perfume (text)
    private String middleNotes;          // Middle notes of the perfume (text)
    private String baseNotes;             // Base notes of the perfume (text)
    private String uncategorizedNotes; // Uncategorized notes of the perfume (text)
    private String fragranceImgURL;   // Fragrance Image URL
    //private String perfumer2;      // Perfumer 2 (name of the perfumer)

}

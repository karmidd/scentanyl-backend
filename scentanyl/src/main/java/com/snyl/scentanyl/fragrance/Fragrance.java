package com.snyl.scentanyl.fragrance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="fragrances_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Fragrance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String gender;

    @Column(name = "`year`")
    private Long year;

    @Column(name = "perfumer_name")
    private String perfumerNames;

    @JsonIgnore
    @Column(name = "perfumer_image")
    private String perfumerImages;

    private String accords;

    @Column(name = "top_notes")
    private String topNotes;

    @Column(name = "middle_notes")
    private String middleNotes;

    @Column(name = "base_notes")
    private String baseNotes;

    @Column(name = "uncategorized_notes")
    private String uncategorizedNotes;

    @Column(name = "image_url")
    private String imageUrl;

}

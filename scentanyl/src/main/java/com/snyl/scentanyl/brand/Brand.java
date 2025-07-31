package com.snyl.scentanyl.brand;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="brands")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String parent;

    private String img;

    private String url;

    @Column(name = "fragrance_count")
    private int fragranceCount;
}

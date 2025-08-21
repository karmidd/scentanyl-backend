package com.snyl.scentanyl.brand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="brands_mat_view")
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

    @JsonIgnore
    private String logo;

    private String url;

    @Column(name = "total_fragrances")
    private int totalFragrances;
}

package com.snyl.scentanyl.perfumer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="perfumers_mat_view")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Perfumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private String image;

    @Column(name = "total_fragrances")
    private int totalFragrances;
}

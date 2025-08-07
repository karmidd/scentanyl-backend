package com.snyl.scentanyl.accord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="accords_mat_view")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "total_appearances")
    private int totalAppearances;
}

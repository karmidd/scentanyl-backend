package com.snyl.scentanyl.perfumer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="perfumers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Perfumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    @Column(name = "total_contributions")
    private int totalContributions;
}

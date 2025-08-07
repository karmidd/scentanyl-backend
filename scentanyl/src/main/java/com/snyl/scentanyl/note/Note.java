package com.snyl.scentanyl.note;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="notes_mat_view")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "top_count")
    private int topCount;

    @Column(name = "middle_count")
    private int middleCount;

    @Column(name = "base_count")
    private int baseCount;

    @Column(name = "uncategorized_count")
    private int uncategorizedCount;

    @Column(name = "total_appearances")
    private int totalAppearances;
}

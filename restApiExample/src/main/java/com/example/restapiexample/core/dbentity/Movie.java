package com.example.restapiexample.core.dbentity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @Column
    private long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private double rating;
    @Column
    private String genre;

    }

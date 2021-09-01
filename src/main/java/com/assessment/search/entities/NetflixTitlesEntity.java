package com.assessment.search.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "netflix_titles_m")
@RequiredArgsConstructor
public class NetflixTitlesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id")
    private String showId;

    @Column
    private String type;

    @Column
    private String title;

    @Column
    private String director;

    @Column
    private String cast;

    @Column
    private String country;

    @Column(name = "date_added")
    private String dateAdded;

    @Column(name = "release_year")
    private int releaseYear;

    @Column
    private String rating;

    @Column
    private String duration;

    @Column(name = "listed_in")
    private String listedIn;

    @Column
    private String description;
}

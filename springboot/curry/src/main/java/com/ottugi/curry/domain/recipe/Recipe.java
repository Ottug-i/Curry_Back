package com.ottugi.curry.domain.recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @Column(name = "Recipe_Id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbnail;

    @Convert(converter = TimeConverter.class)
    @Column(nullable = false)
    private Time time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Composition composition;

    @Column(length = 1000, nullable = false)
    private String ingredients;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Servings servings;

    @Column(length = 10000, nullable = false)
    private String orders;

    @Column(length = 10000)
    private String photo;

    @Builder
    public Recipe(Long id, String name, String thumbnail, Time time, Difficulty difficulty, Composition composition, String ingredients, Servings servings, String orders, String photo) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.time = time;
        this.difficulty = difficulty;
        this.composition = composition;
        this.ingredients = ingredients;
        this.servings = servings;
        this.orders = orders;
        this.photo = photo;
    }
}

package com.ottugi.curry.domain.recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    @Column(length = 100, nullable = false)
    private String time;

    @Column(length = 100, nullable = false)
    private String difficulty;

    @Column(length = 100, nullable = false)
    private String composition;

    @Column(length = 1000, nullable = false)
    private String ingredients;

    @Column(nullable = false)
    private String servings;

    @Column(length = 10000, nullable = false)
    private String orders;

    @Column(length = 10000)
    private String photo;

    @Builder
    public Recipe(Long id, String name, String thumbnail, String time, String difficulty, String composition, String ingredients, String servings, String orders, String photo) {
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

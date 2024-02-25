package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.domain.BaseTime;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe extends BaseTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Recipe_Id", nullable = false, unique = true)
    private Long recipeId;

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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String ingredients;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Servings servings;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String orders;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @Column(columnDefinition = "TEXT")
    private String genre;

    @Builder
    public Recipe(Long id, Long recipeId, String name, String thumbnail,
                  Time time, Difficulty difficulty, Composition composition,
                  String ingredients, Servings servings, String orders, String photo, String genre) {
        this.id = id;
        this.recipeId = recipeId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.time = time;
        this.difficulty = difficulty;
        this.composition = composition;
        this.ingredients = ingredients;
        this.servings = servings;
        this.orders = orders;
        this.photo = photo;
        this.genre = genre;
    }
}

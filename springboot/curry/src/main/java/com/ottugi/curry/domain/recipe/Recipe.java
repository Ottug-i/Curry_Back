package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.domain.BaseTime;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.lately.Lately;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition="TEXT", nullable = false)
    private String ingredients;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Servings servings;

    @Column(columnDefinition="TEXT", nullable = false)
    private String orders;

    @Column(columnDefinition="TEXT")
    private String photo;

    @Column(columnDefinition="TEXT")
    private String genre;

    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lately> latelyList = new ArrayList<>();

    @Builder
    public Recipe(Long id, Long recipeId, String name, String thumbnail, Time time, Difficulty difficulty, Composition composition, String ingredients, Servings servings, String orders, String photo, String genre) {
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

    public void addBookmarkList(Bookmark bookmark) {
        this.bookmarkList.add(bookmark);
        if(bookmark.getRecipeId() != this) {
            bookmark.setRecipe(this);
        }
    }

    public void addLatelyList(Lately lately){
        this.latelyList.add(lately);
        if(lately.getRecipeId() != this){
            lately.setRecipe(this);
        }
    }
}

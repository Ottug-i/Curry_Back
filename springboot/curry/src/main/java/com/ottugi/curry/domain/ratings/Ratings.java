package com.ottugi.curry.domain.ratings;

import com.ottugi.curry.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ratings extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rating_Id")
    private Long id;

    private Long userId;

    private Long recipeId;

    private Double rating;

    @Builder
    public Ratings(Long id, Long userId, Long recipeId, Double rating) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.rating = rating;
    }

    public Ratings updateRatings(Double rating) {
        this.rating = rating;
        return this;
    }
}

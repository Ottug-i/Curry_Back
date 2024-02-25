package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.BaseTime;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Lately extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Lately_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recipe_Id", referencedColumnName = "Recipe_Id")
    private Recipe recipeId;

    @Builder
    public Lately(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.userId = user;
        if (!userId.getLatelyList().contains(this)) {
            user.getLatelyList().add(this);
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipeId = recipe;
    }
}

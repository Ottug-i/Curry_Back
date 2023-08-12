package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.BaseTime;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public void setUser(User user) {
        this.userId = user;
        if(!userId.getLatelyList().contains(this))
            user.getLatelyList().add(this);
    }

    public void setRecipe(Recipe recipe) {
        this.recipeId = recipe;
        if(!recipeId.getLatelyList().contains(this))
            recipe.getLatelyList().add(this);
    }
}

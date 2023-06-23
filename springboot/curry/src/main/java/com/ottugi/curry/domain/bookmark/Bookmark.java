package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bookmark_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recipe_Id", referencedColumnName = "Recipe_Id")
    private Recipe recipeId;

    public void setUser(User user) {
        this.userId = user;

        if(!userId.getBookmarkList().contains(this))
            user.getBookmarkList().add(this);
    }

    public void setRecipe(Recipe recipe) {
        this.recipeId = recipe;
    }
}

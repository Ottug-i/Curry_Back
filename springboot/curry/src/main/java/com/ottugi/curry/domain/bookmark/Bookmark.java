package com.ottugi.curry.domain.bookmark;

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
public class Bookmark extends BaseTime {
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

    @Builder
    public Bookmark(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.userId = user;
        if (!userId.getBookmarkList().contains(this)) {
            user.getBookmarkList().add(this);
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipeId = recipe;
    }
}

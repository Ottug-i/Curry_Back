package com.ottugi.curry.domain.user;

import com.ottugi.curry.domain.BaseTime;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.lately.Lately;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String nickName;

    @Column(length = 100)
    private String favoriteGenre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Boolean isNew;

    private Boolean withdraw;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Lately> latelyList = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickName, String favoriteGenre, Role role) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.favoriteGenre = favoriteGenre;
        this.isNew = true;
        this.role = role;
    }

    public void updateProfile(String nickName) {
        this.nickName = nickName;
    }

    public void updateGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void markAsExistingUser() {
        this.isNew = false;
    }

    public void withdrawUser() {
        this.withdraw = true;
        this.nickName = "탈퇴한 회원";
    }

    public void addBookmarkList(Bookmark bookmark) {
        this.bookmarkList.add(bookmark);

        if (bookmark.getUserId() != this) {
            bookmark.setUser(this);
        }
    }

    public void addLatelyList(Lately lately) {
        this.latelyList.add(lately);

        if (lately.getUserId() != this) {
            lately.setUser(this);
        }
    }
}
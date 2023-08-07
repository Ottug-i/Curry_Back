package com.ottugi.curry.domain.user;

import com.ottugi.curry.domain.BaseTime;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.lately.Lately;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lately> latelyList = new ArrayList<>();

    @Builder
    public User(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    public User updateProfile(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void addBookmarkList(Bookmark bookmark) {
        this.bookmarkList.add(bookmark);

        if(bookmark.getUserId() != this) {
            bookmark.setUser(this);
        }
    }

    public void addLatelyList(Lately lately) {
        this.latelyList.add(lately);

        if(lately.getUserId() != this) {
            lately.setUser(this);
        }
    }
}

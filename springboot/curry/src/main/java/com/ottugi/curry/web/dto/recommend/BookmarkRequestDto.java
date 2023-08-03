package com.ottugi.curry.web.dto.recommend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {

    @ApiModelProperty(notes = "북마크 아이디들", example = "[6842324, 6845721, 6845906, 6846020, 6846262]", required = true)
    private List<Integer> bookmarks;

    @Builder
    public BookmarkRequestDto(List<Integer> bookmarks) {
        this.bookmarks = bookmarks;
    }
}

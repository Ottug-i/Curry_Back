package com.ottugi.curry.domain.recipe;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Time {
    FIVE_MINUTES("5분 이내", 5),
    TEN_MINUTES("10분 이내", 10),
    FIFTEEN_MINUTES("15분 이내", 15),
    TWENTY_MINUTES("20분 이내", 20),
    THIRTY_MINUTES("30분 이내", 30),
    SIXTY_MINUTES("60분 이내", 60),
    NINETY_MINUTES("90분 이내", 90),
    ONE_HUNDRED_TWENTY_MINUTES("120분 이내", 120),
    TWO_HOURS("2시간 이상", 121);

    private String timeName;
    private Integer time;

    Time(String timeName, Integer time) {
        this.timeName = timeName;
        this.time = time;
    }

    public static Time ofTimeName(Integer time) {
        return Arrays.stream(Time.values())
                .filter(t -> t.getTime().equals(time))
                .findAny().orElse(null);
    }

    public static Time ofTime(String timeName) {
        return Arrays.stream(Time.values())
                .filter(t -> t.getTimeName().equals(timeName))
                .findAny().orElse(null);
    }

    public static Boolean isTimeMatching(Recipe recipe, String timeName) {
        if (timeName.equals(TWO_HOURS.getTimeName())) {
            return recipe.getTime().getTimeName().contains(TWO_HOURS.getTimeName());
        }
        return recipe.getTime().getTime() <= ofTime(timeName).getTime();
    }
}

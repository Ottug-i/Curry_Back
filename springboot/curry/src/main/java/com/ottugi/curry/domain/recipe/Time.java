package com.ottugi.curry.domain.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Time {

    fiveMinutes("5분 이내", 5),
    tenMinutes("10분 이내", 10),
    fifteenMinutes("15분 이내", 15),
    twentyMinutes("20분 이내", 20),
    thirtyMinutes("30분 이내", 30),
    sixtyMinutes("60분 이내", 60),
    ninetyMinutes("90분 이내", 90),
    oneHundredTwentyMinutes("120분 이내", 120),
    twoHours("2시간 이상", 121);

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
}

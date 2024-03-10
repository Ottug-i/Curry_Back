package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.InvalidException;
import java.util.HashMap;
import java.util.Map;
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

    private final String timeName;
    private final int timeInMinutes;

    private static final Map<String, Time> TIME_NAME_MAP = new HashMap<>();
    private static final Map<Integer, Time> TIME_MINUTES_MAP = new HashMap<>();

    static {
        for (Time time : Time.values()) {
            TIME_NAME_MAP.put(time.getTimeName(), time);
            TIME_MINUTES_MAP.put(time.getTimeInMinutes(), time);
        }
    }

    public static Time findByTimeName(String timeName) {
        Time foundTime = TIME_NAME_MAP.get(timeName);
        if (foundTime == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundTime;
    }

    public static Time findByTimeInMinutes(int timeInMinutes) {
        Time foundTime = TIME_MINUTES_MAP.get(timeInMinutes);
        if (foundTime == null) {
            throw new InvalidException(BaseCode.BAD_REQUEST);
        }
        return foundTime;
    }

    public static Boolean matchesTime(Recipe recipe, String timeName) {
        if (timeName == null || timeName.isBlank()) {
            return true;
        }
        if (timeName.equals(TWO_HOURS.getTimeName())) {
            return recipe.getTime().equals(TWO_HOURS);
        }
        return recipe.getTime().getTimeInMinutes() <= findByTimeName(timeName).getTimeInMinutes();
    }
}

package com.ottugi.curry.web.controller;

import com.ottugi.curry.web.dto.recommend.BookmarkRequestDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

@Api(tags={"Python Test API (Python 스크립트 테스트)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PythonTestController {

    @PostMapping("/recommend") // 완료
    public String find_favorite_genre_with_bookmark(@RequestParam int userId, @RequestBody BookmarkRequestDto bookmarkRequestDto) {

        List<Integer> bookmarkList = bookmarkRequestDto.getBookmarks();

        try {
            String pythonScriptFileName = "find_favorite_genre_with_bookmark.py";
            String pythonScriptPath = Paths.get("src", "main", "python", pythonScriptFileName).toString();

            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, String.valueOf(userId));
            for (Integer recipeId : bookmarkList) {
                processBuilder.command().add(String.valueOf(recipeId));
            }
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error occurred while executing Python script.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while executing Python script.";
        }
    }

    @GetMapping("/recommendd") // 완료
    public String find_user_ratings(@RequestParam int userId, int recipeId) {

        try {
            String pythonScriptFileName = "find_user_ratings.py";
            String pythonScriptPath = Paths.get("src", "main", "python", pythonScriptFileName).toString();

            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, String.valueOf(userId), String.valueOf(recipeId));
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error occurred while executing Python script.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while executing Python script.";
        }
    }

    @GetMapping("/recommenddd") // 완료
    public String recommend_with_bookmark(@RequestParam int recipeId, int page) {

        try {
            String pythonScriptFileName = "recommend_with_bookmark.py";
            String pythonScriptPath = Paths.get("src", "main", "python", pythonScriptFileName).toString();

            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, String.valueOf(recipeId), String.valueOf(page));
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error occurred while executing Python script.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while executing Python script.";
        }
    }

    @GetMapping("/recommendddd") // 수정 필요
    public String recommend_with_ratings(@RequestParam int userId, String favoriteGenre, int page) {

        try {
            String pythonScriptFileName = "recommend_with_ratings.py";
            String pythonScriptPath = Paths.get("src", "main", "python", pythonScriptFileName).toString();

            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, String.valueOf(userId), favoriteGenre, String.valueOf(page));
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error occurred while executing Python script.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while executing Python script.";
        }
    }

    @GetMapping("/recommenddddd") // 수정 필요
    public String update_or_append_user_ratings(@RequestParam int userId, String newRatingList) {

        try {
            String pythonScriptFileName = "update_or_append_user_ratings.py";
            String pythonScriptPath = Paths.get("src", "main", "python", pythonScriptFileName).toString();

            String[] newRatings = newRatingList.split(",");
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, String.valueOf(userId), String.valueOf(userId));

            for (int i = 0; i < newRatings.length; i += 2) {
                processBuilder.command().add(newRatings[i]);
                processBuilder.command().add(newRatings[i + 1]);
            }

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error occurred while executing Python script.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while executing Python script.";
        }
    }
}
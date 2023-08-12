package com.ottugi.curry.service;

import com.ottugi.curry.config.exception.BaseCode;
import com.ottugi.curry.config.exception.BaseException;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.Time;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final BookmarkRepository bookmarkRepository;

    public CommonService(UserRepository userRepository, RecipeRepository recipeRepository, BookmarkRepository bookmarkRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    /** 회원 관련 함수 */

    // 회원 기본키로 회원 조회
    @Transactional(readOnly = true)
    public User findByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(BaseCode.USER_NOT_FOUND));
    }

    // 회원 이메일로 회원 조회
    @Transactional(readOnly = true)
    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseCode.USER_NOT_FOUND));
    }

    /** 레시피 관련 함수 */

    // 레시피 아이디로 레시피 조회
    @Transactional(readOnly = true)
    public Recipe findByRecipeId(Long recipeId) {
        return recipeRepository.findByRecipeId(recipeId).orElseThrow(() -> new BaseException(BaseCode.RECIPE_NOT_FOUND));
    }

    // 레시피 아이디 리스트로 레시피 조회
    @Transactional(readOnly = true)
    public List<Recipe> findByRecipeIdIn(List<Long> recipeIdList) {
        List<Recipe> recipeList = recipeRepository.findByRecipeIdIn(recipeIdList);
        if(recipeList.size() != recipeIdList.size()) {
            throw new BaseException(BaseCode.RECIPE_NOT_FOUND);
        }
        return recipeList;
    }

    // 레시피 재료로 레시피 조회
    @Transactional(readOnly = true)
    public List<Recipe> findByIngredientsContaining(String ingredients) {
        return recipeRepository.findByIngredientsContaining(ingredients);
    }

    // 레시피 기본키로 레시피 조회
    @Transactional(readOnly = true)
    public List<Recipe> findByIdIn(List<Long> idList) {
        List<Recipe> recipeList = recipeRepository.findByIdIn(idList);
        if(recipeList.size() != idList.size()) {
            throw new BaseException(BaseCode.RECIPE_NOT_FOUND);
        }
        return recipeList;
    }

    // 레시피 조건 일치
    @Transactional(readOnly = true)
    public Boolean isRecipeMatching(Recipe recipe, String time, String difficulty, String composition) {
        return recipe.getTime().getTime() <= Time.ofTime(time).getTime() && recipe.getDifficulty().getDifficulty().contains(difficulty) && recipe.getComposition().getComposition().contains(composition);
    }

    /** 북마크 관련 함수 */

    // 북마크 여부 조회
    @Transactional(readOnly = true)
    public Boolean isBookmarked(User user, Recipe recipe) {
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe) != null;
    }

    // 회원 기본키와 레시피 아이디로 북마크 조회
    @Transactional(readOnly = true)
    public Bookmark findBookmarkByUserAndRecipe(User user, Recipe recipe) {
        return bookmarkRepository.findByUserIdAndRecipeId(user, recipe);
    }

    // 회원 기본키에 따른 북마크 목록 조회
    @Transactional(readOnly = true)
    public List<Bookmark> findBookmarkByUser(User user) {
        return bookmarkRepository.findByUserId(user);
    }

    /** 페이지 처리 관련 함수 */

    // 페이지 처리
    @Transactional
    public <T> Page<T> getPage(List<T> dtoList, int page, int size) {
        int totalItems = dtoList.size();
        int fromIndex = Math.max(0, page - 1) * size;
        int toIndex = Math.min(totalItems, fromIndex + size);

        List<T> pageList = dtoList.subList(fromIndex, toIndex);
        return new PageImpl<>(pageList, PageRequest.of(page - 1, size), totalItems);
    }
}

import pandas as pd
import sys
import os

def recommend_with_bookmark(recipeId, page):

    script_dir = os.path.dirname(__file__)
    csv_path = os.path.join(script_dir, '../resources/csv/genres.csv')
    curry_genres = pd.read_csv(csv_path, index_col='id')

    genres = curry_genres.loc[recipeId, 'ing1':'ing25']
    selected_genres = genres[genres == 1].index

    genre_scores = {}
    for idx, row in curry_genres.iterrows():
        if all(row[genre] == 1 for genre in selected_genres):
            genre_score = sum(row[genre] == 1 for genre in genres.index if genres[genre] == 1)
            genre_score += sum(row[genre] == 0 for genre in genres.index if genres[genre] == 0)
            genre_scores[idx] = genre_score

    sorted_genre_scores = sorted(genre_scores.items(), key=lambda x: x[1], reverse=True)

    similar_recipe_ids = [recipeId for recipeId, similarity_score in sorted_genre_scores]
    similar_recipe_ids = [id for id in similar_recipe_ids if id != recipeId]

    start_idx = (page - 1) * 5
    end_idx = start_idx + 5

    return similar_recipe_ids[start_idx:end_idx]

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python recommend_with_bookmark.py <recipeId> <page>")
        sys.exit(1)

    recipeId = int(sys.argv[1])
    page = int(sys.argv[2])

    try:
        recommended_recipes = recommend_with_bookmark(recipeId, page)
        if recommended_recipes:
            print(recommended_recipes)
        else:
            print("No recommendations found for the specified recipe ID.")
    except FileNotFoundError:
        print("Error: The CSV file 'genres.csv' was not found.")
    except Exception as e:
        print("Error:", e)
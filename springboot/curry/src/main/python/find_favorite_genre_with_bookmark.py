import pandas as pd
import sys
import os

def find_favorite_genre_with_bookmark(userId, bookmark_list):

    script_dir = os.path.dirname(__file__)
    csv_path = os.path.join(script_dir, '../resources/csv/curry_recipe_with_genres.csv')
    curry_recipes = pd.read_csv(csv_path, index_col='id')

    genre_count = {}
    for recipe_id in bookmark_list:
        genres = curry_recipes.loc[recipe_id, 'genres'].split('|')
        for genre in genres:
            genre_count[genre] = genre_count.get(genre, 0) + 1

    favorite_genre = max(genre_count, key=genre_count.get)
    return str(userId) + ", '" + favorite_genre + "'"

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Usage: python find_favorite_genre_with_bookmark.py <userId> <recipe_id1> <recipe_id2> ...")
        sys.exit(1)

    userId = int(sys.argv[1])
    bookmark_list = [int(recipe_id) for recipe_id in sys.argv[2:]]

    try:
        result = find_favorite_genre_with_bookmark(userId, bookmark_list)
        print(result)
    except KeyError as e:
        print("Error: Recipe ID not found in the CSV file.", e)
    except Exception as e:
        print("Error:", e)
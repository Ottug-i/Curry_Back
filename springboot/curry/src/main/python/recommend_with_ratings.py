import pandas as pd
import sys
import os
from sklearn.model_selection import train_test_split
from sklearn.linear_model import Lasso

def recommend_with_ratings(userId, favorite_genre, page):

    script_dir = os.path.dirname(__file__)
    csv_path = os.path.join(script_dir, '../resources/csv/user_ratings.csv')
    user_ratings = pd.read_csv(csv_path)
    csv_path = os.path.join(script_dir, '../resources/csv/genres.csv')
    curry_genres = pd.read_csv(csv_path)

    user_ratings = user_ratings.merge(curry_genres, left_on='id', right_on='id')

    user_profile = user_ratings[user_ratings['userId'] == userId]

    X_train, X_test, y_train, y_test = train_test_split(user_profile[curry_genres.columns], user_profile['rating'], random_state=42, test_size=0.1)

    lasso = Lasso(alpha=0.005)

    lasso.fit(X_train, y_train)

    predictions = lasso.predict(curry_genres)
    curry_genres['predict'] = predictions

    sorted_user_predict = curry_genres.sort_values(by=['predict', favorite_genre], ascending=[False, False])

    user_predict_ids = sorted_user_predict.id.tolist()

    start_idx = (page - 1) * 10
    end_idx = start_idx + 10

    return user_predict_ids[start_idx:end_idx]

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python recommend_with_ratings.py <userId> <favorite_genre> <page>")
        sys.exit(1)

    userId = int(sys.argv[1])
    favorite_genre = sys.argv[2]
    page = int(sys.argv[3])

    try:
        recommended_recipes = recommend_with_ratings(userId, favorite_genre, page)
        if recommended_recipes:
            print(recommended_recipes)
        else:
            print("No recommendations found for the specified user and genre.")
    except FileNotFoundError:
        print("Error: The CSV files 'user_ratings.csv' or 'genres.csv' were not found.")
    except Exception as e:
        print("Error:", e)

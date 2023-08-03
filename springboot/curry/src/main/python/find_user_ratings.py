import pandas as pd
import sys
import os

def find_user_ratings(userId, recipeId):

    script_dir = os.path.dirname(__file__)
    csv_path = os.path.join(script_dir, '../resources/csv/user_ratings.csv')
    curry_ratings = pd.read_csv(csv_path, index_col='id')

    user_ratings = curry_ratings[curry_ratings['userId'] == userId]

    if recipeId in user_ratings.index:
        rating = user_ratings.loc[recipeId, 'rating']
        return f"[{recipeId}, {userId}, {rating}]"
    else:
        return False

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Usage: python find_user_ratings.py <userId> <recipeId>")
        sys.exit(1)

    userId = int(sys.argv[1])
    recipeId = int(sys.argv[2])

    try:
        result = find_user_ratings(userId, recipeId)
        if result:
            print(result)
        else:
            print("False")
    except FileNotFoundError:
        print("Error: The CSV file 'user_ratings.csv' was not found.")
    except Exception as e:
        print("Error:", e)
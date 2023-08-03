import csv
import os

def update_or_append_user_ratings(userId, new_user_ratings_dic):

    script_dir = os.path.dirname(__file__)
    file_path = os.path.join(script_dir, '../resources/csv/user_ratings.csv')

    file_exists = os.path.exists(file_path)

    existing_ratings = []
    if file_exists:
        with open(file_path, 'r') as csvfile:
            csv_reader = csv.DictReader(csvfile)
            existing_ratings = [row for row in csv_reader]

    with open(file_path, 'w', newline='') as csvfile:
        fieldnames = ['id', 'userId', 'rating']
        csv_writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        csv_writer.writeheader()

        for rating in existing_ratings:
            if (rating['userId'] == str(userId)) and (int(rating['id']) in new_user_ratings_dic):
                rating['rating'] = new_user_ratings_dic[int(rating['id'])]

            csv_writer.writerow(rating)

        for key, val in new_user_ratings_dic.items():
            if not any(rating['userId'] == str(userId) and int(rating['id']) == key for rating in existing_ratings):
                csv_writer.writerow({'id': key, 'userId': str(userId), 'rating': val})

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Usage: python update_or_append_user_ratings.py <userId> <new_rating1> <new_rating2> ...")
        sys.exit(1)

    userId = int(sys.argv[1])
    new_user_ratings_dic = {}
    for i in range(2, len(sys.argv), 2):
        recipe_id = int(sys.argv[i])
        rating = float(sys.argv[i + 1])
        new_user_ratings_dic[recipe_id] = rating

    try:
        update_or_append_user_ratings(userId, new_user_ratings_dic)
        print("User ratings updated successfully.")
    except Exception as e:
        print("Error:", e)
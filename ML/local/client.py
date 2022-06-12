import requests

# server url
URL = "http://54.84.37.0/predict"

# audio file we'd like to send for predicting keyword
FILE_PATH = "test/Dix_00.wav"

if __name__ == "__main__":

    # open files
    file = open(FILE_PATH, "rb")

    # package stuff to send and perform POST request
    values = {"file": (FILE_PATH, file, "audio/wav")}
    response = requests.post(URL, files=values)
    data = response.json()

    print(values)
    print("Predicted keyword: {}".format(data["keyword"]))
    print("Predicted language: {}".format(data["language"]))

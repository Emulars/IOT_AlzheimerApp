import random
import os
from flask import Flask, request, jsonify
from keyword_spotting_service import Keyword_Spotting_Service
from language_spotting_service import Language_Spotting_Service


# instantiate flask app
app = Flask(__name__)

# Language Spotting Service
@app.route("/predictLanguage", methods=["POST"])
def predict_Language():
	"""Endpoint to predict keyword
    :return (json): This endpoint returns a json file with the following format:
        {
            "keyword": "down"
        }
	"""

	# get file from POST request and save it
	audio_file = request.files["file"]
	file_name = str(random.randint(0, 100000))
	audio_file.save(file_name)

	# instantiate keyword spotting service singleton and get prediction
	kss = Language_Spotting_Service()
	predicted_keyword = kss.predict(file_name)

	# we don't need the audio file any more - let's delete it!
	os.remove(file_name)

	# send back result as a json file
	result = {"keyword": predicted_keyword}
	return jsonify(result)


# Speech to Text Spotting Service
@app.route("/predictSpeechToText", methods=["POST"])
def predict_SpeechToText():
	"""Endpoint to predict keyword
    :return (json): This endpoint returns a json file with the following format:
        {
            "keyword": "down"
        }
	"""

	# get file from POST request and save it
	audio_file = request.files["file"]
	file_name = str(random.randint(0, 100000))
	audio_file.save(file_name)

	# instantiate keyword spotting service singleton and get prediction
	kss = Keyword_Spotting_Service()
	predicted_keyword = kss.predict(file_name)

	# we don't need the audio file any more - let's delete it!
	os.remove(file_name)

	# send back result as a json file
	result = {"keyword": predicted_keyword}
	return jsonify(result)


if __name__ == "__main__":
    app.run(debug=False)
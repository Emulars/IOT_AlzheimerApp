import random
import os
from flask import Flask, request, jsonify
from keyword_spotting_service import Keyword_Spotting_Service
from language_spotting_service import Language_Spotting_Service


# instantiate flask app
app = Flask(__name__)

# Language and Keyword Spotting Service
@app.route("/predict", methods=["POST"])
def predict():
	"""Endpoint to predict keyword
    :return (json): This endpoint returns a json file with the following format:
        {
            "keyword": "down"
			"language": "EN"
        }
	"""

	# get file from POST request and save it
	audio_file = request.files["file"]
	file_name = str(random.randint(0, 100000))
	audio_file.save(file_name)

	# instantiate keyword spotting service singleton and get prediction
	# TODO: un-comment line 29 and delete line 30
	#kss1 = Keyword_Spotting_Service()
	kss1 = Language_Spotting_Service()
	predicted_keyword = kss1.predict(file_name)

	# instantiate language spotting service singleton and get prediction
	kss2 = Language_Spotting_Service()
	predicted_language = kss2.predict(file_name)

	# we don't need the audio file any more - let's delete it!
	os.remove(file_name)

	# send back result as a json file
	result = {"keyword": predicted_keyword, "language": predicted_language}
	return jsonify(result)


if __name__ == "__main__":
    app.run(debug=False)
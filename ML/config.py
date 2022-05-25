# PrepareDataset.py
DATASET_PATH = "ML/dataset/languages" # Default folder "ML/dataset" 
JSON_PATH = "ML/data.json"
SAMPLES_TO_CONSIDER = 22050 # 1 sec. of audio

# Train.py
#JSON_PATH = "ML/data.json"
SAVED_MODEL_PATH_M5 = "ML/languages_model.h5"
SAVED_MODEL_PATH_TFLITE = "ML/languages_model.tflite"
EPOCHS = 40
BATCH_SIZE = 32
PATIENCE = 5
LEARNING_RATE = 0.0001

# Keyword_spotting_service.py
SAVED_MODEL_PATH = "languages_model.h5"
#SAMPLES_TO_CONSIDER = 22050
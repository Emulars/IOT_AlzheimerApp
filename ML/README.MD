# How to

## Create dataset to train

1. Change "DATASET_PATH" path in PrepareDataset.py
2. Change "SAVED_MODEL_PATH_M5" and "SAVED_MODEL_PATH_TFLITE" path in train.py
3. Change "NUM_KEYWORDS" value based on the number of labels in the dataset in file train.py

## Create Tensorflow model 

1. Create folder "dataset" inside folder "ML"
2. Fill the folder with the desired folders structure and the audio file
   1. The audio file extension must be .wav
3. Run the script "PrepareDataset.py"
   1. It may take a while...
4. Run the script "train.py"
5. Now the model is ready to be used (model.h5)

## Docker - Container creation and start up

1. Move to "ML/server/"
2. Run "docker-compose up --build"

## Docker - Client connection

1. Move to "ML/local/"
2. Run "python3 client.py"

## Delete Docker Container

1. To show all containers run "docker container ls -a"
2. To stop a container run "docker stop <container_name>"
3. To delete all container run "docker system prune"

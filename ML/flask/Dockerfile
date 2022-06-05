# Pull tensorflow image with Python3
FROM tensorflow/tensorflow:latest

ENV TZ=Europe/Kiev
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Set the working directory to /app
WORKDIR /app

# Transfer content from current dir to /app in container
ADD . /app

# Install audio libraries
RUN apt-get update && pip install --upgrade pip && apt-get install -y libsndfile1 libasound-dev portaudio19-dev libportaudio2 libportaudiocpp0 ffmpeg

# Install python packages
RUN pip3 install -r requirements.txt 

# Start uWSGI using config file
CMD ["uwsgi", "app.ini"]

import os
from pydub import AudioSegment

FOLDERS = ["EN", "FR", "IT"]
MP3s_PATH = "ML/dataset/"
WAVs_PATH = "ML/dataset/languages/"

for i in FOLDERS:
    for j, (dirpath, dirnames, filenames) in enumerate(os.walk(MP3s_PATH+i)):
        if dirpath is not MP3s_PATH+i:
            for f in filenames:
                print("Converting file: ",f)
                sound = AudioSegment.from_mp3(dirpath+"/"+f)
                sound.export(WAVs_PATH+i+"/"+f.split(".", 1)[0]+".wav", format="wav")
                os.remove(dirpath+"/"+f)
            
print("Conversion terminated")


# convert wav to mp3                                                            
#sound = AudioSegment.from_mp3(src)
#sound.export(dst, format="wav")
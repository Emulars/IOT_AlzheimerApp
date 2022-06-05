import librosa
import tensorflow as tf
import numpy as np

SAVED_MODEL_PATH_LANG = "languages_model.h5"
# SAVED_MODEL_PATH_S2T = "speech2text_model.h5"
SAMPLES_TO_CONSIDER = 22050

class _Language_Spotting_Service:

    model = None
    _mapping = ["EN", "FR", "IT"]
    _instance = None

    def predict(self, file_path):
        # extract MFCC
        MFCCs = self.preprocess(file_path)

        # we need a 4-dim array to feed to the model for prediction: (# samples, # time steps, # coefficients, 1)
        MFCCs = MFCCs[np.newaxis, ..., np.newaxis]

        # get the predicted label
        predictions = self.model.predict(MFCCs)
        predicted_index = np.argmax(predictions)
        predicted_keyword = self._mapping[predicted_index]
        return predicted_keyword

    def preprocess(self, file_path, num_mfcc=13, n_fft=2048, hop_length=512):
        """Extract MFCCs from audio file.
        :param file_path (str): Path of audio file
        :param num_mfcc (int): # of coefficients to extract
        :param n_fft (int): Interval we consider to apply STFT. Measured in # of samples
        :param hop_length (int): Sliding window for STFT. Measured in # of samples
        :return MFCCs (ndarray): 2-dim array with MFCC data of shape (# time steps, # coefficients)
        """

        # load audio file
        signal, sample_rate = librosa.load(file_path)

        if len(signal) >= SAMPLES_TO_CONSIDER:
            # ensure consistency of the length of the signal
            signal = signal[:SAMPLES_TO_CONSIDER]

            # extract MFCCs
            MFCCs = librosa.feature.mfcc(signal, sample_rate, n_mfcc=num_mfcc, n_fft=n_fft,
                                         hop_length=hop_length)
        return MFCCs.T


def Language_Spotting_Service():
    # ensure an instance is created only the first time the factory function is called
    if _Language_Spotting_Service._instance is None:
        _Language_Spotting_Service._instance = _Language_Spotting_Service()
        _Language_Spotting_Service.model = tf.keras.models.load_model(SAVED_MODEL_PATH_LANG)
    return _Language_Spotting_Service._instance

if __name__ == "__main__":

    # create 2 instances of the keyword spotting service
    kss = Language_Spotting_Service()
    kss1 = Language_Spotting_Service()

    # check that different instances of the keyword spotting service point back to the same object (singleton)
    assert kss is kss1

    # make a prediction
    keyword = kss.predict("Celeste_1.wav")
    print(keyword)
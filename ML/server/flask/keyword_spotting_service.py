import librosa
import tensorflow as tf
import numpy as np

# SAVED_MODEL_PATH_LANG = "languages_model.h5"
SAVED_MODEL_PATH_S2T = "speech2text_model.h5"
SAMPLES_TO_CONSIDER = 22050

class _Keyword_Spotting_Service:

    model = None
    _mapping = ["Agosto", "Aprile", "Autista", "Autunno", "Avvocato", "Benedetto_Sedicesimo", "Celeste",
                  "Cento", "Cinquanta", "Cinque", "Davide", "Dicembre", "Diciannove", "Diciassette", "Diciotto", "Dieci",
                  "Dodici", "Domenica", "Due", "Duemila", "Duemilaventi", "Duemilaventidue", 'Duemilaventuno', "Estate",
                  "Febbraio", "Filippo", "Firenze", "Francesco", "Gennaio", "Genova", "Giorgio", "Giovanni_Paolo_Secondo",
                  "Giovedi", "Giugno", "Insegnante", "Inverno", "Luglio", "Lunedi", "Maggio", "Mario", "Martedi", "Marzo",
                  "Matita", "Mattarella", "Mattina", "Mercoledi", "Millenovecento", "Millenovecentodiciotto", "Millenovecentoquarantacinque",
                  "Millenovecentoquattordici", "Millenovecentotrentanove", "Napoli", "Napolitano", "Notte", "Novanta", "Nove", "Novembre",
                  "Ottanta", "Otto", "Ottobre", "Palla", "Piatto", "Pomeriggio", "Pompiere", "Primavera", "Quaranta", "Quattordici", "Quattro",
                  "Quindici", "Sabato", "Sedici", "Sei", "Sera", "Sergio", "Sessanta", "Settanta", "Sette", "Settembre", "Tre", "Tredici",
                  "Trenta", "Trentuno", "Undici", "Uno", "Venerdi", "Venezia", "Venti", "Venticinque", "Ventidue", "Ventinove", "Ventiquattro",
                  "Ventisei", "Ventisette", "Ventitre", "Ventotto", "Ventuno"
                  
                  "August", "April", "Driver", "Autumn", "Lawyer", "Celestial",
                  "Hundred", "Fifty", "Five", "Dave", "December", "Nineteen", "Seventeen", "Eighteen", "Ten",
                  "Twelve", "Sunday", "Two", "Two_Thousand", "Two_Thousand_Twenty", "Two_Thousand_Twenty_Two", "Two_Thousand_Twenty_One", "Summer",
                  "February", "Philipp", "Florence", "January", "Genoa", 
                  "Thursday", "June", "Teacher", "Winter", "July", "Monday", "May", "Tuesday", "March",
                  "Pencil", "Morning", "Wednesday", "Nineteen_Hundred", "Nineteen_Hundred_Eighteen", "Nineteen_Hundred_Forty_Five",
                  "Nineteen_Hundred_Fourteen", "Nineteen_Hundred_Thirty_Nine", "Naples", "Night", "Ninety", "Nine", "November",
                  "Eighty", "Eight", "October", "Ball", "Plate", "Afternoon", "Fireman", "Spring", "Forty", "Fourteen", "Four",
                  "Fifteen", "Saturday", "Sixteen", "Six", "Evening", "Sixty", "Seventy", "Seven", "September", "Three", "Thirteen",
                  "Thirty", "Thirty_One", "Eleven", "One", "Friday", "Venice", "Twenty", "Twenty_Five", "Twenty_Two", "Twenty_Nine", "Twenty_Four",
                  "Twenty_Six", "Twenty_Seven", "Twenty_Three", "Twenty_Eight", "Twenty_One"
                  
                  "Aout", "Avril", "Chauffeur", "Automne", "Avocat",
                  "Cent", "Cinquante", "Cinq", "Decembre", "Dix_Neuf", "Dix_Sept", "Dix_Huit", "Dix",
                  "Douze", "Dimanche", "Deux", "Deux_mille", "Deux_Mille_Vingt", "Deux_Mille_Vingt_Deux", "Deux_Mille_Vingt_Et_Un", "Ete",
                  "Fevrier", "Florence", "Janvier", "Genes",
                  "Jeudi", "Juin", "Professeur", "Hiver", "Juillet", "Lundi", "Mai", "Mardi", "Mars",
                  "Crayon", "Matin", "Mercredi", "Mille_Neuf_Cent", "Mille_Neuf_Nent_Dix_Huit", "Mille_Neuf_Cent_Quarante_Cinq",
                  "Mil_Neuf_Cent_Quatorze","Mille_Neuf_Cent_Trente_Neuf", "Naples","Nuit", "Quatre_Vingt_Dix", "Neuf", "Novembre",
                  "Quatre_Vingt", "Huit", "Octobre", "Ballon", " Apres_Midi", " Pompier", " Printemps", " Quarante", " Quatorze", " Quatre",
                  "Quinze", "Samedi", "Seize", "Six", "Soir", "Soixante", "Soixante_Dix", "Sept", "Septembre", "Trois", "Treize",
                  "Trente", "Trente_Et_Un", "Onze", "Un", "Vendredi", "Venise", "Vingt", "Vingt_Cinq", "Vingt_Deux", "Vingt_Neuf", "Vingt_Quatre",
                  "Vingt_Six", "Vingt_Sept", "Vingt_Trois", "Vingt_Huit", "Vingt_Et_Un"]
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


def Keyword_Spotting_Service():
    # ensure an instance is created only the first time the factory function is called
    if _Keyword_Spotting_Service._instance is None:
        _Keyword_Spotting_Service._instance = _Keyword_Spotting_Service()
        _Keyword_Spotting_Service.model = tf.keras.models.load_model(SAVED_MODEL_PATH_S2T)
    return _Keyword_Spotting_Service._instance

if __name__ == "__main__":

    # create 2 instances of the keyword spotting service
    kss = Keyword_Spotting_Service()
    kss1 = Keyword_Spotting_Service()

    # check that different instances of the keyword spotting service point back to the same object (singleton)
    assert kss is kss1

    # make a prediction
    keyword = kss.predict("Celeste_1.wav")
    print(keyword)
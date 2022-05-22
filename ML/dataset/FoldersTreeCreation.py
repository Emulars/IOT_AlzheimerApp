import os
ML = os.getcwd()

language = os.path.join(ML, "languages") 
speechtext = os.path.join(ML, "speech2text")

language_list = ["EN", "FR", "IT"]
speech_list_IT = ["Agosto", "Aprile", "Autista", "Autunno", "Avvocato", "Benedetto_Sedicesimo", "Celeste",
                  "Cento", "Cinquanta", "Cinque", "Davide", "Dicembre", "Diciannove", "Diciassette", "Diciotto", "Dieci",
                  "Dodici", "Domenica", "Due", "Duemila", "Duemilaventi", "Duemilaventidue", 'Duemilaventuno', "Estate",
                  "Febbraio", "Filippo", "Firenze", "Francesco", "Gennaio", "Genova", "Giorgio", "Giovanni_Paolo_Secondo",
                  "Giovedì", "Giugno", "Insegnante", "Inverno", "Luglio", "Lunedì", "Maggio", "Mario", "Martedì", "Marzo",
                  "Matita", "Mattarella", "Mattina", "Mercoledì", "Millenovecento", "Millenovecentodiciotto", "Millenovecentoquarantacinque",
                  "Millenovecentoquattordici", "Millenovecentotrentanove", "Napoli", "Napolitano", "Notte", "Novanta", "Nove", "Novembre",
                  "Ottanta", "Otto", "Ottobre", "Palla", "Piatto", "Pomeriggio", "Pompiere", "Primavera", "Quaranta", "Quattordici", "Quattro",
                  "Quindici", "Sabato", "Sedici", "Sei", "Sera", "Sergio", "Sessanta", "Settanta", "Sette", "Settembre", "Tre", "Tredici",
                  "Trenta", "Trentuno", "Undici", "Uno", "Venerdì", "Venezia", "Venti", "Venticinque", "Ventidue", "Ventinove", "Ventiquattro",
                  "Ventisei", "Ventisette", "Ventitre", "Ventotto", "Ventuno"]


speech_list_EN = []
speech_list_FR = []

############################################################
##                   CREATION                             ##
############################################################
for i in language_list:
  os.makedirs(os.path.join(language, i))

for i in speech_list_IT:
  os.makedirs(os.path.join(speechtext, i))

for i in speech_list_EN:
  os.makedirs(os.path.join(speechtext, i))

for i in speech_list_FR:
  os.makedirs(os.path.join(speechtext, i))
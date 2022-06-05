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


speech_list_EN = ["August", "April", "Driver", "Autumn", "Lawyer", "Celestial",
                  "Hundred", "Fifty", "Five", "Dave", "December", "Nineteen", "Seventeen", "Eighteen", "Ten",
                  "Twelve", "Sunday", "Two", "Two_Thousand", "Two_Thousand_Twenty", "Two_Thousand_Twenty_Two", "Two_Thousand_Twenty_One", "Summer",
                  "February", "Philipp", "Florence", "January", "Genoa", 
                  "Thursday", "June", "Teacher", "Winter", "July", "Monday", "May", "Tuesday", "March",
                  "Pencil", "Morning", "Wednesday", "Nineteen_Hundred", "Nineteen_Hundred_Eighteen", "Nineteen_Hundred_Forty_Five",
                  "Nineteen_Hundred_Fourteen", "Nineteen_Hundred_Thirty_Nine", "Naples", "Night", "Ninety", "Nine", "November",
                  "Eighty", "Eight", "October", "Ball", "Plate", "Afternoon", "Fireman", "Spring", "Forty", "Fourteen", "Four",
                  "Fifteen", "Saturday", "Sixteen", "Six", "Evening", "Sixty", "Seventy", "Seven", "September", "Three", "Thirteen",
                  "Thirty", "Thirty_One", "Eleven", "One", "Friday", "Venice", "Twenty", "Twenty_Five", "Twenty_Two", "Twenty_Nine", "Twenty_Four",
                  "Twenty_Six", "Twenty_Seven", "Twenty_Three", "Twenty_Eight", "Twenty_One"]

speech_list_FR = ["Août", "Avril", "Chauffeur", "Automne", "Avocat",
                  "Cent", "Cinquante", "Cinq", "Décembre", "Dix_Neuf", "Dix_Sept", "Dix_Huit", "Dix",
                  "Douze", "Dimanche", "Deux", "Deux_mille", "Deux_Mille_Vingt", "Deux_Mille_Vingt_Deux", "Deux_Mille_Vingt_Et_Un", "Été",
                  "Février", "Florence", "Janvier", "Gênes",
                  "Jeudi", "Juin", "Professeur", "Hiver", "Juillet", "Lundi", "Mai", "Mardi", "Mars",
                  "Crayon", "Matin", "Mercredi", "Mille_Neuf_Cent", "Mille_Neuf_Nent_Dix_Huit", "Mille_Neuf_Cent_Quarante_Cinq",
                  "Mil_Neuf_Cent_Quatorze","Mille_Neuf_Cent_Trente_Neuf", "Naples","Nuit", "Quatre_Vingt_Dix", "Neuf", "Novembre",
                  "Quatre_Vingt", "Huit", "Octobre", "Ballon", " Après_Midi ", " Pompier ", " Printemps ", " Quarante ", " Quatorze ", " Quatre ",
                  "Quinze", "Samedi", "Seize", "Six", "Soir", "Soixante", "Soixante_Dix", "Sept", "Septembre", "Trois", "Treize",
                  "Trente", "Trente_Et_Un", "Onze", "Un", "Vendredi", "Venise", "Vingt", "Vingt_Cinq", "Vingt_Deux", "Vingt_Neuf", "Vingt_Quatre",
                  "Vingt_Six", "Vingt_Sept", "Vingt_Trois", "Vingt_Huit", "Vingt_Et_Un"]

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
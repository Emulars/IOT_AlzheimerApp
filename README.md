# IOT_AlzheimerApp
Multimedia App and Internet of Things final project

Per non consumare il massimo di richieste gratuite disponibili per il server AWS abbiamo eseguito la maggior parte dei test utilizzando una copia del server locale.
Per potersi connettere al server remoto è necessario modificare il codice all'interno del file app/src/main/java/com/example/profile/PgQt.java alla riga 143 e sostituire la variabile R.string.server_url_local con R.strings.server_url_remote

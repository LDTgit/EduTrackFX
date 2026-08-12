=======================================
PROIECT - APLICATIE TRIVIA
=======================================
I. TEHNOLOGII UTILIZATE
Limbaj: Java
Interfata Grafica: JavaFX (layout-uri dinamice: VBox, GridPane, ScrollPane)
Management Proiect: Maven (pentru gestionarea dependintelor)
Librarii externe:
   - Ikonli (FontAwesome) - pentru iconite vectoriale
   - HttpClient - pentru conexiunea cu servere externe
---------------------------------------
II. MODULE SI ECRANE IMPLEMENTATE (6 SCENE)
1. Autentificare (Login): Ecran de acces cu securizarea parolei.
                          Verifica credentialele prin compararea hash-urilor BCrypt.
                          Permite crearea de conturi noi cu validarea unicitatii userului.
2. Dashboard: Panou central de control cu butoane stilizate si efecte de hover.
3. Test Grila: Modul de testare care descarca intrebari in timp real.
4. Gestiune Date (Tabel): Vizualizarea istoricului intrebarilor.
5. Statistici (Grafic): Reprezentarea performantei prin PieChart.
6, Ajutor: Manual de utilizare integrat cu ScrollPane.
---------------------------------------
III. SURSA DATELOR (API EXTERN)
Aplicatia utilizeaza API-ul "Open Trivia Database" (https://opentdb.com/).
Datele sunt preluate prin cereri HTTP de tip GET, procesate din format JSON si curatate de entitati HTML pentru o afisare corecta.
--------------------------------------
IV. SECURITATE
Parolele sunt securizate folosind algoritmul BCrypt.
Baza de date locala (users.db) nu stocheaza parole in clar.
Sistem de validare a input-ului: prevenirea duplicatelor si a campurilor goale.
--------------------------------------
V. INSTRUCTIUNI DE RULARE
1. Din IDE (IntelliJ): Rulati clasa com.example.edutrackfx.Launcher
2. Din executabil (JAR):
Deschideti un terminal in folderul fisierului JAR si rulati comanda:
java --enable-native-access=ALL-UNNAMED -jar EduTrackFX-1.0-SNAPSHOT.jar
======================================

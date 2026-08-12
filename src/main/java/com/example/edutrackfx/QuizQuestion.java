package com.example.edutrackfx; //declararea pachetului in care este clasa
import java.util.List; //importam interfata List pentru a putea lucra cu colectii de variante de raspuns

//Aceasta clasa doar stocheaza datele unei intrebari, nu face nicio actiune
public class QuizQuestion {
    public String question; //textul intrebarii
    public String correctAnswer; //varianta corecta de raspuns
    public List<String> allAnswers; //lista tuturor variantelor de raspuns

    //Constructorul - creeaza o intrebare noua
    public QuizQuestion(String question, String correctAnswer, List<String> allAnswers){
        //this face referire la variabila clasei, pentru a o diferentia de parametrul metodei
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.allAnswers = allAnswers;
    }

    //Metode obligatorii pentru TableView
    public String getQuestion(){return question;}  //Metoda este folosita de TableView prin PropertyValueFactory
    public String getCorrectAnswer(){return correctAnswer;} //Aceasta metoda este apelata automat de PropertyValueFactory("correctAnswer")
}

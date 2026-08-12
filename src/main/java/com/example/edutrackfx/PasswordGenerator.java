package com.example.edutrackfx;

import org.mindrot.jbcrypt.BCrypt;

//Cream un generator de hash pentru parole
public class PasswordGenerator{
    public static void main(String[] args){
        String parolaMea = "admin"; //Aici introducem parola dorita
        
        //Generam hash-ul. 12 este "work factor", adica nivelul de complexitate
        String hashed = BCrypt.hashpw(parolaMea, BCrypt.gensalt(12));
        
        System.out.println("Hash-ul generat pentru users.db este:");
        System.out.println(hashed);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package fr.insa.titouan.encheres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Titouan
 */
public class Encheres {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection con = bdd.defaultConnect();
        ResultSet rs = bdd.getUsers(con);
        System.out.println("ID|Nom|Prenom|email|pw|codepostal");
        while (rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String pw = rs.getString("pw");
            String email = rs.getString("email");
            String codepostal = rs.getString("codepostal");
            System.out.println(id+"|"+"|"+nom+"|"+prenom+"|"+email+"|"+pw+"|"+codepostal);
        }
    }
}

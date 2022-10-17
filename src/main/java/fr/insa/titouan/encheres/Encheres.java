/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package fr.insa.titouan.encheres;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Titouan
 */
public class Encheres {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection con = bdd.defaultConnect();
        bdd.deleteTable(con);
    }
}

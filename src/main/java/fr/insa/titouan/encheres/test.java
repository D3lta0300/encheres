/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import static fr.insa.titouan.encheres.bdd.createSchema;
import static fr.insa.titouan.encheres.bdd.defaultConnect;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class test {

    public static void main(String[] args) {

        try (Connection con = defaultConnect()){
            System.out.println("Link is sucessfully etablished");
            bdd.deleteAllTables(con);
            createSchema(con);
            bdd.addExample(con);
            bdd.textInterface();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(bdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

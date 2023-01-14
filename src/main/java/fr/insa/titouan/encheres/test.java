/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import static fr.insa.titouan.encheres.bdd.defaultConnect;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class test {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        try (Connection con = defaultConnect()){
            PreparedStatement pst = con.prepareStatement("UPDATE articles SET image = ? WHERE id = ?");
            pst.setInt(2, 1);
            File file = new File("C:\\Users\\Titouan\\Downloads\\test.jpg");
            FileInputStream fis = new FileInputStream(file);
            pst.setBinaryStream(1, fis, (int)file.length());
            pst.executeUpdate();
            pst.close();
            fis.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

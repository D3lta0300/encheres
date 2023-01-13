/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import fr.insa.titouan.encheres.bdd;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class Page_connection extends VerticalLayout {
    private EmailField email;
    private PasswordField pw;
    private Button send;
    
    public Page_connection(VuePrincipale main){
        this.email = new EmailField("Quel est votre adresse mail ?");
        this.pw = new PasswordField("Veuillez choisir un mot de passe :");
        this.send = new Button("Valider");
        
        this.send.addClickListener((event) ->{
            try {
                if (bdd.userExists(email.getValue())){
                    if (bdd.rightPw(email.getValue(), pw.getValue())){
                        main.setUser(bdd.getUserId(email.getValue()));
                        Notification.show("Connecté");
                    }
                    else{
                        Notification.show("Mauvais mdp");
                    }
                }
                else{
                    Notification.show("Email non reconnu");
                }
            } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Page_connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        this.add(this.email, this.pw, this.send);
        
    }
}

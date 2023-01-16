/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
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
public class ConnectionPage extends VerticalLayout {
    private EmailField email;
    private PasswordField pw;
    private Button send;
    
    public ConnectionPage(VuePrincipale main, Component c){
        this.email = new EmailField("Quel est votre adresse mail ?");
        this.email.setWidth("80%");
        this.pw = new PasswordField("Veuillez choisir un mot de passe :");
        this.pw.setWidth("80%");
        this.send = new Button("Valider");
        
        this.send.addClickListener((event) ->{
            try {
                if (bdd.userExists(email.getValue())){
                    if (bdd.rightPw(email.getValue(), pw.getValue())){
                        main.setUser(bdd.getUserId(email.getValue()));
                        Notification.show("Connecté");
                        main.setPrincipal(c);
                    }
                    
                    else{
                        Notification.show("Mauvais mdp");
                    }
                }
                else{
                    Notification.show("Email non reconnu");
                }
            } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ConnectionPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        H2 title = new H2("Connexion");
        title.setWidth("100%");
        
        this.add(title,this.email, this.pw, this.send);
        
    }
}

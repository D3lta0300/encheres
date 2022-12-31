/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 * @author Titouan
 */
public class CreateAccount extends FormLayout {
    private TextField nom;
    private TextField prénom;
    private TextField postalcode;
    private EmailField email;
    private PasswordField pw;
    private PasswordField verify;
    private Button send;
    
    public CreateAccount(VuePrincipale main){
        this.prénom = new TextField("Quel est votre prénom ?");
        this.nom = new TextField("Quel est votre nom ?");
        this.postalcode = new TextField("Quel est votre code postale ?");
        this.email = new EmailField("Quel est votre adresse mail ?");
        this.pw = new PasswordField("Veuillez choisir un mot de passe :");
        this.verify = new PasswordField("Veuillez confirmer votre mot de passe :");
        this.send = new Button("Valider");
        
        this.send.addClickListener((event) ->{
            if (this.pw.getValue().equals(this.verify.getValue())){
                Notification.show("Votre compte va être créé.");
            } else {
                Notification.show("Il y a une erreur dans votre mot de passe.");
            }
        });
        
        this.add(this.prénom,this.nom, this.postalcode, this.email, this.pw, this.verify, this.send);
        
    }
}

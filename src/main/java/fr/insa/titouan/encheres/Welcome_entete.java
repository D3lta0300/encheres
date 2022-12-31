/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author Titouan
 */
public class Welcome_entete extends HorizontalLayout {
    
    public Welcome_entete(VuePrincipale main){
        this.add(new H1("PN - Enchères"));
        
        Button connect = new Button("Connexion");
        connect.addClickListener((event) -> {
            Notification.show("tout casser !!!");
        });
        
        Button createAccount = new Button("Nouvel utilisateur ?");
        createAccount.addClickListener((event) -> {
            Notification.show("Vous allez être redirigé vers le formulaire d'inscription.");
            main.setPrincipal(new CreateAccount(main));
        });
        
        this.add(connect, createAccount);
    }
}
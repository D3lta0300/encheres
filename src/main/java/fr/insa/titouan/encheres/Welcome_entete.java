/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class Welcome_entete extends HorizontalLayout {

    public Welcome_entete(VuePrincipale main) {
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

        Button reset = new Button("Réinitialiser la BdD");
        reset.addClickListener((event) -> {
            try {
                bdd.deleteAllTables(main.getSession().getCon());
                bdd.createSchema(main.getSession().getCon());
                bdd.addExample(main.getSession().getCon());
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Welcome_entete.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.add(connect, createAccount, reset);
    }
}

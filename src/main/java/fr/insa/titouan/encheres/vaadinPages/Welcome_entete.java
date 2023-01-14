/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.insa.titouan.encheres.bdd;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class Welcome_entete extends HorizontalLayout {

    public Welcome_entete(VuePrincipale main) {
        this.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        
        H1 title = new H1("PN - Enchères");
        title.setHeight("2em");
        this.add(title);

        Button connect = new Button("Connexion");
        connect.addClickListener((event) -> {
            main.setPrincipal(new ConnectionPage(main));
            Notification.show("Vous allez être connecté");
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

            } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Welcome_entete.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button showBids = new Button("Afficher les enchères");
        showBids.addClickListener((event) -> {
            main.setPrincipal(new ShowBids(main));
        });
        
        Button showObjects = new Button("Afficher les objets");
        showObjects.addClickListener((event)-> {
            try {
                main.setPrincipal(new ShowArticles(main));
            } catch (SQLException ex) {
                Logger.getLogger(Welcome_entete.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button createObject = new Button("Vendre un objet");
        createObject.addClickListener((event) -> {
            main.setPrincipal(new CreateArticle(main));
        });

        this.add(connect, createAccount, reset, showBids, showObjects, createObject);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

    VerticalLayout v1, v2, v3;

    public Welcome_entete(VuePrincipale main) {
        this.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        this.v1 = new VerticalLayout();
        this.v2 = new VerticalLayout();
        this.v3 = new VerticalLayout();

        this.v1.setDefaultHorizontalComponentAlignment(Alignment.START);
        this.v2.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        this.v3.setDefaultHorizontalComponentAlignment(Alignment.END);

        this.v1.setWidth("20%");
        this.v2.setWidth("60%");
        this.v3.setWidth("20%");

        H1 title = new H1("PN - Enchères");
        title.setHeight("2em");
        this.v1.add(title);
        title.addClickListener((event) -> {
            main.setPrincipal(new ShowBids(main));
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
        reset.setEnabled(false);
        
        
        TextField searchField = new TextField();
        searchField.setPlaceholder("Rechercher un article");
        Icon showObjects = new Icon("lumo", "search");
        showObjects.addClickListener((event) -> {
            main.setPrincipal(new ShowArticles(main, searchField.getValue()));
            System.out.println("Voici la chaine de caractère retenue : '" + searchField.getValue() + "'");
        });
        showObjects.addClickShortcut(Key.ENTER);
        searchField.setSuffixComponent(showObjects);

        Button createObject = new Button("Vendre un objet");
        createObject.addClickListener((event) -> {
            if (main.getSession().getUser() != -1) {
                main.setPrincipal(new CreateArticle(main));
            } else {
                Notification.show("Veuillez vous connecter");
                main.setPrincipal(new LogPage(main, new CreateArticle(main)));
            }
        });
        
        HorizontalLayout container = new HorizontalLayout(reset, searchField, createObject);
        this.v2.add(container);

        UserIcon uIcon = new UserIcon(main);
        uIcon.setAlignItems(Alignment.CENTER);
        uIcon.setWidth("5%");
        this.v3.add(uIcon);

        this.add(v1, v2, v3);
        this.setWidth("100%");
    }
}

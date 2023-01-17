/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
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
public class CreateAccount extends FormLayout {

    private TextField nom;
    private TextField prénom;
    private TextField postalcode;
    private EmailField email;
    private PasswordField pw;
    private PasswordField verify;
    private Button send;

    /**
     * *
     * Let create an Account. When account is created, set c component as main.
     *
     * @param main
     * @param c
     */
    public CreateAccount(VuePrincipale main, Component c) {
        this.prénom = new TextField("Quel est votre prénom ?");
        this.nom = new TextField("Quel est votre nom ?");
        this.nom.setRequired(true);
        this.postalcode = new TextField("Quel est votre code postal ?");
        this.email = new EmailField("Quel est votre adresse mail ?");
        this.pw = new PasswordField("Veuillez choisir un mot de passe :");
        this.pw.setRequired(true);
        this.verify = new PasswordField("Veuillez confirmer votre mot de passe :");
        this.verify.setRequired(true);
        this.send = new Button("Valider");

        this.send.addClickListener((event) -> {
            if (this.pw.getValue().equals(this.verify.getValue())) {
                Notification.show("Votre compte va être créé.");
                String[] user = new String[5];
                user[0] = this.nom.getValue();
                user[1] = this.prénom.getValue();
                user[2] = this.email.getValue();
                user[3] = this.pw.getValue();
                user[4] = this.postalcode.getValue();
                try {
                    main.getSession().setUser(bdd.addUser(main.getSession().getCon(), user));
                    main.setPrincipal(c);
                    main.setEntete(new Welcome_entete(main));
                } catch (SQLException ex) {
                    Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                    Notification.show("il y a une erreur de BdD");
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Notification.show("Il y a une erreur dans votre mot de passe.");
            }
        });

        H2 title = new H2("Créer un compte");
        title.setWidth("100%");

        this.add(title, this.prénom, this.nom, this.postalcode, this.email, this.pw, this.verify, this.send);

        this.setColspan(title, 2);
    }

    /**
     * *
     * Let create a bid and the create an account
     *
     * @param main
     * @param c
     * @param articleID
     * @param value
     */
    public CreateAccount(VuePrincipale main, Component c, int articleID, int value) {
        this.prénom = new TextField("Quel est votre prénom ?");
        this.nom = new TextField("Quel est votre nom ?");
        this.nom.setRequired(true);
        this.postalcode = new TextField("Quel est votre code postal ?");
        this.email = new EmailField("Quel est votre adresse mail ?");
        this.pw = new PasswordField("Veuillez choisir un mot de passe :");
        this.pw.setRequired(true);
        this.verify = new PasswordField("Veuillez confirmer votre mot de passe :");
        this.verify.setRequired(true);
        this.send = new Button("Valider");

        this.send.addClickListener((event) -> {
            if (this.pw.getValue().equals(this.verify.getValue())) {
                Notification.show("Votre compte va être créé.");
                String[] user = new String[5];
                user[0] = this.nom.getValue();
                user[1] = this.prénom.getValue();
                user[2] = this.email.getValue();
                user[3] = this.pw.getValue();
                user[4] = this.postalcode.getValue();
                try {
                    main.getSession().setUser(bdd.addUser(main.getSession().getCon(), user));
                    bdd.addBid(main.getSession().getCon(), main.getSession().getUser(), articleID, value);
                    main.setEntete(new Welcome_entete(main));
                    main.setPrincipal(c);
                } catch (SQLException ex) {
                    Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                    Notification.show("il y a une erreur de BdD");
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Notification.show("Il y a une erreur dans votre mot de passe.");
            }
        });

        H2 title = new H2("Créer un compte");
        title.setWidth("100%");

        this.add(title, this.prénom, this.nom, this.postalcode, this.email, this.pw, this.verify, this.send);

        this.setColspan(title, 2);

    }
}

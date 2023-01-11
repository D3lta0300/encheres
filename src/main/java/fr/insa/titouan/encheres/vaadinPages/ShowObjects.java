/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.titouan.encheres.bdd;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.insa.titouan.encheres.objects.Object;
import java.util.Set;

/**
 *
 * @author Titouan
 */
public class ShowObjects extends VerticalLayout {
    
    ObjectList objects;
    
    public ShowObjects(VuePrincipale main) {
        this.add(new H3("Liste des enchères"));
        Button validate = new Button ("Valider");
        validate.addClickListener((event) -> {
            Set<Object> selected = objects.getSelectedItems();
            for (Object select : selected){
                Notification.show("L'objet d'id " + select.getId() + " a été sélectionné.");
            }
        });
        this.add(validate);
        try {
            this.objects = new ObjectList(bdd.showObjects(main.getSession().getCon()));
            this.add(this.objects);
        } catch (SQLException ex) { 
            Logger.getLogger(ShowBids.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

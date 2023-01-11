/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.titouan.encheres.bdd;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class ShowBids extends VerticalLayout{
    
    public ShowBids(VuePrincipale main){
        this.add(new H3("Liste des enchères"));
        try {
            this.add(new BidList(bdd.showBids(main.getSession().getCon())));
        } catch (SQLException ex) {
            Logger.getLogger(ShowBids.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

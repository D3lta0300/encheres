/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author Titouan
 */
public class Welcome_entete extends HorizontalLayout {
    private Label titre;
    private VuePrincipale main;
    
    public Welcome_entete(VuePrincipale main){
        this.main = main;
        
        this.titre = new Label("PN - Enchères");
        this.add(this.titre);
        
        Button connect = new Button("connect");
        connect.addClickListener((event) -> {
            Notification.show("tout casser !!!");
        });
        this.add(connect);
    }
}
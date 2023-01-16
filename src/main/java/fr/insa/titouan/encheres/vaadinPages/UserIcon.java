/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *
 * @author Titouan
 */
public class UserIcon extends VerticalLayout {

    private int state;

    public UserIcon(VuePrincipale main) {
        state = main.getSession().getUser();
        this.add(new Avatar());
        Label text;
        if (this.state == -1) {
            text = new Label("Connexion");
        } else {
            text = new Label("Not done yet");
        }
        this.add(text);

        this.addClickListener((event) -> {
            if (this.state == -1) {
                main.setPrincipal(new LogPage(main, new VerticalLayout()));
            } else {
                Notification.show("Not done yet.");
            }
        });
        
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}

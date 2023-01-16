/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author Titouan
 */
public class LogPage extends HorizontalLayout{
    public LogPage(VuePrincipale main){
        this.add(new ConnectionPage(main), new CreateAccount(main));
    }
}

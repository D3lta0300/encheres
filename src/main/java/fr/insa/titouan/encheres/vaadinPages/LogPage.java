/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author Titouan
 */
public class LogPage extends HorizontalLayout{
    public LogPage(VuePrincipale main, Component c){
        this.add(new ConnectionPage(main, c), new CreateAccount(main, c));
    }
}

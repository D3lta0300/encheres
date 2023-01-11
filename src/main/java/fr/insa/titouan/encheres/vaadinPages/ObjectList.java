/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import fr.insa.titouan.encheres.objects.Object;


/**
 *
 * @author Titouan
 */
public class ObjectList extends Grid<Object>{
    private List<Object> objects;
    
    public ObjectList(List<Object> objects){
        this.objects = objects;
        
        Grid.Column<Object> id = this.addColumn(Object::getId).setHeader("ID");
        Grid.Column<Object> title = this.addColumn(Object::getTitle).setHeader("Titre");
        Grid.Column<Object> from = this.addColumn(Object::getFrom_user).setHeader("Proposé par ");
        Grid.Column<Object> highest_bid = this.addColumn(Object::getHighest_bid).setHeader("Enchère la plus haute (€)");
        
        this.setItems(this.objects);
        
        this.setSelectionMode(SelectionMode.SINGLE);
        
    }
}

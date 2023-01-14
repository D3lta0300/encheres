/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import fr.insa.titouan.encheres.objects.Bid;

/**
 *
 * @author Titouan
 */
public class BidList extends Grid<Bid> {

    private List<Bid> bids;
    
    public BidList(List<Bid> bids){
        this.bids = bids;
        
        Column<Bid> nom_complet = this.addColumn(Bid::getNom_complet).setHeader("Enchère de");
        Column<Bid> object = this.addColumn(Bid::getObject).setHeader("Sur l'objet");
        Column<Bid> values = this.addColumn(Bid::getValue).setHeader("Pour une valeur de (€)    ");
        Column<Bid> time = this.addColumn(Bid::getTime).setHeader("Réalisée à");
        
        this.setItems(this.bids);
        
    }
    
    public void addBid(Bid bid){
        this.bids.add(bid);
        this.setItems(this.bids);
    }
    
    public void setBids(List<Bid> bids){
        this.bids = bids;
        this.setItems(this.bids);
    }
}

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
        
        Column<Bid> uIds = this.addColumn(Bid::getuId).setHeader("User Id");
        Column<Bid> oIds = this.addColumn(Bid::getoId).setHeader("Object Id");
        Column<Bid> values = this.addColumn(Bid::getValue).setHeader("Value");
        Column<Bid> time = this.addColumn(Bid::getTime).setHeader("Time");
        
        this.setItems(this.bids);
        
    }
}

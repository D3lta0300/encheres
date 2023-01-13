/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import fr.insa.titouan.encheres.objects.Article;


/**
 *
 * @author Titouan
 */
public class ArticleList extends Grid<Article>{
    private List<Article> objects;
    
    public ArticleList(List<Article> objects){
        this.objects = objects;
        
        Grid.Column<Article> id = this.addColumn(Article::getId).setHeader("ID");
        Grid.Column<Article> title = this.addColumn(Article::getTitle).setHeader("Titre");
        Grid.Column<Article> from = this.addColumn(Article::getAuthor).setHeader("Proposé par ");
        Grid.Column<Article> highest_bid = this.addColumn(Article::getHighest_bid).setHeader("Enchère la plus haute (€)");
        
        this.setItems(this.objects);
        
        this.setSelectionMode(SelectionMode.SINGLE);
        
    }
}

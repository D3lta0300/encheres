/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.titouan.encheres.bdd;
import fr.insa.titouan.encheres.objects.Article;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class ArticlePage extends VerticalLayout {
    private Article article;
    
    public ArticlePage(int id, VuePrincipale main) throws SQLException{
        try {
            this.article = bdd.getArticle(id, main.getSession().getCon());
            
            H1 title = new H1(article.getTitle());
            Html description = new Html("<p>" + article.getDescription() + "</p>");
            Details details = new Details("Description",description);
            
            this.add(title, details);
        } catch (SQLException ex) {
            Logger.getLogger(ArticlePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

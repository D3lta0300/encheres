/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.titouan.encheres.bdd;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.insa.titouan.encheres.objects.Article;
import java.util.Set;

/**
 *
 * @author Titouan
 */
public class ShowArticles extends VerticalLayout {
    
    ArticleList articles;
    
    public ShowArticles(VuePrincipale main) throws SQLException {
        this.add(new H3("Liste des articles"));
        
        Button validate = new Button ("Sélectionner");
        validate.addClickListener((event) -> {
            Set<Article> selected = articles.getSelectedItems();
            for (Article select : selected){
                try {
                    Notification.show("L'objet d'id " + select.getId() + " a été sélectionné.");
                    main.setPrincipal(new ArticlePage(select.getId(),main));
                } catch (SQLException ex) {
                    Logger.getLogger(ShowArticles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.add(validate);
        
        try {
            this.articles = new ArticleList(bdd.showArticles(main.getSession().getCon()));
            this.add(this.articles);
        } catch (SQLException ex) { 
            Logger.getLogger(ShowBids.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
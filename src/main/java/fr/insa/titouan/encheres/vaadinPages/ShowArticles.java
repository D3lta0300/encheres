/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.titouan.encheres.bdd;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.insa.titouan.encheres.objects.Article;
import fr.insa.titouan.encheres.objects.Categorie;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Titouan
 */
public class ShowArticles extends VerticalLayout {

    ArticleList articles;

    public ShowArticles(VuePrincipale main, String search) {
        this.add(new H3("Liste des articles"));
        Button validate = new Button("Sélectionner");
        validate.addClickListener((event) -> {
            Set<Article> selected = articles.getSelectedItems();
            for (Article select : selected) {
                Notification.show("L'objet d'id " + select.getId() + " a été sélectionné.");
                main.setPrincipal(new ArticlePage(select.getId(), main));
            }
        });
        
        ComboBox categories = new ComboBox("Catégories");
        try {
            ArrayList<Categorie> a = bdd.getCategories(main.getSession().getCon());
            String[] b = new String[a.size()+1];
            for (int i = 0; i < b.length-1; i++) {
                b[i] = a.get(i).toString();
            }
            b[a.size()] = "";
            categories.setItems((Object[]) b);
        } catch (SQLException ex) {
            Logger.getLogger(CreateArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
        HorizontalLayout container = new HorizontalLayout(validate,categories);
        container.setAlignItems(Alignment.END);
        try {
            this.articles = new ArticleList(bdd.showArticles(main.getSession().getCon(), search));
            this.add(container,this.articles);
        } catch (SQLException ex) {
            Logger.getLogger(ShowBids.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

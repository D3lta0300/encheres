/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import fr.insa.titouan.encheres.bdd;
import fr.insa.titouan.encheres.objects.Article;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Titouan
 */
public class ArticlePage extends HorizontalLayout {
    private Article article;
    private VerticalLayout left;
    private VerticalLayout right;
    
    public ArticlePage(int id, VuePrincipale main) throws SQLException{
        try {
            this.setSizeFull();
            this.article = bdd.getArticle(id, main.getSession().getCon());
            this.left = new VerticalLayout();
            this.right = new VerticalLayout();
            this.right.setWidth("50%");
            this.left.setWidth("50%");          
            this.add(this.left, this.right);
            
            H1 title = new H1(article.getTitle());
            VerticalLayout titleAndImage = new VerticalLayout(title,bdd.getArticleImage(id, main.getSession().getCon()));
            Html author = new Html("<P> Cet article est vendu par " + article.getAuthor() + "</p>");
            Html description = new Html("<p>" + article.getDescription() + "</p>");
            Details details = new Details("Description",description);
            
            this.left.add(titleAndImage,author, details);
            
            IntegerField bid = new IntegerField("Quel prix proposé vous ?");
            bid.setClearButtonVisible(true);
            Div euro = new Div();
            euro.setText("€");
            bid.setSuffixComponent(euro);
            bid.setWidth("90%");
            bid.setMin(this.article.getHighest_bid());
            Button valider = new Button("Soummetre");
            valider.setWidth("50%");
            HorizontalLayout new_bid = new HorizontalLayout(bid, valider);
            new_bid.setDefaultVerticalComponentAlignment(Alignment.END);
            
            BidList bidlist = new BidList(bdd.showBidsOnObject(id, main.getSession().getCon()));
            bidlist.setWidth("100%");
            this.right.add(new_bid,bidlist);
            
            valider.addClickListener((event) -> {
                try {bdd.addBid(main.getSession().getCon(), main.getSession().getUser(), id, bid.getValue());
                    bidlist.setBids(bdd.showBidsOnObject(id, main.getSession().getCon()));
                } catch (SQLException ex) {
                    Logger.getLogger(ArticlePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(ArticlePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
    Copyright 2000- Francois de Bertrand de Beuvron

    This file is part of CoursBeuvron.

    CoursBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CoursBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.titouan.encheres.vaadinPages;

import fr.insa.titouan.encheres.objects.Session;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.titouan.encheres.bdd;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * vue principale. pour les composants vaadin, voir
 * https://vaadin.com/docs/latest/ds/components
 *
 * @author francois
 */
@Route(value = "")
@PageTitle("Enchères")
public class VuePrincipale extends VerticalLayout {

    private HorizontalLayout entete;
    private VerticalLayout principal;
    private Session session;

    public void setEntete(Component c) {
        this.entete.removeAll();
        this.entete.add(c);
    }

    public void setPrincipal(Component c) {
        this.principal.removeAll();
        this.principal.add(c);
    }
    
    public Session getSession(){
        return this.session;
    }
    public void setUser(int id){
        this.session.setUser(id);
    }

    public VuePrincipale() {
        this.session = new Session();
        
        this.entete = new HorizontalLayout();
        this.entete.setWidthFull();
        this.entete.setHeight("4em");

        this.principal = new VerticalLayout();
        this.principal.setWidthFull();
        this.principal.setHeightFull();

        this.add(this.entete, this.principal);

        try {
            session.setCon(bdd.defaultConnect());
            this.setEntete(new Welcome_entete(this));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VuePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            Notification.show("Something went wrong");
        } catch (SQLException ex) {
            Logger.getLogger(VuePrincipale.class.getName()).log(Level.SEVERE, null, ex);
            Notification.show("Vous n'avez pas accès à la base de donnée");
        }
    }

}

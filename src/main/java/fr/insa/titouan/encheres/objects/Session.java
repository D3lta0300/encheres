/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.objects;

import java.sql.Connection;

/**
 *
 * @author Titouan
 */
public class Session {
    private int user;
    private Connection con;
    
    public Session(){
        this.user = -1;
        this.con = null;
    }
    
    public Connection getCon(){
        return this.con;
    }
    
    public void setCon(Connection con){
        this.con = con;
    }
    
    public void setUser(int id){
        this.user = id;
    }
    
    public int getUser(){
        return this.user;
    }
}

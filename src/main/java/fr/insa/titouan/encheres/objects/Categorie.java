/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.objects;

/**
 *
 * @author magic
 */
public class Categorie {
    public int id;
    public String Name;
    
    public Categorie (int id, String name){
        this.id=id;
        this.Name=name;
    }
    public String toString(){
        return ""+this.id+"."+this.Name;
    }
}

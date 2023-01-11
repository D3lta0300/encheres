/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.objects;

import java.sql.Timestamp;

/**
 *
 * @author Titouan
 */
public class Bid {
    private int value;
    private Timestamp time;
    private String nom_complet;
    private String object;

    public Bid(int value, Timestamp time, String nom_complet, String object) {
        this.value = value;
        this.time = time;
        this.nom_complet = nom_complet;
        this.object = object;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getNom_complet() {
        return nom_complet;
    }

    public void setNom_complet(String nom_complet) {
        this.nom_complet = nom_complet;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}

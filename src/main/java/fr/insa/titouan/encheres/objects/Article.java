/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.objects;

/**
 *
 * @author Titouan
 */
public class Article {
    private int id;
    private String title;
    private String author;
    private int highest_bid;
    private String description;

    public Article(int id, String title, String Author, int highest_bid) {
        this.id = id;
        this.title = title;
        this.author = Author;
        this.highest_bid = highest_bid;
        this.description = "";
    }
    
    public Article(int id, String title, String Author, int highest_bid, String description) {
        this.id = id;
        this.title = title;
        this.author = Author;
        this.highest_bid = highest_bid;
        this.description = description;
    }
    
    public Article() {
        this.id = -1;
        this.title = "";
        this.author = "";
        this.highest_bid = -1;
        this.description = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getHighest_bid() {
        return highest_bid;
    }

    public void setHighest_bid(int highest_bid) {
        this.highest_bid = highest_bid;
    }
    
    
}

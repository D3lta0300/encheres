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
    private int uId;
    private int oId;
    private int value;
    private Timestamp time;

    public Bid(int uId, int oId, int value, Timestamp time) {
        this.uId = uId;
        this.oId = oId;
        this.value = value;
        this.time = time;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
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

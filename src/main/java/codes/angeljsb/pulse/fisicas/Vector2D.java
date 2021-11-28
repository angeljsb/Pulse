/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse.fisicas;

/**
 *
 * @author Angel
 */
public class Vector2D {
    
    public double x, y;
    
    public Vector2D () {
        this.x = this.y = 0;
    }
    
    public Vector2D (double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public Double getX() {
        return x;
    }
    
    public Double getY() {
        return y;
    }
    
    public void sumar(Vector2D otro) {
        this.sumar(otro.x, otro.y);
    }
    
    public void sumar(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
    @Override
    public String toString() {
        return "x: " + x + ",y: " + y;
    }
}

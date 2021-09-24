package com.example.vendeur.carte;
//Point coordinates from the map
public class PointMap {
    private double x;
    private double y;
    PointMap(){
        this.x= (double) 0.0;
        this.y=(double)0.0;
    }

    public PointMap(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

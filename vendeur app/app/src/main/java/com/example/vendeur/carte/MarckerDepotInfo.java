package com.example.vendeur.carte;
//this class contains all the information of the Marcher depot
public class MarckerDepotInfo {
    PointMap pointMap;
    String nomDepot;
    String numTeleDepot;

    public int getIdDepot() {
        return idDepot;
    }

    public void setIdDepot(int idDepot) {
        this.idDepot = idDepot;
    }

    int idDepot;

    public MarckerDepotInfo() {
        this.pointMap = new PointMap();
        this.nomDepot = "";
        this.numTeleDepot = "";
        this.idDepot=-1;
    }

    public MarckerDepotInfo(PointMap pointMap, String nomDepot, String numTeleDepot,int idDepot) {
        this.pointMap = pointMap;
        this.nomDepot = nomDepot;
        this.numTeleDepot = numTeleDepot;

    }
    public MarckerDepotInfo(PointMap pointMap, String nomDepot, String numTeleDepot) {
        this.pointMap = pointMap;
        this.nomDepot = nomDepot;
        this.numTeleDepot = numTeleDepot;

    }

    public PointMap getPointMap() {
        return pointMap;
    }

    public String getNomDepot() {
        return nomDepot;
    }

    public String getNumTeleDepot() {
        return numTeleDepot;
    }

    public void setPointMap(PointMap pointMap) {
        this.pointMap = pointMap;
    }

    public void setNomDepot(String nomDepot) {
        this.nomDepot = nomDepot;
    }

    public void setNumTeleDepot(String numTeleDepot) {
        this.numTeleDepot = numTeleDepot;
    }
}

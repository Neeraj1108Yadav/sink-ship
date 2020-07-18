package com.example.sinktheship;

import android.util.Log;

import java.util.ArrayList;

public class PresenterShip implements ShipListener.ShipPresenter {

    private ShipListener.ShipView shipView;
    private int shipCount,maxShip,countDays=0;
    private ArrayList<Integer> pirateShip = new ArrayList<>();

    public PresenterShip(ShipListener.ShipView shipView) {
        this.shipView = shipView;
    }

    @Override
    public void inputMaxPirateShip(int maxShip) {
        this.maxShip = maxShip;
        shipView.onSetMaxShip();
    }

    @Override
    public void enterShipLoot(int shipLoot) {
        ++shipCount;
        pirateShip.add(shipLoot);
        shipView.onSetShipLoot(shipCount,shipLoot,maxShip);
    }

    @Override
    public void startWar() {
        checkLoot();
    }

    @Override
    public void reset() {
        shipCount=0;
        maxShip=0;
        countDays=0;
        pirateShip.clear();
    }

    private void checkLoot(){
        ArrayList<Integer> removeShip = new ArrayList<>();
        for(int i=0;i<pirateShip.size();i++){
            if(i != pirateShip.size()-1){
                if(pirateShip.get(i) < pirateShip.get(i+1)) removeShip.add(pirateShip.get(i+1));
            }
        }

        if(removeShip.size() !=0){
            ++countDays;
            pirateShip.removeAll(removeShip);
            /*for(int i=0;i<pirateShip.size();i++){
                Log.i("SHIP",String.valueOf(pirateShip.get(i)));
            }*/
            startWar();
        }else{
            Log.i("SHIP","DAYS = "+countDays);
            shipView.getWarResult(countDays);
        }
    }
}

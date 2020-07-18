package com.example.sinktheship;

public interface ShipListener {

    interface ShipPresenter{
        void inputMaxPirateShip(int maxShip);
        void enterShipLoot(int shipLoot);
        void startWar();
        void reset();
    }

    interface ShipView{
        void onSetMaxShip();
        void onSetShipLoot(int shipCount,int shipLoot,int maxShip);
        void getWarResult(int resultDays);
    }
}

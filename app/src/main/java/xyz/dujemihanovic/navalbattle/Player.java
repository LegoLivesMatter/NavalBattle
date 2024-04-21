package xyz.dujemihanovic.navalbattle;

public class Player {
    private int gunboatLeft;
    private int destroyerLeft;
    private int battleshipLeft;
    private int carrierLeft;

    protected void Player() {
        gunboatLeft = 2;
        destroyerLeft = 3;
        battleshipLeft = 4;
        carrierLeft = 5;
    }

    protected boolean lost() {
        return gunboatLeft == 0 && destroyerLeft == 0 && battleshipLeft == 0 && carrierLeft == 0;
    }

    protected int getGunboatLeft() {
        return gunboatLeft;
    }

    protected int getDestroyerLeft() {
        return destroyerLeft;
    }

    protected int getBattleshipLeft() {
        return battleshipLeft;
    }

    protected int getCarrierLeft() {
        return carrierLeft;
    }

    protected void hitGunboat() {
        if (gunboatLeft > 0) gunboatLeft--;
        else System.err.println("Tried to hit already sunken ship!");
    }

    protected void hitDestroyer() {
        if (destroyerLeft > 0) destroyerLeft--;
        else System.err.println("Tried to hit already sunken ship!");
    }

    protected void hitBattleship() {
        if (battleshipLeft > 0) battleshipLeft--;
        else System.err.println("Tried to hit already sunken ship!");
    }

    protected void hitCarrier() {
        if (carrierLeft > 0) carrierLeft--;
        else System.err.println("Tried to hit already sunken ship!");
    }
}

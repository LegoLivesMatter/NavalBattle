package xyz.dujemihanovic.navalbattle;

import android.widget.TextView;

import androidx.gridlayout.widget.GridLayout;

public class Player {
    private int gunboatLeft;
    private int destroyerLeft;
    private int battleshipLeft;
    private int carrierLeft;
    private int shipsPlaced;
    private GridLayout grid;

    public Player(GridLayout grid) {
        gunboatLeft = 2;
        destroyerLeft = 3;
        battleshipLeft = 4;
        carrierLeft = 5;
        shipsPlaced = 0;
        this.grid = grid;
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
    protected int getShipsPlaced() { return shipsPlaced; }
    protected TextView getCell(int i) { return grid.findViewById(i); }

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

    protected void addTv(TextView tv) { grid.addView(tv); }

    protected boolean placeShip(int i) {
        switch (getShipsPlaced()) {
            case 0:
                if (i / 8 < (i + 1) / 8)
                    return true;
                else {
                    TextView one = grid.findViewById(i);
                    TextView two = grid.findViewById(i+1);

                    one.setText("1");
                    two.setText("1");
                }
                break;
            case 1:
                if (i / 8 < (i + 2) / 8)
                    return true;
                else {
                    TextView one = grid.findViewById(i);
                    TextView two = grid.findViewById(i+1);
                    TextView three = grid.findViewById(i+2);

                    one.setText("2");
                    two.setText("2");
                    three.setText("2");
                }
                break;
            case 2:
                if (i / 8 < (i + 3) / 8)
                    return true;
                else {
                    TextView one = grid.findViewById(i);
                    TextView two = grid.findViewById(i+1);
                    TextView three = grid.findViewById(i+2);
                    TextView four = grid.findViewById(i+3);

                    one.setText("3");
                    two.setText("3");
                    three.setText("3");
                    four.setText("3");
                }
                break;
            case 3:
                if (i / 8 < (i + 4) / 8)
                    return true;
                else {
                    TextView one = grid.findViewById(i);
                    TextView two = grid.findViewById(i+1);
                    TextView three = grid.findViewById(i+2);
                    TextView four = grid.findViewById(i+3);
                    TextView five = grid.findViewById(i+4);

                    one.setText("4");
                    two.setText("4");
                    three.setText("4");
                    four.setText("4");
                    five.setText("4");
                }
                break;
            default:
                return true;
        }
        shipsPlaced++;
        return false;
    }
}

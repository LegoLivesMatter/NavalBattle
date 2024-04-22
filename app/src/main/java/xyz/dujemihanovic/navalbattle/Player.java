package xyz.dujemihanovic.navalbattle;

import android.widget.TextView;

import androidx.gridlayout.widget.GridLayout;

public class Player {
    private int gunboatLeft;
    private int destroyerLeft;
    private int battleshipLeft;
    private int carrierLeft;
    private int shipsPlaced;
    private final GridLayout grid;

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

    protected int getGunboatLeft() { return gunboatLeft; }

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

    /**
     * Shoot at this player's field.
     * @param i The ID of the hit TextView
     * @return HitResult.HIT if hit, destruction, MISS if miss, INVALID if tried to shoot at already shot field
     */
    protected HitResult shoot(int i) {
        TextView hit = grid.findViewById(i);

        switch (hit.getText().toString()) {
            case "1":
                hitGunboat();
                hit.setText("D");
                if (getGunboatLeft() == 0) return HitResult.GUNBOAT_DESTROYED;
                return HitResult.HIT;
            case "2":
                hitDestroyer();
                hit.setText("D");
                if (getDestroyerLeft() == 0) return HitResult.DESTROYER_DESTROYED;
                return HitResult.HIT;
            case "3":
                hitBattleship();
                hit.setText("D");
                if (getBattleshipLeft() == 0) return HitResult.BATTLESHIP_DESTROYED;
                return HitResult.HIT;
            case "4":
                hitCarrier();
                hit.setText("D");
                if (getCarrierLeft() == 0) return HitResult.CARRIER_DESTROYED;
                return HitResult.HIT;
            case "B":
                hit.setText("M");
                return HitResult.MISS;
            default:
                return HitResult.INVALID;
        }
    }

    protected void addTv(TextView tv) { grid.addView(tv); }

    protected boolean placeShip(int i) {
        switch (getShipsPlaced()) {
            case 0: {
                if (i / 8 < (i + 1) / 8)
                    return true;

                TextView one = grid.findViewById(i);
                TextView two = grid.findViewById(i + 1);

                if (one.getText() != "B" || two.getText() != "B")
                    return true;

                one.setText("1");
                two.setText("1");
                break;
            }
            case 1: {
                if (i / 8 < (i + 2) / 8)
                    return true;

                TextView one = grid.findViewById(i);
                TextView two = grid.findViewById(i + 1);
                TextView three = grid.findViewById(i + 2);

                if (one.getText() != "B" || two.getText() != "B" || three.getText() != "B")
                    return true;

                one.setText("2");
                two.setText("2");
                three.setText("2");
                break;
            }
            case 2: {
                if (i / 8 < (i + 3) / 8)
                    return true;

                TextView one = grid.findViewById(i);
                TextView two = grid.findViewById(i+1);
                TextView three = grid.findViewById(i+2);
                TextView four = grid.findViewById(i+3);

                if (one.getText() != "B" || two.getText() != "B" || three.getText() != "B" || four.getText() != "B")
                    return true;

                one.setText("3");
                two.setText("3");
                three.setText("3");
                four.setText("3");
                break;
            }
            case 3: {
                if (i / 8 < (i + 4) / 8)
                    return true;

                TextView one = grid.findViewById(i);
                TextView two = grid.findViewById(i+1);
                TextView three = grid.findViewById(i+2);
                TextView four = grid.findViewById(i+3);
                TextView five = grid.findViewById(i+4);

                if (one.getText() != "B" || two.getText() != "B" || three.getText() != "B" || four.getText() != "B" || five.getText() != "B")
                    return true;

                one.setText("4");
                two.setText("4");
                three.setText("4");
                four.setText("4");
                five.setText("4");
                break;
            }
            default:
                return true;
        }
        shipsPlaced++;
        return false;
    }
}

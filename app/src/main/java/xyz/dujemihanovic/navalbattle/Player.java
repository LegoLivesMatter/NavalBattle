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
    private final char[] board;
    private static final int g = GameActivity.gridSize;

    public Player(GridLayout grid) {
        gunboatLeft = 2;
        destroyerLeft = 3;
        battleshipLeft = 4;
        carrierLeft = 5;
        shipsPlaced = 0;
        board = new char[g * g];
        for (int i = 0; i < g * g; i++) {
            board[i] = 'B';
        }
        this.grid = grid;
        this.grid.setColumnCount(g);
        this.grid.setRowCount(g);
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

    protected int getShipsPlaced() {
        return shipsPlaced;
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

    /**
     * Reveal a ship.
     *
     * @param ship The number of the ship to reveal
     */
    private void revealShip(char ship) {
        for (int i = 0; i < g * g; i++)
            if (board[i] == ship) {
                TextView v = grid.findViewById(i);
                v.setText(String.valueOf(ship));
            }
    }

    /**
     * Shoot at this player's field.
     *
     * @param i The ID of the hit TextView
     * @return HitResult.HIT if hit, destruction, MISS if miss, INVALID if tried to shoot at already shot field
     */
    protected HitResult shoot(int i) {
        TextView v = grid.findViewById(i);

        switch (board[i]) {
            case '1':
                hitGunboat();
                v.setText("D");
                if (getGunboatLeft() != 0) return HitResult.HIT;
                revealShip('1');
                return HitResult.GUNBOAT_DESTROYED;
            case '2':
                hitDestroyer();
                v.setText("D");
                if (getDestroyerLeft() != 0) return HitResult.HIT;
                revealShip('2');
                return HitResult.DESTROYER_DESTROYED;
            case '3':
                hitBattleship();
                v.setText("D");
                if (getBattleshipLeft() != 0) return HitResult.HIT;
                revealShip('3');
                return HitResult.BATTLESHIP_DESTROYED;
            case '4':
                hitCarrier();
                v.setText("D");
                if (getCarrierLeft() != 0) return HitResult.HIT;
                return HitResult.CARRIER_DESTROYED;
            case 'B':
                v.setText("M");
                return HitResult.MISS;
            default:
                return HitResult.INVALID;
        }
    }

    protected void addTv(TextView tv) {
        grid.addView(tv);
    }

    private boolean placeShipHorizontal(int i) {
        switch (getShipsPlaced()) {
            case 0: {
                if (i / g < (i + 1) / g)
                    return true;

                if (board[i] != 'B' || board[i + 1] != 'B')
                    return true;

                board[i] = '1';
                board[i + 1] = '1';

                for (int j = i; j < i + 2; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("1");
                }
                break;
            }
            case 1: {
                if (i / g < (i + 2) / g)
                    return true;

                if (board[i] != 'B' || board[i + 1] != 'B' || board[i + 2] != 'B')
                    return true;

                board[i] = '2';
                board[i + 1] = '2';
                board[i + 2] = '2';

                for (int j = i; j < i + 3; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("2");
                }
                break;
            }
            case 2: {
                if (i / g < (i + 3) / g)
                    return true;

                if (board[i] != 'B' || board[i + 1] != 'B' || board[i + 2] != 'B' || board[i + 3] != 'B')
                    return true;

                board[i] = '3';
                board[i + 1] = '3';
                board[i + 2] = '3';
                board[i + 3] = '3';

                for (int j = i; j < i + 4; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("3");
                }
                break;
            }
            case 3: {
                if (i / g < (i + 4) / g)
                    return true;

                if (board[i] != 'B' || board[i + 1] != 'B' || board[i + 2] != 'B' || board[i + 3] != 'B' || board[i + 4] != 'B')
                    return true;

                board[i] = '4';
                board[i + 1] = '4';
                board[i + 2] = '4';
                board[i + 3] = '4';
                board[i + 4] = '4';

                for (int j = 0; j < g * g; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("?");
                }
                break;
            }
            default:
                return true;
        }
        shipsPlaced++;
        return false;
    }

    private boolean placeShipVertical(int i) {
        switch (getShipsPlaced()) {
            case 0: {
                if (i + g > g * g)
                    return true;

                if (board[i] != 'B' || board[i + g] != 'B')
                    return true;

                board[i] = '1';
                board[i + g] = '1';

                for (int j = i; j < i + 2 * g; j += g) {
                    TextView v = grid.findViewById(j);
                    v.setText("1");
                }
                break;
            }
            case 1: {
                if (i + 2 * g > g * g)
                    return true;

                if (board[i] != 'B' || board[i + g] != 'B' || board[i + 2 * g] != 'B')
                    return true;

                board[i] = '2';
                board[i + g] = '2';
                board[i + 2 * g] = '2';

                for (int j = i; j < i + 3 * g; j += g) {
                    TextView v = grid.findViewById(j);
                    v.setText("2");
                }
                break;
            }
            case 2: {
                if (i + 3 * g > g * g)
                    return true;

                if (board[i] != 'B' || board[i + g] != 'B' || board[i + 2 * g] != 'B' || board[i + 3 * g] != 'B')
                    return true;

                board[i] = '3';
                board[i + g] = '3';
                board[i + 2 * g] = '3';
                board[i + 3 * g] = '3';

                for (int j = i; j < i + 4 * g; j += g) {
                    TextView v = grid.findViewById(j);
                    v.setText("3");
                }
                break;
            }
            case 3: {
                if (i + 4 * g > g * g)
                    return true;

                if (board[i] != 'B' || board[i + g] != 'B' || board[i + 2 * g] != 'B' || board[i + 3 * g] != 'B' || board[i + 4 * g] != 'B')
                    return true;

                board[i] = '4';
                board[i + g] = '4';
                board[i + 2 * g] = '4';
                board[i + 3 * g] = '4';
                board[i + 4 * g] = '4';

                for (int j = 0; j < g * g; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("?");
                }
                break;
            }
            default:
                return true;
        }
        shipsPlaced++;
        return false;
    }

    /**
     * Place a ship at a field.
     *
     * @param i The ID of the TextView
     * @return true if the ship was placed, false otherwise
     */
    protected boolean placeShip(int i, boolean vertical) {
        if (vertical) return placeShipVertical(i);
        else return placeShipHorizontal(i);
    }
}

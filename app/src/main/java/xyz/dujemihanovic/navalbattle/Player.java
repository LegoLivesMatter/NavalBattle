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
    private final boolean isB;
    private static final int gridSize = GameActivity.gridSize;

    public Player(GridLayout grid, boolean isB) {
        gunboatLeft = 2;
        destroyerLeft = 3;
        battleshipLeft = 4;
        carrierLeft = 5;
        shipsPlaced = 0;
        board = new char[gridSize*gridSize];
        for (int i = 0; i < gridSize*gridSize; i++) {
            board[i] = 'B';
        }
        this.grid = grid;
        this.isB = isB;
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
        TextView v = grid.findViewById(i);

        if (i >= gridSize*gridSize) i -= gridSize*gridSize;

        switch (board[i]) {
            case '1':
                hitGunboat();
                board[i] = 'D';
                v.setText("D");
                if (getGunboatLeft() == 0) return HitResult.GUNBOAT_DESTROYED;
                return HitResult.HIT;
            case '2':
                hitDestroyer();
                board[i] = 'D';
                v.setText("D");
                if (getDestroyerLeft() == 0) return HitResult.DESTROYER_DESTROYED;
                return HitResult.HIT;
            case '3':
                hitBattleship();
                board[i] = 'D';
                v.setText("D");
                if (getBattleshipLeft() == 0) return HitResult.BATTLESHIP_DESTROYED;
                return HitResult.HIT;
            case '4':
                hitCarrier();
                board[i] = 'D';
                v.setText("D");
                if (getCarrierLeft() == 0) return HitResult.CARRIER_DESTROYED;
                return HitResult.HIT;
            case 'B':
                board[i] = 'M';
                v.setText("M");
                return HitResult.MISS;
            default:
                return HitResult.INVALID;
        }
    }

    protected void addTv(TextView tv) { grid.addView(tv); }

    /**
     * Place a ship at a field.
     * @param i The ID of the TextView
     * @return true if the ship was placed, false otherwise
     */
    protected boolean placeShip(int i) {
        switch (getShipsPlaced()) {
            case 0: {
                if (isB) i -= gridSize*gridSize;

                if (i / gridSize < (i + 1) / gridSize)
                    return true;

                if (board[i] != 'B' || board[i+1] != 'B')
                    return true;

                board[i] = '1';
                board[i+1] = '1';

                if (isB) i += gridSize*gridSize;
                for (int j = i; j < i+2; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("1");
                }
                break;
            }
            case 1: {
                if (isB) i -= gridSize*gridSize;

                if (i / gridSize < (i + 2) / gridSize)
                    return true;

                if (board[i] != 'B' || board[i+1] != 'B' || board[i+2] != 'B')
                    return true;

                board[i] = '2';
                board[i+1] = '2';
                board[i+2] = '2';

                if (isB) i += gridSize*gridSize;
                for (int j = i; j < i+3; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("2");
                }
                break;
            }
            case 2: {
                if (isB) i -= gridSize*gridSize;

                if (i / gridSize < (i + 3) / gridSize)
                    return true;

                if (board[i] != 'B' || board[i+1] != 'B' || board[i+2] != 'B' || board[i+3] != 'B')
                    return true;

                board[i] = '3';
                board[i+1] = '3';
                board[i+2] = '3';
                board[i+3] = '3';

                if (isB) i += gridSize*gridSize;
                for (int j = i; j < i+4; j++) {
                    TextView v = grid.findViewById(j);
                    v.setText("3");
                }
                break;
            }
            case 3: {
                if (isB) i -= gridSize*gridSize;

                if (i / gridSize < (i + 4) / gridSize)
                    return true;

                if (board[i] != 'B' || board[i+1] != 'B' || board[i+2] != 'B' || board[i+3] != 'B' || board[i+4] != 'B')
                    return true;

                board[i] = '4';
                board[i+1] = '4';
                board[i+2] = '4';
                board[i+3] = '4';
                board[i+4] = '4';

                if (isB)
                    for (int j = gridSize*gridSize; j < 2*gridSize*gridSize; j++) {
                        TextView v = grid.findViewById(j);
                        v.setText("?");
                    }
                else
                    for (int j = 0; j < gridSize*gridSize; j++) {
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
}

package xyz.dujemihanovic.navalbattle;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public static final int gridSize = 8;
    TextView status;
    Player a, b;
    ButtonAction current;
    MediaPlayer plr;
    boolean vsHuman;
    Random rand;

    private void beginPlaceShipsB() {
        current = ButtonAction.B_PLACING;
        status.setText(R.string.key_strPlrTwoPlaceShips);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        current = ButtonAction.NOTHING;

        a = new Player(findViewById(R.id.gridA));
        b = new Player(findViewById(R.id.gridB));

        rand = new Random();

        vsHuman = getIntent().getBooleanExtra("human", false);

        // create board dynamically with GridLayout, perhaps can be replaced with RecyclerView?
        status = findViewById(R.id.tvStatus);

        for (int i = 0; i < gridSize*gridSize; i++) {
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText("B");
            tv.setWidth(96);
            tv.setHeight(96);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(this::btnOnClick);
            tv.setOnLongClickListener(this::btnOnLongClick);
            a.addTv(tv);
        }
        for (int i = 0; i < gridSize*gridSize; i++) {
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText("B");
            tv.setWidth(96);
            tv.setHeight(96);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(this::btnOnClick);
            tv.setOnLongClickListener(this::btnOnLongClick);
            b.addTv(tv);
        }

        current = ButtonAction.A_PLACING;
    }

    private void playExp() {
        if (plr == null) {
            plr = MediaPlayer.create(this, R.raw.explosion);
        } else {
            plr.release();
            plr = MediaPlayer.create(this, R.raw.explosion);
        }
        plr.setOnCompletionListener(mp -> {
            plr.stop();
            plr.release();
            plr = null;
        });
        plr.start();
    }

    private void playSplash() {
        if (plr == null) {
            plr = MediaPlayer.create(this, R.raw.splash);
        } else {
            plr.release();
            plr = MediaPlayer.create(this, R.raw.splash);
        }
        plr.setOnCompletionListener(mp -> {
            plr.stop();
            plr.release();
            plr = null;
        });
        plr.start();
    }

    private boolean btnOnLongClick(View v) {
        final boolean isB = ((View) (v.getParent())).getId() == R.id.gridB;

        switch (current) {
            case A_PLACING:
                if (isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    break;
                }
                if (a.getShipsPlaced() < 4)
                    if (a.placeShip(v.getId(), true)) {
                        Toast.makeText(this, getString(R.string.key_strCantPlaceThere), Toast.LENGTH_SHORT).show();
                        break;
                    }
                if (a.getShipsPlaced() == 4) {
                    beginPlaceShipsB();
                    if (!vsHuman) {
                        while (b.getShipsPlaced() < 4)
                            b.placeShip(rand.nextInt(gridSize * gridSize), rand.nextBoolean());
                        current = ButtonAction.A_SHOOTING;
                        status.setText(R.string.key_strPlrOneShoot);
                    }
                }
                break;
            case B_PLACING:
                if (!isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    break;
                }
                if (b.getShipsPlaced() < 4)
                    if (b.placeShip(v.getId(), true)) {
                        Toast.makeText(this, getString(R.string.key_strCantPlaceThere), Toast.LENGTH_SHORT).show();
                        break;
                    }
                if (b.getShipsPlaced() == 4) {
                    current = ButtonAction.A_SHOOTING;
                    status.setText(getString(R.string.key_strPlrOneShoot));
                }
                break;
            default:
                Toast.makeText(this, R.string.key_strLongPressAfterPlace, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    // Most actual game logic is here (and in Player)
    private void btnOnClick(View v) {
        final boolean isB = ((View) (v.getParent())).getId() == R.id.gridB;

        switch (current) {
            case A_PLACING:
                if (isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (a.getShipsPlaced() < 4)
                    if (a.placeShip(v.getId(), false)) {
                        Toast.makeText(this, getString(R.string.key_strCantPlaceThere), Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (a.getShipsPlaced() == 4) {
                    beginPlaceShipsB();
                    if (!vsHuman) {
                        while (b.getShipsPlaced() < 4) b.placeShip(rand.nextInt(gridSize*gridSize), rand.nextBoolean());
                        current = ButtonAction.A_SHOOTING;
                        status.setText(R.string.key_strPlrOneShoot);
                    }
                }
                return;
            case B_PLACING:
                if (!isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (b.getShipsPlaced() < 4)
                    if (b.placeShip(v.getId(), false)) {
                        Toast.makeText(this, getString(R.string.key_strCantPlaceThere), Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (b.getShipsPlaced() == 4) {
                    current = ButtonAction.A_SHOOTING;
                    status.setText(getString(R.string.key_strPlrOneShoot));
                }
                return;
            case A_SHOOTING:
                if (!isB) return;
                switch (b.shoot(v.getId())) {
                    case CARRIER_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkCarrier), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case BATTLESHIP_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkBattleship), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case DESTROYER_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkDestroyer), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case GUNBOAT_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkGunboat), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case INVALID:
                        Toast.makeText(this, getString(R.string.key_strCantShoot), Toast.LENGTH_SHORT).show();
                        return;
                    case MISS:
                        current = ButtonAction.B_SHOOTING;
                        status.setText(R.string.key_strPlrTwoShoot);
                        playSplash();
                        break;
                    default:
                        playExp();
                }

                // bot logic is here
                if (!vsHuman && current == ButtonAction.B_SHOOTING) {
                    boolean keepShooting = true;
                    int next = rand.nextInt(gridSize*gridSize);
                    Direction direction = Direction.UNDETERMINED;

                    current = ButtonAction.A_SHOOTING;
                    status.setText(getString(R.string.key_strPlrTwoShoot));
                    while (keepShooting) {
                        if (plr != null)
                            while (plr.isPlaying());

                        switch (a.shoot(next)) {
                            case MISS:
                                keepShooting = false;
                                playSplash();
                                break;
                            case INVALID:
                                next = rand.nextInt(gridSize*gridSize);
                                break;
                            case CARRIER_DESTROYED:
                                next = rand.nextInt(gridSize*gridSize);
                                Toast.makeText(this, getString(R.string.key_strCarrierSunk), Toast.LENGTH_SHORT).show();
                                playExp();
                                break;
                            case BATTLESHIP_DESTROYED:
                                next = rand.nextInt(gridSize*gridSize);
                                Toast.makeText(this, getString(R.string.key_strBattleshipSunk), Toast.LENGTH_SHORT).show();
                                playExp();
                                break;
                            case DESTROYER_DESTROYED:
                                next = rand.nextInt(gridSize*gridSize);
                                Toast.makeText(this, getString(R.string.key_strDestroyerSunk), Toast.LENGTH_SHORT).show();
                                playExp();
                                break;
                            case GUNBOAT_DESTROYED:
                                next = rand.nextInt(gridSize*gridSize);
                                Toast.makeText(this, getString(R.string.key_strGunboatSunk), Toast.LENGTH_SHORT).show();
                                playExp();
                                break;
                            default:
                                if (direction == Direction.UNDETERMINED) direction = Direction.values()[rand.nextInt(Direction.values().length)];
                                switch (direction) {
                                    case UP:
                                        if (next - gridSize > 0) next -= gridSize;
                                        break;
                                    case DOWN:
                                        if (next + gridSize < gridSize*gridSize) next += gridSize;
                                        break;
                                    case LEFT:
                                        if (next > 0) next--;
                                        break;
                                    case RIGHT:
                                        if (next < gridSize*gridSize) next++;
                                }
                                playExp();
                        }
                    }
                    status.setText(getString(R.string.key_strPlrOneShoot));
                }
                break;
            case B_SHOOTING:
                if (isB) return;
                switch (a.shoot(v.getId())) {
                    case CARRIER_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkCarrier), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case BATTLESHIP_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkBattleship), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case DESTROYER_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkDestroyer), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case GUNBOAT_DESTROYED:
                        Toast.makeText(this, getString(R.string.key_strSunkGunboat), Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case INVALID:
                        Toast.makeText(this, getString(R.string.key_strCantShoot), Toast.LENGTH_SHORT).show();
                        return;
                    case MISS:
                        current = ButtonAction.A_SHOOTING;
                        status.setText(getString(R.string.key_strPlrOneShoot));
                        playSplash();
                        return;
                    default:
                        playExp();
                }
                break;
        }

        if (a.lost()) {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle(R.string.key_strGameOver);
            build.setMessage(vsHuman ? R.string.key_strCongratsTwo : R.string.key_strComputerWon);
            build.setPositiveButton(R.string.ok, (dialog, which) -> finish());
            build.show();
        } else if (b.lost()) {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle(R.string.key_strGameOver);
            build.setMessage(vsHuman ? R.string.key_strCongratsOne : R.string.key_strHumanWon);
            build.setPositiveButton(R.string.ok, (dialog, which) -> finish());
            build.show();
        }
    }
}
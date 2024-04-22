package xyz.dujemihanovic.navalbattle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    TextView status;
    Player a, b;
    ButtonAction current;
    MediaPlayer plr;

    private void beginPlaceShipsA() {
        current = ButtonAction.A_PLACING;
    }

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

        // create board dynamically with GridLayout, perhaps can be replaced with RecyclerView?
        status = findViewById(R.id.tvStatus);

        for (int i = 0; i < 64; i++) {
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText("B");
            tv.setWidth(96);
            tv.setHeight(96);
            tv.setOnClickListener(this::btnOnClick);
            a.addTv(tv);
        }
        for (int i = 64; i < 128; i++) {
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText("B");
            tv.setWidth(96);
            tv.setHeight(96);
            tv.setOnClickListener(this::btnOnClick);
            b.addTv(tv);
        }

        beginPlaceShipsA();
    }

    private void playExp() {
        if (plr == null) {
            plr = MediaPlayer.create(this, R.raw.explosion);
            plr.setOnCompletionListener(mp -> {
                plr.release();
                plr = null;
            });
            plr.start();
        } else {
            plr.release();
            plr = MediaPlayer.create(this, R.raw.explosion);
            plr.setOnCompletionListener(mp -> {
                plr.release();
                plr = null;
            });
            plr.start();
        }
    }

    private void playSplash() {
        if (plr == null) {
            plr = MediaPlayer.create(this, R.raw.splash);
            plr.setOnCompletionListener(mp -> {
                plr.release();
                plr = null;
            });
            plr.start();
        } else {
            plr.release();
            plr = MediaPlayer.create(this, R.raw.splash);
            plr.setOnCompletionListener(mp -> {
                plr.release();
                plr = null;
            });
            plr.start();
        }
    }

    // Most actual game logic is here (and in Player)
    private void btnOnClick(View v) {
        @SuppressLint("ResourceType") final boolean isB = v.getId() > 63;

        switch (current) {
            case A_PLACING:
                if (isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (a.getShipsPlaced() < 4)
                    if (a.placeShip(v.getId())) {
                        Toast.makeText(this, getString(R.string.key_strCantPlaceThere), Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (a.getShipsPlaced() == 4) beginPlaceShipsB();
                return;
            case B_PLACING:
                if (!isB) {
                    Toast.makeText(this, getString(R.string.key_strMustPlaceShipOnOwnBoard), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (b.getShipsPlaced() < 4)
                    if (b.placeShip(v.getId())) {
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
                        Toast.makeText(this, "You sunk a carrier!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case BATTLESHIP_DESTROYED:
                        Toast.makeText(this, "You sunk a battleship!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case DESTROYER_DESTROYED:
                        Toast.makeText(this, "You sunk a destroyer!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case GUNBOAT_DESTROYED:
                        Toast.makeText(this, "You sunk a gunboat!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case INVALID:
                        Toast.makeText(this, "Can't shoot there!", Toast.LENGTH_SHORT).show();
                        return;
                    case MISS:
                        current = ButtonAction.B_SHOOTING;
                        status.setText(R.string.key_strPlrTwoShoot);
                        playSplash();
                        return;
                    default:
                        playExp();
                }
                break;
            case B_SHOOTING:
                if (isB) return;
                switch (a.shoot(v.getId())) {
                    case CARRIER_DESTROYED:
                        Toast.makeText(this, "You sunk a carrier!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case BATTLESHIP_DESTROYED:
                        Toast.makeText(this, "You sunk a battleship!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case DESTROYER_DESTROYED:
                        Toast.makeText(this, "You sunk a destroyer!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case GUNBOAT_DESTROYED:
                        Toast.makeText(this, "You sunk a gunboat!", Toast.LENGTH_SHORT).show();
                        playExp();
                        break;
                    case INVALID:
                        Toast.makeText(this, "Can't shoot there!", Toast.LENGTH_SHORT).show();
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
            build.setTitle("Game over!");
            build.setMessage("Congratulations to B for winning!");
            build.setPositiveButton("OK", (dialog, which) -> finish());
            build.show();
        } else if (b.lost()) {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("Game over!");
            build.setMessage("Congratulations to A for winning!");
            build.setPositiveButton("OK", (dialog, which) -> finish());
            build.show();
        }
    }
}
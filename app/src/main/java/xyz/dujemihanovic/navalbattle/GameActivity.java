package xyz.dujemihanovic.navalbattle;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    TextView status;
    Player a, b;
    ButtonAction current;

    @SuppressLint("ResourceType")
    private void beginPlaceShipsA() {
        current = ButtonAction.A_PLACING;
        status.setText("Player 1: Place your ships");
        status.setVisibility(View.VISIBLE);
    }

    private void beginPlaceShipsB() {
        current = ButtonAction.B_PLACING;
        status.setText("Player 2: Place your ships");
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

    private void gameLoop() {
        do {

        } while (!a.lost() && !b.lost());

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Game over!");
        if (a.lost())
            build.setMessage("B won, congratulations!");
        else
            build.setMessage("A won, congratulations!");
        build.setPositiveButton("OK", (dialog, which) -> finish());
        build.show();
    }

    private void btnOnClick(View v) {
        @SuppressLint("ResourceType") boolean isB = v.getId() > 63;

        switch (current) {
            case A_PLACING:
                if (isB) return;
                if (a.getShipsPlaced() < 4) {
                    if (a.placeShip(v.getId()))
                        Toast.makeText(this, "Can't place ship there!", Toast.LENGTH_SHORT).show();
                }
                else beginPlaceShipsB();
                break;

            case B_PLACING:
                if (!isB) return;
                if (b.getShipsPlaced() < 4) {
                    if (b.placeShip(v.getId()))
                        Toast.makeText(this, "Can't place ship there!", Toast.LENGTH_SHORT).show();
                }
                else gameLoop();
                break;
        }
    }
}
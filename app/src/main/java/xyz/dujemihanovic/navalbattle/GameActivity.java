package xyz.dujemihanovic.navalbattle;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

public class GameActivity extends AppCompatActivity {

    GridLayout grid;

    Player a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        a = new Player();
        b = new Player();

        // create board dynamically with GridLayout, perhaps can be replaced with RecyclerView?
        grid = findViewById(R.id.grid);

        for (int i = 0; i < 128; i++) {
            TextView tv = new TextView(this);
            tv.setText("B");
            tv.setWidth(96);
            tv.setHeight(96);
            grid.addView(tv);
        }
    }
}
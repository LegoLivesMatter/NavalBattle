package xyz.dujemihanovic.navalbattle;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StatsActivity extends AppCompatActivity {

    Button back;
    TextView tvWins, tvLosses, tvRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int wins, losses;
        float ratio;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("stats", MODE_PRIVATE);
        wins = pref.getInt("wins", 0);
        losses = pref.getInt("losses", 0);
        ratio = losses > 0 ? (float) wins / losses : Float.NaN;

        back = findViewById(R.id.btnBack);
        tvWins = findViewById(R.id.tvWins);
        tvLosses = findViewById(R.id.tvLosses);
        tvRatio = findViewById(R.id.tvRatio);

        back.setOnClickListener(v -> finish());
        tvWins.setText(getString(R.string.key_strBotWins, wins));
        tvLosses.setText(getString(R.string.key_strBotLosses, losses));
        tvRatio.setText(getString(R.string.key_strWLR, ratio));
    }
}
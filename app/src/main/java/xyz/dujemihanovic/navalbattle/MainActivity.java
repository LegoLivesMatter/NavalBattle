package xyz.dujemihanovic.navalbattle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button exit, options, play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        exit = findViewById(R.id.btnExit);
        exit.setOnClickListener(v -> {
            super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            finish();
        });

        options = findViewById(R.id.btnOptions);
        options.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, OptionsActivity.class);
            startActivity(intent);
        });

        play = findViewById(R.id.btnPlay);
        play.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, GameModeActivity.class);
            startActivity(intent);
        });
    }
}
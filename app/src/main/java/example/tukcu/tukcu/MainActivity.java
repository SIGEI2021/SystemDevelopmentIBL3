package example.tukcu.tukcu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tukcu.tukcu.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3300;
    Animation animation;
    private ImageView mainLogo;
    private TextView welcomeText, whereithapens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        whereithapens = findViewById(R.id.whereithapens);
        welcomeText = findViewById(R.id.welcomeText);
        mainLogo = findViewById(R.id.mainLogo);

        animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        mainLogo.setAnimation(animation);
        welcomeText.setAnimation(animation);
        whereithapens.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

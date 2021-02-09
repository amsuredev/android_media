package pl.alex.lab6_finish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.audio:
                        //Toast.makeText(MainActivity.this, "audio", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), AudioActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.video:
                        Toast.makeText(MainActivity.this, "video", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.camera:
                        Toast.makeText(MainActivity.this, "camera", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}
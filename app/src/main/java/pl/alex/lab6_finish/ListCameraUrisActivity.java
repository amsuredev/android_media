package pl.alex.lab6_finish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListCameraUrisActivity extends AppCompatActivity {
    ListView videoUrisList;

    public static final String CAMERA_PREFERENCES = "CAMERA_PREFERENCES";
    public static final String CAMERA_URIS = "CAMERA_URIS";
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_camera_uris);
        mSettings = getSharedPreferences(CAMERA_PREFERENCES, Context.MODE_PRIVATE);
        videoUrisList = findViewById(R.id.video_uris_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String videoUrisBeforeParsing = mSettings.getString(CAMERA_URIS, null);
        String[] videoUris;
        if (videoUrisBeforeParsing == null)
        {
            videoUris = new String[] {};
        } else {
            videoUris = videoUrisBeforeParsing.split(";");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, videoUris);
        videoUrisList.setAdapter(adapter);
    }
}
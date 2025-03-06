package com.example.triviab;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private FbModule fbModule;
    private String backgroundColor = "";
    private ConstraintLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = findViewById(R.id.main);

        fbModule = new FbModule(this);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                        if (o.getResultCode() == RESULT_OK) {
                            Intent data = o.getData();
                            String str = data.getStringExtra("color");
                            fbModule.writeBackgroundColorToFb(str);
                            //Toast.makeText(MainActivity.this, "" + str, Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );

    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("color",backgroundColor);
        startActivity(intent);
    }


    public void onSetting(View view) {
        Intent i = new Intent(this, SettingActivity.class);

        launcher.launch(i);
    }


    public void onInstruction(View view) {

        Intent i = new Intent(this, InstructionActivity.class);
        startActivity(i);
    }

    public void setNewColorFromFb(String str) {
        this.backgroundColor = str;
        //Toast.makeText(this, ""+backgroundColor, Toast.LENGTH_SHORT).show();
        setBackgroundColor(str);
    }

    public void setBackgroundColor(String color)
    {
        switch (color)
        {
            case "Red":
            {
                ll.setBackgroundColor(Color.RED);
                break;
            }
            case "Blue":
            {
                ll.setBackgroundColor(Color.BLUE);
                break;
            }
            case "Pink":
            {
                ll.setBackgroundColor(Color.argb(255,255,105,180));
                break;
            }
            case "Yellow":
            {
                ll.setBackgroundColor(Color.YELLOW);
                break;
            }

            default:
                ll.setBackgroundColor(Color.WHITE);
        }
    }
}
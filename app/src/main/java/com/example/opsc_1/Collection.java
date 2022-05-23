package com.example.opsc_1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.awt.font.TextAttribute;
import com.example.opsc_1.Collection;
import com.example.opsc_1.AddCollection;

public class Collection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_main);
        ImageButton add;
        String name;

        add = (ImageButton) findViewById(R.id.Addbtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCollectioPage();
            }
        });
    }

    public void openAddCollectioPage() {
        Intent intent = new Intent(Collection.this,AddCollection.class);
        startActivity(intent);
    }
/*
    TextView displayName = findViewById(R.id.collectionName);
    Bundle bn = getIntent().getExtras();
    String name = bn.getString("sendname");
    //displayName.setText(String.valueOf(name));
    TextView displayGoal = findViewById(R.id.collectionGoal);
    String goal = bn.getString("sendgoal");
    //displayName.setText(String.valueOf(goal));
*/

}


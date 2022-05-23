package com.example.opsc_1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.awt.font.TextAttribute;


public class AddCollection extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_collection);

        EditText name = findViewById(R.id.ETName);
        EditText goal = findViewById(R.id.ETGoal);
        Button add = findViewById(R.id.addcollectionbtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempName = name.getText().toString();
                String tempGoal = goal.getText().toString();

                Intent intent = new Intent(AddCollection.this,Collection.class);
                intent.putExtra("sendname",tempName);
                intent.putExtra("sendgoal",tempGoal);
                startActivity(intent);
                finish();

            }
        });

    }

}

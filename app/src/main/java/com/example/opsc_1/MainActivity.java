package com.example.opsc_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu,menu);

            MenuItem menuItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Type here to search");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {//Once a user has clicked submit on a search, it will search for item.

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
    }
}
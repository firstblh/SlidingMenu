package com.firstblh.qqsidemenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.firstblh.qqsidemenu.tablayoutviewpager.ViewPagerActivity;
import com.firstblh.qqsidemenu.widget.SlidingMenu;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SlidingMenu slidMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidMenu = (SlidingMenu) this.findViewById(R.id.slidMenu);
        this.findViewById(R.id.open_viewpager).setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ImageView iv1 = (ImageView) this.findViewById(R.id.iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "you click the iv1", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void toggleMenu(View view) {
        slidMenu.toggle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_viewpager:
                Intent i = new Intent(this, ViewPagerActivity.class);
                startActivity(i);
                break;
        }
    }
}

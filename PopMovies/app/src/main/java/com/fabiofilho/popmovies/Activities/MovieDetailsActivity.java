package com.fabiofilho.popmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.fabiofilho.popmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dialam on 30/01/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_details_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        //Loads the toolbar object.
        loadToolbar();
    }


    private void loadToolbar(){

        setSupportActionBar(mToolbar);

        // Actives the back arrow if there is a "Support Action Bar".
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

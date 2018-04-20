package com.example.android.movieapitest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    ArrayList<MovieObject> mMovieArray;
    private String option = "popular";
    //RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.main_activity);
        setTitle("Popular Movies");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this);

        if (savedInstanceState == null || !savedInstanceState.containsKey("MovieObjectArray")) {
            loadMovieData();
        } else {
            adapter.setMovieData(savedInstanceState.getParcelableArrayList("MovieObjectArray"));
        }

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieObject movieObject) {
                //Toast.makeText(getApplicationContext(), movieObject.getTitle(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                //Log.v("adapter getItem title", adapter.getItem(position).getTitle());
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("MovieObject", movieObject);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMovieArray = adapter.getmMovieDataArray();
        outState.putParcelableArrayList("MovieObjectArray", mMovieArray);
        super.onSaveInstanceState(outState);
    }

    /*
     *Check if there is Network Connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /*
    Start AsyncTask to fetch Movie Data
     */
    private void loadMovieData() {
        if (isOnline()) {
            //new FetchMovieData().execute();
            new FetchMovieData(this, new FetchMyDataTaskCompleteListener()).execute(option);
        } else {
            Toast.makeText(getApplicationContext(), "No Network Connection.", Toast.LENGTH_LONG).show();
        }
    }

    /*
    Create option menu for Sort type
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    /*
    functions for Option Menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_popular:
                option = "popular";
                setTitle("Popular Movies");
                loadMovieData();
                return true;
            case R.id.option_top_rated:
                option = "top_rated";
                setTitle("Top Rated Movies");
                loadMovieData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
Fetch Movie Data using AsyncTask
 */
    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<MovieObject>> {
        @Override
        public void onTaskComplete(ArrayList<MovieObject> result) {
            adapter.setMovieData(result);
        }
    }


}

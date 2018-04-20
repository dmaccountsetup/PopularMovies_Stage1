package com.example.android.movieapitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/*
*Activity for Movie Detail display
*/
public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_OBJECT = "MovieObject";
    ImageView mImageView;
    TextView mSynopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Movie Detail");
        Intent intent = getIntent();
        MovieObject movieObject = intent.getParcelableExtra(MOVIE_OBJECT);

        String posterPath = movieObject.getPosterPath();
        String title = movieObject.getTitle();
        String synopsis = movieObject.getSynopsis();
        String rating = movieObject.getRating();
        String date = movieObject.getDate();

        //adding scrollable function to Synopsis TextView
        mSynopsis = (TextView)findViewById(R.id.synopsis);
        mSynopsis.setMovementMethod(new ScrollingMovementMethod());

        //add poster image to ImageView
        mImageView = (ImageView)findViewById(R.id.imageView);
        Picasso.with(getApplicationContext())
                .load(posterPath)
                .fit()
                .into(mImageView);

        TextView mMovieTitle = findViewById(R.id.movie_title);
        mMovieTitle.setText(title);

        TextView mSynopsis = findViewById(R.id.synopsis);
        mSynopsis.setText(synopsis);

        TextView mRating = findViewById(R.id.user_rating);
        mRating.setText("Rating: "+rating+"/10");

        TextView mDate = findViewById(R.id.release_date);
        mDate.setText(date);
    }
}

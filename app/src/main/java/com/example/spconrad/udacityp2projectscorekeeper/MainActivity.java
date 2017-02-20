package com.example.spconrad.udacityp2projectscorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int[] teams = new int[] {0, 0};
    int safety = 2;
    int fieldGoal = 3;
    int touchDown = 6;
    int extraPoint = 1;
    int twoPointConversion = 2;

    TextView team0ScoreTextView;
    TextView team1ScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View team0 = findViewById(R.id.team_0_layout);
        View team1 = findViewById(R.id.team_1_layout);

        int team0id = getResources().getIdentifier("team_" + 0 + "_score", "id", getPackageName());
        team0ScoreTextView = (TextView) team0.findViewById(team0id);
        int team1id = getResources().getIdentifier("team_" + 1 + "_score", "id", getPackageName());
        team1ScoreTextView = (TextView) team1.findViewById(team1id);
    }


    public void causedSafety(View view){
        addPoints(view, safety);
    }

    public void gotFieldGoal(View view){
        addPoints(view, fieldGoal);
    }

    public void gotTouchDown(View view){
        addPoints(view, touchDown);
    }

    public void gotExtraPoint(View view){
        addPoints(view, extraPoint);
    }

    public void gotTwoPointConversion(View view){
        addPoints(view, twoPointConversion);
    }

    private void addPoints(View view, int score){
        String team = (String) view.getTag();
        int i_team = Integer.parseInt(team);
        teams[i_team] += score;
        display();
    }

    private void display(){
        team0ScoreTextView.setText("" + teams[0]);
        team1ScoreTextView.setText("" + teams[1]);
    }
}

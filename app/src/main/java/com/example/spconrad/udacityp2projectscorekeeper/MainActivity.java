package com.example.spconrad.udacityp2projectscorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    Button team0ExtraPoint;
    Button team0TwoPointConversion;
    Button team1ExtraPoint;
    Button team1TwoPointConversion;

    int currentTeam = -1;

    boolean[] teamClicked = new boolean[] {false, false};

    /// The grander plan for this app would be to have a single row of buttons and
    /// be able to select either team 0 or team 1, then the buttons would use
    /// the parent tag/id to identify the team and assign the new points.
    /// Additionally, I'd like to add a condition that requires a touchdown happen before an extra
    /// point or two point conversion can be entered, but that's a bit complicated
    /// for me at the moment and I believe not entirely necessary for the scope
    /// of this project.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///the textViews are nested within linearLayouts, so we must first get
        ///those layouts and then search for the textViews
        View team0 = findViewById(R.id.team_0_layout);
        View team1 = findViewById(R.id.team_1_layout);

        ///alright, we have found the necessary views, go in and get the textviews
        int team0id = getResources().getIdentifier("team_" + 0 + "_score", "id", getPackageName());
        team0ScoreTextView = (TextView) team0.findViewById(team0id);
        int team1id = getResources().getIdentifier("team_" + 1 + "_score", "id", getPackageName());
        team1ScoreTextView = (TextView) team1.findViewById(team1id);

        team0ExtraPoint = (Button) team0.findViewById(R.id.extra_point);
        team0TwoPointConversion = (Button) team0.findViewById(R.id.two_point_conversion);

        team1ExtraPoint = (Button) team0.findViewById(R.id.extra_point);
        team1TwoPointConversion = (Button) team0.findViewById(R.id.two_point_conversion);

        team0ExtraPoint.setEnabled(false);
        team0TwoPointConversion.setEnabled(false);

        team1ExtraPoint.setEnabled(false);
        team1TwoPointConversion.setEnabled(false);
    }

    private void changeButtons(int team, boolean changeTo){
        team0ExtraPoint.setEnabled(changeTo);
        team0TwoPointConversion.setEnabled(changeTo);
    }

    /*public void teamClicked(View view){
        currentTeam = getTeamNumber(view);
        teamClicked[currentTeam] = true;
    }*/

    private int getTeamNumber(View view){
        ///can't seem to get this to work by simply parseInt on the getTag, so
        ///first I cast it to a string and then convert it to an int.
        String s_team = (String) view.getTag();
        int i_team = Integer.parseInt(s_team);
        return i_team;
    }

    public void causedSafety(View view){
        int team = getTeamNumber(view);
        addPoints(team, safety);
    }

    public void gotFieldGoal(View view){
        int team = getTeamNumber(view);
        addPoints(team, fieldGoal);
    }

    public void gotTouchdown(View view){
        int team = getTeamNumber(view);
        changeButtons(team, true);
        addPoints(team, touchDown);
    }

    public void gotExtraPoint(View view){
        int team = getTeamNumber(view);
        changeButtons(team, false);
        addPoints(team, extraPoint);
    }

    public void gotTwoPointConversion(View view){
        int team = getTeamNumber(view);
        changeButtons(team, false);
        addPoints(team, twoPointConversion);
    }

    public void resetScores(View view){
        ///in a larger array this would be a for or forEach loop
        teams[0] = 0;
        teams[1] = 0;
        display();
    }

    private void addPoints(int team, int score){
        if (score != 0) {
            teams[team] += score;
        } else {
            ///if the passed in number is a zero then you ought to reset it to 0
            teams[team] = 0;
        }
        display();
    }

    private void display(){
        ///rather than passing in the text view, just update them both at the same time.
        ///obviously not viable for larger more complicated apps, but this is only
        ///updating two text fields so it's fine for now
        team0ScoreTextView.setText("" + teams[0]);
        team1ScoreTextView.setText("" + teams[1]);
    }
}

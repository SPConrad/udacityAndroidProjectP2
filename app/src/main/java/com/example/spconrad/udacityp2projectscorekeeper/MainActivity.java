package com.example.spconrad.udacityp2projectscorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ///create a whole bunch of variables
    ///int array to keep track of scores
    int[] teams = new int[]{0, 0};

    ///textview array for the score text views
    TextView[] teamScoreTextViews = new TextView[2];

    ///view arrays for the container layouts
    View[] teamView = new View[2];

    ///buttons!
    Button fieldGoalButton;
    Button touchdownButton;
    Button safetyButton;
    Button extraPointButton;
    Button twoPointConversionButton;
    Button resetButton;

    ///current team at any given time
    int currentTeam = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ///the textViews are nested within linearLayouts, so we must first get
        ///those layouts and then search for the textViews
        ///intialize the layout views
        teamView[0] = findViewById(R.id.team_0_layout);
        teamView[1] = findViewById(R.id.team_1_layout);

        ///alright, we have found the necessary views, go in and get the textviews
        int team0id = getResources().getIdentifier("team_" + 0 + "_score", "id", getPackageName());
        int team1id = getResources().getIdentifier("team_" + 1 + "_score", "id", getPackageName());
        teamScoreTextViews[0] = (TextView) teamView[0].findViewById(team0id);
        teamScoreTextViews[1] = (TextView) teamView[1].findViewById(team1id);

        ///find the buttons!!
        extraPointButton = (Button) findViewById(R.id.extra_point_button);
        twoPointConversionButton = (Button) findViewById(R.id.two_point_conversion_button);
        fieldGoalButton = (Button) findViewById(R.id.field_goal_button);
        touchdownButton = (Button) findViewById(R.id.touchdown_button);
        safetyButton = (Button) findViewById(R.id.safety_button);
        resetButton = (Button) findViewById(R.id.reset_scores_button);

        ///disable the buttons. I do this here instead of in the XML as it is a
        ///bit easier to manage
        changeButtons(false);
        changeExtraPointButtons(false);

    }

    private void changeButtons(boolean changeTo){
        ///this enables or disables the main scoring options when a team is selected
        /// or after a team has finished a scoring run
        fieldGoalButton.setEnabled(changeTo);
        safetyButton.setEnabled(changeTo);
        touchdownButton.setEnabled(changeTo);
    }

    private void changeResetButton(boolean changeTo){
        ///enable or disable the reset button if there are or are not any scores
        resetButton.setEnabled(changeTo);
    }

    private void changeExtraPointButtons(boolean changeTo) {
        ///enable the extra point buttons when a touch down is scored
        ///and disable them after an extra point attempt is completed
        extraPointButton.setEnabled(changeTo);
        twoPointConversionButton.setEnabled(changeTo);

        ///give them the ability to enter a failed extra point attempt
        if (changeTo == true){
            fieldGoalButton.setEnabled(changeTo);
            safetyButton.setEnabled(changeTo);
            touchdownButton.setText("Failed Extra Point Attempt");
            touchdownButton.setTag("0");
        } else
        {
            ///and then reset it to standard touchdown afterwards
            touchdownButton.setText("Touchdown");
            touchdownButton.setTag("6");
        }
    }

    private void teamUnClicked(){
        ///clear out the changed values when a team is unselected/finishes its run
        if (currentTeam != -1) {
            teamView[currentTeam].setBackgroundColor(0xFFFFFF);
            teamView[currentTeam].setClickable(true);
            //changeButtons(false);
            currentTeam = -1;
            changeButtons(false);
            changeExtraPointButtons(false);
        }
    }

    public void teamWasClicked(View view) {
        ///make things shiny and set variables when a team is selected
        teamUnClicked();
        currentTeam = getTag(view);

        teamView[currentTeam].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        teamView[currentTeam].setClickable(false);

        changeButtons(true);
    }
    public void addPoints(View view) {
        ///pretty straightforward
        ///this if might not be strictly necessary but I like the extra protection
        if (currentTeam == 0 || currentTeam == 1) {
            int pointsToAdd = getTag(view);
            teams[currentTeam] += pointsToAdd;

            ///I'm not sure if two if statements are more or less expensive
            ///than changing this to be enabled every time regardless, so
            ///I'm just going to enable it every time
            changeResetButton(true);

            ///if it was a touch down, enable the extra point buttons
            ///and disable the other buttons
            if (pointsToAdd == 6){
                changeExtraPointButtons(true);
            } else {
                teamUnClicked();
            }

            display();
        }
    }

    private void display() {
        ///rather than passing in the text view, just update them both at the same time.
        ///obviously not viable for larger more complicated apps, but this is only
        ///updating two text fields so it's fine for now
        teamScoreTextViews[0].setText("" + teams[0]);
        teamScoreTextViews[1].setText("" + teams[1]);
    }

    public void resetScores(View view) {
        ///in a larger array this would be a for or forEach loop
        teams[0] = 0;
        teams[1] = 0;
        display();
        changeResetButton(false);
    }

    private int getTag(View view) {
        ///can't seem to get this to work by simply parseInt on the getTag, so
        ///first I cast it to a string and then convert it to an int.
        String s_team = (String) view.getTag();
        int i_team = Integer.parseInt(s_team);
        return i_team;
    }

}

package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[3][3];

    private boolean playerXTurn = true;

    private int roundCount;

    private int playerXPoints, playerOPoints;

    private TextView textViewPlayerX, textViewPlayerO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = findViewById(R.id.text_view_player_x);
        textViewPlayerO = findViewById(R.id.text_view_player_o);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if (playerXTurn){
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()){
            if(playerXTurn){
                playerXWins();
            } else {
                playerOwins();
            }
        } else if (roundCount == 9){
            draw();
        } else {
            playerXTurn = !playerXTurn;
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for (int i = 0; i <3; i++){
             for (int j =0; j<3; j++) {
                 field[i][j] = buttons[i][j].getText().toString();
             }
        }

        for (int i= 0; i<3; i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }

        for (int i= 0; i<3; i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][1].equals(field[2][i])
                    && !field[0][1].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1 ])
                && field[0][2].equals(field[2][0])
                && !field[0][2 ].equals("")){
            return true;
        }
        return false;
    }

    private void playerXWins(){
        playerXPoints++;
        Toast.makeText(this,"Player X wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void  playerOwins(){
        playerOPoints++;
        Toast.makeText(this,"Player O wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private  void  draw(){
        Toast.makeText(this,"Its a Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayerX.setText("Player X: " + playerXPoints);
        textViewPlayerO.setText("Player O: " + playerOPoints);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;

        playerXTurn = true;

    }

    private  void  resetGame(){
        playerXPoints = 0;
        playerOPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerXPoints",playerXPoints);
        outState.putInt("playerOPoints",playerOPoints);
        outState.putBoolean("playerXTurn",playerXTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerXPoints = savedInstanceState.getInt("playerXPoints");
        playerOPoints = savedInstanceState.getInt("playerOPoints");
        playerXTurn = savedInstanceState.getBoolean("playerXTurn");
    }
}

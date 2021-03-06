package com.andrew.sudoku;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private Logic dataLogic;
    protected static boolean isVisible = true;

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
        board.loadSave();
    }

    @Override
    public void onPause() {
        board.writeSave();
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataLogic = new Logic();
        LinearLayout parentLayout = findViewById(R.id.parent);

        board = new Board(MainActivity.this, dataLogic);

        parentLayout.addView(board);
        parentLayout.setOnTouchListener(board);

        for (int x = 0; x < 9; x ++){
            TextView textView = new TextView(MainActivity.this);
            textView.setText(String.valueOf(x));
            parentLayout.addView(textView);
        }

    }

}
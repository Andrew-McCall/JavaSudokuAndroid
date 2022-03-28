package com.andrew.sudoku;

import static java.time.Clock.tick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class Board extends View implements View.OnTouchListener{

    private int time;
    private Logic dataLogic;
    private int selectedNo = 5;
    private boolean pencil = false;

    private int BoxDimensions;
    private int PanelButton;

    public Board(Context context, Logic logic) {
        super(context);
        dataLogic = logic;
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (dataLogic.getValue(0,0) != 5){
                    time+=1;
                    Board.super.invalidate();
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void onDraw(Canvas canvas) {

        BoxDimensions = canvas.getWidth() / 9;

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setTextSize(BoxDimensions * 0.8f);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                // Checker //
                if ((x + y) % 2 == 0) {
                    paint.setColor(Color.rgb(220, 175, 200));
                } else {
                    paint.setColor(Color.rgb(255, 200, 235));
                }
                Rect rect = new Rect(x * BoxDimensions, y * BoxDimensions, x * BoxDimensions + BoxDimensions, y * BoxDimensions + BoxDimensions);
                canvas.drawRect(rect, paint);

                // Text //
                final int no = dataLogic.getValue(x, y);
                final int meta = dataLogic.getMeta(x, y);

                if (no == 0) continue;

                switch (meta) {
                    case 0:    // Pencil
                        paint.setColor(Color.rgb(170, 150, 160));
                        break;

                    case 1: // Regular
                        paint.setColor(Color.rgb(100, 30, 80));
                        break;

                    case 3: // Permanent
                        paint.setColor(Color.rgb(90, 10, 60));
                        break;

                    case 5: // Green For Fin.
                        paint.setColor(Color.rgb(45, 120, 45));
                        break;

                    default: // Error (4 & 2)
                        paint.setColor(Color.rgb(255, 45, 45));
                }

                canvas.drawText(String.valueOf(no), (float) ((x * BoxDimensions) + BoxDimensions / 4), (float) ((y * BoxDimensions) + BoxDimensions / 1.25), paint);

            }
        }

        // Box lines (Large 3x3)
        paint.setColor(Color.rgb(90, 10, 60));
        for (int row = 0; row < 4; row++) {
            Rect rect = new Rect(3 * BoxDimensions * row - 8,
                    0,
                    3 * BoxDimensions * row + 8,
                    9 * BoxDimensions
            );
            canvas.drawRect(rect, paint);

            rect = new Rect(0,
                    3 * BoxDimensions * row + 8,
                    9 * BoxDimensions,
                    3 * BoxDimensions * row - 8
            );
            canvas.drawRect(rect, paint);
        }


        // Control Panel //

        PanelButton = (int) (BoxDimensions * 1.5f);

        for (int x = 0; x < 9; x++) {
            int left = (int) (PanelButton / 3 + (PanelButton) * (x % 3));
            int top = (int) (PanelButton * 6.7 + (PanelButton) * (x / 3));
            Rect rect = new Rect(left,
                    top,
                    left + PanelButton - BoxDimensions / 10,
                    top + PanelButton - BoxDimensions / 10
            );

            paint.setColor(Color.rgb(220, 175, 200));
            canvas.drawRect(rect, paint);

            if (x + 1 != selectedNo) {
                rect = new Rect(left,
                        top,
                        left + PanelButton - BoxDimensions / 6,
                        top + PanelButton - BoxDimensions / 6
                );

                paint.setColor(Color.rgb(255, 200, 235));
                canvas.drawRect(rect, paint);
            }


            paint.setColor(Color.rgb(90, 10, 60));
            paint.setTextSize(PanelButton);
            canvas.drawText(String.valueOf(x + 1), (float) left + PanelButton / 5, (float) ((float) top + PanelButton / 1.25), paint);
        }

        // Erase Button
        Rect rect = new Rect(9 * BoxDimensions - PanelButton / 3 - PanelButton * 2,
                (int) (11.5 * BoxDimensions),
                9 * BoxDimensions - PanelButton / 3,
                (int) (12.5 * BoxDimensions)
        );
        paint.setColor(Color.rgb(220, 175, 200));
        canvas.drawRect(rect, paint);

        if (selectedNo != 0) {
            rect = new Rect(9 * BoxDimensions - PanelButton / 3 - PanelButton * 2,
                    (int) (11.5 * BoxDimensions),
                    9 * BoxDimensions - PanelButton / 3 - BoxDimensions / 8,
                    (int) (12.5 * BoxDimensions - BoxDimensions / 8)
            );
            paint.setColor(Color.rgb(255, 200, 235));
            canvas.drawRect(rect, paint);
        }

        paint.setColor(Color.rgb(90, 10, 60));
        paint.setTextSize(BoxDimensions * 0.8f);
        canvas.drawText("Eraser", 9.4f * BoxDimensions - PanelButton / 3 - PanelButton * 2, 12.2f * BoxDimensions, paint);

        // Pencil/Pen Button
        rect = new Rect(9 * BoxDimensions - PanelButton / 3 - PanelButton * 2,
                13 * BoxDimensions,
                9 * BoxDimensions - PanelButton / 3,
                14 * BoxDimensions
        );
        paint.setColor(Color.rgb(220, 175, 200));
        canvas.drawRect(rect, paint);

        if (!pencil) {
            rect = new Rect(9 * BoxDimensions - PanelButton / 3 - PanelButton * 2,
                    13 * BoxDimensions,
                    9 * BoxDimensions - PanelButton / 3 - BoxDimensions / 8,
                    14 * BoxDimensions - BoxDimensions / 8
            );
            paint.setColor(Color.rgb(255, 200, 235));
            canvas.drawRect(rect, paint);
        }

        paint.setColor(Color.rgb(90, 10, 60));
        paint.setTextSize(BoxDimensions * 0.8f);
        canvas.drawText((pencil) ? "Pen" : "Pencil", 9.5f * BoxDimensions - PanelButton / 3 - PanelButton * 2 + ((pencil) ? PanelButton / 4 : 0), 13.75f * BoxDimensions, paint);

        // Timer
        rect = new Rect(9 * BoxDimensions - PanelButton / 3 - PanelButton * 2,
                10 * BoxDimensions,
                9 * BoxDimensions - PanelButton / 3,
                11 * BoxDimensions
        );
        paint.setColor(Color.rgb(255, 200, 235));
        canvas.drawRect(rect, paint);

        paint.setColor(Color.rgb(90, 10, 60));
        String clock = String.format("%d : %d", time/60, time%60);
        canvas.drawText(clock, 6.25f * BoxDimensions - ((time/60>9)?BoxDimensions/3.15f:0), 10.75f * BoxDimensions, paint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            int squareX = (int) (motionEvent.getX()/BoxDimensions);
            int squareY = (int) (motionEvent.getY()/BoxDimensions);

            if (squareX < 9 && squareX >= 0 && squareY < 9 && squareY >= 0){
                dataLogic.writeNumber(squareX, squareY, selectedNo, pencil);
            } else {

            }
            super.invalidate();
            return true;
        }
        return false;
    }


}
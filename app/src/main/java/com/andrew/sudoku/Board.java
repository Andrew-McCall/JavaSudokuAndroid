package com.andrew.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View implements View.OnTouchListener{

    Logic dataLogic;

    public Board(Context context, Logic logic) {
        super(context);
        dataLogic = logic;
    }

    private int BoxDimensions;

    @Override
    public void onDraw(Canvas canvas) {

        BoxDimensions = canvas.getWidth()/9;

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setTextSize(BoxDimensions*0.8f);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if ((x + y) % 2 == 0 ){
                    paint.setColor( Color.rgb(220, 175, 200) );
                }else{
                    paint.setColor( Color.rgb(255, 200, 235) );
                }
                Rect rect = new Rect(x*BoxDimensions, y*BoxDimensions, x*BoxDimensions + BoxDimensions, y*BoxDimensions + BoxDimensions);
                canvas.drawRect(rect, paint);

                int no = dataLogic.getValue(x, y);
                int meta = dataLogic.getMeta(x, y);

                if (no == 0) continue;

                switch (meta){
                    case 0:	// Pencil
                        paint.setColor( Color.rgb(180, 30, 100) );
                        break;

                    case 1: // Regular
                        paint.setColor( Color.rgb(120, 15, 80) );
                        break;

                    case 3: // Permanent
                        paint.setColor( Color.rgb(9, 10, 60) );
                        break;

                    case 5: // Green For Fin.
                        paint.setColor( Color.rgb(45, 120, 45) );
                        break;

                    default: // Error (4 & 2)
                        paint.setColor( Color.rgb(255, 45, 45) );
                }

                canvas.drawText(String.valueOf(no),(float) ((x*BoxDimensions)+BoxDimensions/4), (float) ((y*BoxDimensions)+BoxDimensions/1.25), paint);

            }
        }

        // Box lines (Large 3x3)
        paint.setColor( Color.rgb(90, 10, 60) );
        for (int row = 0; row < 4; row++) {
            Rect rect = new Rect(3*BoxDimensions*row-8,
                    0,
                    3*BoxDimensions*row+8,
                    9*BoxDimensions
            );
            canvas.drawRect(rect, paint);

            rect = new Rect(0,
                    3*BoxDimensions*row+8,
                    9*BoxDimensions,
                    3*BoxDimensions*row-8
            );
            canvas.drawRect(rect, paint);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            int squareX = (int) (motionEvent.getX()/BoxDimensions);
            int squareY = (int) (motionEvent.getY()/BoxDimensions);

            Log.d("Touch coordinates",String.valueOf(squareX) + ", " + String.valueOf(squareY));

        }
        return true;
    }
}
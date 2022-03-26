package com.andrew.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Board extends View {

    public Board(Context context) {
        super(context);
    }



    @Override
    public void onDraw(Canvas canvas) {

        int BoxDimensions = canvas.getWidth()/9;

        Paint paint = new Paint();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if ((x + y) % 2 == 0 ){
                    paint.setColor( Color.rgb(220, 175, 200) );
                }else{
                    paint.setColor( Color.rgb(255, 200, 235) );
                }
                Rect rect = new Rect(x*BoxDimensions, y*BoxDimensions, x*BoxDimensions + BoxDimensions, y*BoxDimensions + BoxDimensions);
                canvas.drawRect(rect, paint);
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

}
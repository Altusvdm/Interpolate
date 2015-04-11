package za.co.afrikaburn.interpolate.ui.views.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Altus on 2015/02/01.
 */
public class Border {

    private static final int BORDER_WIDTH = 1;

    public static void onDraw(View view, Canvas canvas, int borderWidth, int color) {
        int height = view.getHeight();
        int width = view.getWidth();

        Paint paint = new Paint();

        paint.setColor(color);
        paint.setStrokeWidth(borderWidth);

        //Top
        canvas.drawLine(0, 0, width, 0, paint);
        //Right
        canvas.drawLine(width, 0, width, height, paint);
        //Left
        canvas.drawLine(0, 0, 0, height, paint);
        //Bottom
        canvas.drawLine(0, height, width, height, paint);
    }

    public static void onDraw(View view, Canvas canvas) {
        onDraw(view, canvas, BORDER_WIDTH, Color.parseColor("#E4E4E4"));
    }
}
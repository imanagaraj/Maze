package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;

import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Toast;
import edu.wm.cs.cs301.amazebycarolinefaparnan.ui.PlayActivity;


/**
 * Created by Aparna and Caroline on 11/16/16.
 */

public class MazePanel extends View {

    Canvas canvas;
    Bitmap bit;
    Paint paint;
    Point point;
    Color color;


    private Handler draw = new Handler();

    /**
     * General constructor
     * @param context
     */

    public MazePanel(Context context){
        super(context);
        //this.context = context;
        this.paint = new Paint ();
        this.bit = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bit);
        paint.setColor(Color.RED);
    }

    /**
     * constructor for very generalized objects, like point
     * @param context
     * @param attributes
     */

    public MazePanel(Context context, AttributeSet attributes){
        super(context, attributes);
        bit = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bit);
        paint = new Paint();
        // paint.setColor();
        paint.setColor(Color.RED);

    }

    /**
     * Override the onDraw to draw on the bitmap v canvas
     * @param c
     */
    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);
        c.drawBitmap(this.bit,0,0, null);
        System.out.println("ondraw reached");

    }

    /**
     * return the bitmap
     * @return
     */
    public Bitmap getBit() {
        return bit;
    }

    /**
     * return the paint
     * @return
     */

    public Paint getPaint() {
        return paint;
    }


    public void setPaint(){
        paint.setStyle(Style.FILL);
    }

    /**
     * return the canvas
     * @return
     */
    public Canvas getGraphics() {
        return canvas;
    }

    public void setCanvas(Canvas can){
        this.canvas = can;
    }

    /**
     * draws a line
     * @param x
     * @param y
     * @param a
     * @param b
     */
    public void drawLine(int x, int y, int a, int b){
        canvas.drawLine(x, y, a, b, paint);
    }

    /**
     * draws a rectangle
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void fillRect(int x, int y, int w, int h){
        paint.setStyle(Style.FILL);
        canvas.drawRect(new Rect(x, y, w+x, h+y), paint);
        //canvas.drawRect(new Rect(x,y, w, h), paint);
    }

    /**
     * draws a path and fills to create a polygon
     * @param xps
     * @param yps
     * @param z
     */
    public void fillPolygon(int[] xps, int [] yps, int z){
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(xps[0], yps[0]);
        for(int i = 1; i < z; i++){
            path.lineTo(xps[i], yps[i]);
        }
        //path.lineTo(xps[0], yps[0]);
        path.moveTo(xps[0], yps[0]);
        canvas.drawPath(path,paint);
    }

    /**
     * draws an oval using rectF
     * @param i
     * @param j
     * @param circle
     * @param circle2
     */
    public void fillOval(int i, int j, int circle, int circle2){
        RectF oval = new RectF(i, j, circle + i, circle2 + j);
        canvas.drawOval(oval, paint);
    }

    /**
     * updates graphics
     */
    public void update(){
        invalidate();
        System.out.println("PANEL.UPDATE");
    }

    /**
     * returns the point object
     * @return
     */
    public Point getPoint(){
        return point;
    }

    /**
     * sets the point object
     * @param x
     * @param y
     */
    public void setPoint(int x, int y){
        point = new Point(x,y);
    }

    /**
     * setting color useful for seg
     * @param color
     */
    public void setColor(int color){
        paint.setColor(color);
    }

    public void setColor(int color1, int color2, int color3){
        paint.setColor(Color.rgb(color1, color2, color3));
    }

    public static class GWColor extends Color {

        private int r, g, b, a = 255;
        private int RGB = -1;
        public GWColor(int i, int val1, int val12) {
            super();
            r = i;
            g = val1;
            b = val12;
        }
        public GWColor(int rgb) {
            super();
            RGB = rgb;
        }
        public int getR() { return r; }
        public int getG() { return g; }
        public int getB() { return b; }
        public int getRGB() {
            if (RGB != -1) return RGB;
            return ((a & 0xFF) << 24) |
                    ((r & 0xFF) << 16) |
                    ((g & 0xFF) <<  8) |
                    ((b & 0xFF) <<  0);
        }
    }

    public void drawBackground(int width, int height){
        setGraphicsColor("black");
        fillRect(0, 0, width, height/2);
        setGraphicsColor("pink");
        fillRect(0, 0, width, height/2);
    }


    /**
     * to use in seg
     * @param name of color
     */
    public void setGraphicsColor(String name){

        switch(name){
            case "red":
                paint.setColor(Color.RED);
                break;
            case "gray":
                paint.setColor(Color.GRAY);
                break;
            case "yellow":
                paint.setColor(Color.YELLOW);
                break;
            case "white":
                paint.setColor(Color.WHITE);
                break;
            case "black":
                paint.setColor(Color.BLACK);
                break;
            case "blue":
                paint.setColor(Color.BLUE);
                break;
            case "orange":
                paint.setColor(Color.YELLOW);
                break;
            case "darkGray":
                paint.setColor(Color.DKGRAY);
                break;
            case "pink":
                paint.setColor(Color.MAGENTA);
                break;
            case "cyan":
                paint.setColor(Color.CYAN);
                break;
        }

    }

    public Color getColor(){
        return color;
    }

//    public void setRenderingHint(Key hintKey, Object hintValue){
//		hint = new RenderingHints(hintKey,hintValue);




}


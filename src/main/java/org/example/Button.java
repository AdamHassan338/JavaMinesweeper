package org.example;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

import com.raylib.Raylib;
import com.raylib.Jaylib.*;
import static com.raylib.Jaylib.*;

public class Button {

    enum State{
        idle,
        hovered
    }

    private int width;
    private int height;
    private int x;
    private int y;

    private String text;
    Raylib.Color colour;
    int spacing = 1;

    private State state;

    Raylib.Rectangle rect;

    public enum Anchor{
        top,
        bottom,
        center

    }

    Button(String text,float x, float y,int width, int height, Raylib.Color colour){
        this.text = text;

        this.x =(int) (GetScreenWidth()*x) - (width/2);
        this.y = (int) (GetScreenHeight()*y) - (height/2);
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.spacing = spacing;


        rect = new Raylib.Rectangle();
        rect.x(this.x);
        rect.y(this.y);
        rect.width(width);
        rect.height(height);

        state = State.idle;
    }


    public void draw(){

        Raylib.Color brush = new Raylib.Color().r(colour.r()).g(colour.g()).b(colour.b()).a(colour.a());

        if(state==State.hovered) {
            tintColour(brush,0.5f);
        }

        DrawRectangle(x , y , width, height , brush);
        int textSize =20;
        int textWidth = MeasureText(text,textSize);
        int textX = x+(width-textWidth)/2;
        int textY = y+ (height-textSize)/2;
        DrawText(text,textX,textY,textSize,BLUE);

    }

    public String getText() {
        return text;
    }
    public void setState(State state) {
        this.state = state;
    }



    public static int mapByteTo255(byte x) {
        return Math.min (x + 128,255);
    }
    public static byte map255ToByte(int x) {
        return (byte) (x - 128);
    }

    private void tintColour(Raylib.Color c, float tint){

        c.r(  map255ToByte((int) (mapByteTo255(c.r())*tint)));
        c.g(  map255ToByte((int) (mapByteTo255(c.g())*tint)));
        c.b(  map255ToByte((int) (mapByteTo255(c.b())*tint)));

    }


    public Raylib.Rectangle rect(){
        return rect;
    }

}

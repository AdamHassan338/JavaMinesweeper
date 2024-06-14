package org.example;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

import com.raylib.Raylib;
import com.raylib.Jaylib.*;
import static com.raylib.Jaylib.*;

public class Button {

    private int width;
    private int height;
    private int x;
    private int y;

    public String getText() {
        return text;
    }

    private String text;
    Raylib.Color colour;
    int spacing = 1;

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
    }


    public void draw(){
        DrawRectangle(x , y , width, height , colour);
        int textSize =20;
        int textWidth = MeasureText(text,textSize);
        int textX = x+(width-textWidth)/2;
        int textY = y+ (height-textSize)/2;
        DrawText(text,textX,textY,textSize,BLUE);

    }


    public Raylib.Rectangle rect(){
        return rect;
    }

}

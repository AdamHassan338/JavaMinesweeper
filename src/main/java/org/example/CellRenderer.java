package org.example;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

public class CellRenderer {
    static int  spacing = 0;
    static Raylib.Color lineColour = BLACK;
    static Raylib.Color fillColour = WHITE;
    static int borderThinkness = 4;
    static Texture duckTexture = LoadTexture("Assets/duck.png");
    static void drawCell(int x,int y,boolean isFlagged, boolean isRevealed,boolean isMined){
        int xPos = x*spacing;
        int yPos = y * spacing;
        int topBorder = y==0 ? borderThinkness : 0;
        int leftBorder = x==0 ? borderThinkness : 0;

        Raylib.Color colour = GRAY;

        if(isRevealed) {
            colour = isMined ? RED : WHITE;

        }

        //draw outline
        DrawRectangle(xPos , yPos , spacing, spacing , BLACK);

        //Draw inside
        DrawRectangle(xPos + leftBorder , yPos + topBorder, spacing - leftBorder- borderThinkness , spacing- topBorder- borderThinkness, colour);
        if(isFlagged){
            DrawTexture(duckTexture,xPos + leftBorder,yPos + topBorder,WHITE );
        }
    }
}

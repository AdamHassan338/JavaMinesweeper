package org.example;


import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class CellRenderer {
    static int  spacing = 0;
    static Raylib.Color lineColour = BLACK;
    static Raylib.Color mineColour = RED;
    static Raylib.Color revealColour = WHITE;
    static Raylib.Color hidenColour = GRAY;
    static Raylib.Color textColour = BLUE;
    static int textSize = 30;
    static int borderThinkness = 4;
    static boolean debugMode = false;
    static Texture duckTexture;

    public static void init(){
        duckTexture = LoadTexture("Assets/duck.png");
    }

    static void drawCell(int x,int y,int neighbours,boolean isFlagged, boolean isRevealed,boolean isMined, boolean hovering){
        int xPos = x*spacing;
        int yPos = y * spacing;
        int topBorder = y==0 ? borderThinkness : 0;
        int leftBorder = x==0 ? borderThinkness : 0;

        Raylib.Color colour = hidenColour;

        if(isRevealed) {
            colour = isMined ? mineColour : revealColour;

        }

        if(debugMode && isMined)
            colour = mineColour;
        if(hovering && !isRevealed){
            Vector4 temp = new Vector4();
            temp.x((colour.r()*.5f)/255.f);
            temp.y((colour.g()*.5f)/255.f);
            temp.z((colour.b()*.5f)/255.f);
            temp.w(colour.a()/255.f);
            colour = ColorFromNormalized(temp);



        }


        //draw outline
        DrawRectangle(xPos , yPos , spacing, spacing , lineColour);

        //Draw inside
        DrawRectangle(xPos + leftBorder , yPos + topBorder, spacing - leftBorder- borderThinkness , spacing- topBorder- borderThinkness, colour);
        if(isFlagged){
            DrawTexture(duckTexture,xPos + leftBorder +(spacing-duckTexture.width())/2,yPos + topBorder + +(spacing-duckTexture.height())/2,WHITE );
        }

        int textWidth = MeasureText(String.valueOf(neighbours),textSize);


        if((isRevealed || debugMode) && neighbours>0 && !isMined)
        DrawText(String.valueOf(neighbours),(xPos + leftBorder)+(spacing - textWidth)/2,(yPos + topBorder) + (spacing-textSize)/2,textSize, textColour);
    }
}

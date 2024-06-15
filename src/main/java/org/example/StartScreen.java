package org.example;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import javax.imageio.plugins.tiff.TIFFTag;

import static com.raylib.Jaylib.*;

public class StartScreen extends Game{

    Button easy;
    Button medium;
    Button hard;
    String title;
    Button[] buttons = new Button[3];



    String pressedButton;
    StartScreen(String title){
        this.title = title;
        easy = new Button("Easy",0.5f,0.4f,300,100, WHITE);
        hard = new Button("Medium",0.5f,0.6f,300,100, WHITE);
        medium = new Button("Hard",0.5f,0.8f,300,100, WHITE);
        buttons[0] = easy;
        buttons[1] = medium;
        buttons[2] = hard;
        pressedButton = new String();
    }

    @Override
    void draw() {
    int width = (int) (GetScreenWidth() * 0.5);
    int height = (int)  (GetScreenHeight() * 0.1);

    rlClearScreenBuffers();
    BeginDrawing();
        int textSize = 50;

        int textWidth = MeasureText(title,textSize);
        DrawText(title,(GetScreenWidth() - textWidth)/2, (int) (GetScreenHeight()*0.1f),textSize,WHITE);


        for(Button b : buttons){
            b.draw();
        }
    EndDrawing();

    }
    void update(){
        handleInputs();
    }

    private void handleInputs(){
        pressedButton= "";

        for(Button b : buttons){
            b.setState(Button.State.idle);
            if(CheckCollisionPointRec(mousePos,b.rect)){
                b.setState(Button.State.hovered);
                if(IsMouseButtonPressed(MOUSE_BUTTON_LEFT))
                    pressedButton = b.getText();
            }
        }


    }

    public String getPressedButton() {
        return pressedButton;
    }

}

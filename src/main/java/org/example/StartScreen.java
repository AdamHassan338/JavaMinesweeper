package org.example;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class StartScreen extends Game{

    Button Start;

    public String getPressedButton() {
        return pressedButton;
    }

    String pressedButton;
    StartScreen(String Title){
        Start = new Button("Start",0.5f,0.5f,300,100,WHITE);
    }

    @Override
    void draw() {
    int width = (int) (GetScreenWidth() * 0.5);
    int height = (int)  (GetScreenHeight() * 0.1);

    rlClearScreenBuffers();
    BeginDrawing();
    Start.draw();
    EndDrawing();

    }
    void update(){
        handleInputs();
    }

    private void handleInputs(){
        if(!IsMouseButtonPressed(MOUSE_BUTTON_LEFT))
            return;
        pressedButton= "";
        if(CheckCollisionPointRec(mousePos,Start.rect)){
            pressedButton = Start.getText();
        }
    }

}

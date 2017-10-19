package com.spritetech.tetronic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Board b = new Board();
        setContentView(new GamePanel(this,b));
    }


}

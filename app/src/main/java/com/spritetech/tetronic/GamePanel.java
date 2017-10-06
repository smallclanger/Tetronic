package com.spritetech.tetronic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ponsfords on 05/07/2017.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SPDrawable
{
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    Context context=null;
    ArrayList<Point> touchPoints = new ArrayList<>();
    Bitmap[] lettertileOriginals = null;
    Bitmap[] lettertileRescaled = null;
    private MainThread thread;
    private Background bg;
    private Random rand = new Random();
    private Board board = null;

    private int[][] landed = new int[16][10];

    public GamePanel(Context context, Board board)
    {
        super(context);
        this.context=context;
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
        this.board=board;

        Resources resources = context.getResources();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

      //  Bitmap bgbm = BitmapFactory.decodeResource(getResources(), R.drawable.download);
       // bg = new Background(Utils.getResizedBitmap(bgbm, getWidth(), getHeight()));

        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
        int o = getResources().getConfiguration().orientation;// == Configuration.ORIENTATION_LANDSCAPE

//        board.setDimensions(getWidth(), getWidth(), 0, (getHeight() - getWidth()) / 2);
  //      board.LoadTileImages(BitmapFactory.decodeResource(getResources(), R.drawable.bronzetiles), 11, 11);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);

    }

    public void onUpdate()
    {
        board.onUpdate();

    }

    private void drawBlock(Canvas canvas, int x, int y, int c)
    {
        Paint blockPaint = new Paint();
        blockPaint.setColor(c);
        int blockStartPixel_x = x * 20;
        int blockStartPixel_y = y*20;
        canvas.drawRect(blockStartPixel_x,blockStartPixel_y,blockStartPixel_x+20,blockStartPixel_y+20,blockPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);


        bg.onDraw(canvas);

        for (int row = 0; row < landed.length; row++) {
            for (int col = 0; col < landed[row].length; col++) {
                if (landed[row][col] != 0) {
                    //draw block at position corresponding to row and col
                    //remember, row gives y-position, col gives x-position
                    drawBlock(canvas,col,row,Color.WHITE);
                }
            }
        }


        // draw outline for board

        if (board == null)
            return;
        Paint textPaint=new Paint();
        textPaint.setTextSize(40);
        textPaint.setColor(Color.YELLOW);

        board.onDraw(canvas);

        textPaint.setColor(Color.YELLOW);
       // canvas.drawText(board.getName(), 0, 0, textPaint);
        textPaint.setColor(Color.CYAN);
     //   canvas.drawText(board.getBuildingWord(), 0, 70, textPaint);

        //canvas.drawText(getResources().getString(R.string.scoretext) + String.valueOf(board.getScoreDisplayed()), 0, 50, textPaint);
      //  canvas.drawText("Theme Words:" + String.valueOf(board.getThemeWordsNotFound()), 0, 50, textPaint);

        textPaint.setTextSize(20);

  //      board.drawThemeWords(canvas, 0, board.getyBoardPos() + 22 + board.getBoardHeight(), getWidth(), getHeight() - (board.getyBoardPos() + 22 + board.getBoardHeight()));


        // Idea..

        // under the board represent the words to find with boxes for the letters:
        // when each word is found reveal the word
        // need to find best way to layout the words eg long word next to short word.

        // eg word lengths = 3,5,9,6,4 ,5
        // 9 + 3
        // 6 + 4
        // 5+ 5

        // quick solution
        // aim to show 3 rows of words
        // total letters = ... /3


    }


}
package com.spritetech.tetronic;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by ponsfords on 05/07/2017.
 */

public class FallingBlock implements SPDrawable {


    private ArrayList<int[][]> blockTypes = new ArrayList<>();

    public int[][] shape;

    // ####
    private int[][] block_0 = new int[][]{{1,1,1,1}};

    // ##
    // ##
    private int[][] block_1 = new int[][]{{1,1},{1,1}};

    //
    // #
    // ##
    //  #
    private int[][] block_2 = new int[][]{{1,0},{1,1},{0,1}};

    public FallingBlock()
    {
        // create random shape

        blockTypes.add(block_0);
        blockTypes.add(block_1);
        blockTypes.add(block_2);
        /*

        0 = ####

        1 = ##
            ##

        #
        ##
         #

          #
         ##
         #

         #
         ##
         #

         ##
         #
         #

         ##
          #
          #


         */

        shape=blockTypes.get(0);
    }

    public void rotateClockwise()
    {

        int rowNum = shape.length;
        int colNum = shape[0].length;
        int[][] temp = new int[rowNum][colNum];

        for(int i=0; i<shape[0].length; i++){
            for(int j=shape.length-1; j>0; j--){
                if( shape[i][j]==1)
                    temp[j][i] = shape[i][j];
            }
        }


        shape = temp;
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void onUpdate() {

    }
}

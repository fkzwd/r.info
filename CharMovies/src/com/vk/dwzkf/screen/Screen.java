package com.vk.dwzkf.screen;

import com.vk.dwzkf.animation.Animation;
import com.vk.dwzkf.animation.Image;

import java.util.LinkedList;
import java.util.List;

public class Screen {
    public static final int DEFAULT_ITERATIONS_FOR_STEP = 0;
    public static final int DEFAULT_SHOW_ONE_PICTURE_TIMES = 1;
    public static final int MOVING_LEFT = 0;
    public static final int MOVING_RIGHT = 1;

    private final int BORDER_SIZE;
    private final int BORDER_TOP;
    private final int BORDER_BOT;

    private int iterationsForStep = DEFAULT_ITERATIONS_FOR_STEP;
    private int showOnePictureTimes = DEFAULT_SHOW_ONE_PICTURE_TIMES;

    private Character[][] screen;
    private final int width;
    private final int height;
    private Animation animation;
    private double position = 0;
    private int counter = 0;
    private int moveMode = MOVING_RIGHT;
    private int[] staticX;
    private int[] staticY;

    public Screen(int width, int height, int borderSize) {
        screen = new Character[height + borderSize * 2][width];
        for (int i = 0; i<screen.length; i++) {
            for (int j = 0; j<screen[i].length; j++) {
                screen[i][j]=' ';
            }
        }
        staticX = new int[screen.length];
        staticY = new int[screen[0].length];
        BORDER_SIZE = borderSize;
        BORDER_TOP = BORDER_SIZE;
        BORDER_BOT = BORDER_SIZE;
        this.width = width;
        this.height = height;
    }

    public int getIterationsForStep() {
        return iterationsForStep;
    }

    public void setIterationsForStep(int iterationsForStep) {
        this.iterationsForStep = iterationsForStep;
    }

    public int getShowOnePictureTimes() {
        return showOnePictureTimes;
    }

    public void setShowOnePictureTimes(int showOnePictureTimes) {
        this.showOnePictureTimes = showOnePictureTimes;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void fillLineXStatic(int i, char c) {
        if (i<0 || i>=screen.length) return;
        staticX[i] = 1;
        for (int j = 0; j<screen[i].length; j++) {
            screen[i][j] = c;
        }
    }

    public void fillLineYStatic(int j, char c) {
        if (j<0 || j>=screen.length) return;
        staticY[j] = 1;
        for (int i = 0; i<screen.length; i++) {
            screen[i][j] = c;
        }
    }

    public void clearStaticX(int i) {
        if (i<0 || i>=screen.length) {
            return;
        }
        staticX[i] = 0;
        for (int j = 0; j<screen[i].length; j++) {
            screen[i][j] = ' ';
        }
    }

    public void clearStaticY(int j) {
        if (j<0 || j>=screen[0].length) {
            return;
        }
        staticY[j] = 0;
        for (int i = 0; i<screen.length; i++) {
            screen[i][j] = ' ';
        }
    }

    public void setMovingMode(int mode) {
        if (mode != MOVING_LEFT && mode != MOVING_RIGHT) {
            this.moveMode = MOVING_RIGHT;
            return;
        }
        this.moveMode = mode;
    }

    public Character[][] next() {
        counter++;
        Image m = animation.getImage();
        if (counter == showOnePictureTimes) {
            m = animation.getImageAndNext();
            counter = 0;
        }
        position = position % screen[0].length;

        char[][] img = m.getImage();
        for (int i = BORDER_TOP; i < screen.length - BORDER_BOT; i++) {
            for (int j = 0; j < screen[i].length; j++) {
                if (staticY[j]==0 && staticX[i]==0) {
                    screen[i][j] = ' ';
                }
            }
        }
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[i].length; j++) {
                int x = ((int) position + j) % screen[0].length;
                if (moveMode == MOVING_LEFT) {
                    x = (screen[0].length - ((int) position + 1) + j)%screen[0].length;
                }
                int y = i;
                y = y % getHeight() + BORDER_TOP;
                if (staticY[x]==0 && staticX[y]==0) {
                    screen[y][x] = img[i][j];
                }
            }
        }
        if (iterationsForStep != 0) {
            position += 1.0 / iterationsForStep;
        }
        return screen;
    }
}

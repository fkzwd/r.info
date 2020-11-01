package com.vk.dwzkf.screen;

import com.vk.dwzkf.animation.Animation;
import com.vk.dwzkf.animation.Image;

public class Screen {
    private static final int DEFAULT_ITERATIONS_FOR_STEP = 0;
    private static final int DEFAULT_SHOW_ONE_PICTURE_TIMES = 1;

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

    public Screen(int width, int height, int borderSize) {
        screen = new Character[height+borderSize*2][width];
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

    public Character[][] next() {
        counter++;
        Image m = animation.getImage();
        if (counter == showOnePictureTimes) {
            m = animation.getImageAndNext();
            counter=0;
        }
        position = position % screen[0].length;

        char[][] img = m.getImage();
        for (int i = 0; i< screen.length; i++) {
            for (int j = 0; j< screen[i].length; j++) {
                screen[i][j] = ' ';
            }
        }
        for (int i = 0; i<img.length; i++) {
            for (int j = 0; j<img[i].length; j++) {
                int x = ((int)position+j) % screen[0].length;
                int y = i;
                y = y % getHeight() + BORDER_TOP;
                screen[y][x] = img[i][j];
            }
        }
        if (iterationsForStep!=0) {
            position += 1.0 / iterationsForStep;
        }
        return screen;
    }
}

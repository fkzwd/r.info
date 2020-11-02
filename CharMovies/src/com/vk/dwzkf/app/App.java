package com.vk.dwzkf.app;

import com.vk.dwzkf.screen.Screen;
import com.vk.dwzkf.util.ImageUtils;

public class App {
    private static final boolean WITH_NEW_LINE = true;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Add arg man/car/snow to program and run");
            return;
        }
        String animation = args[0];
        App app = new App();
        if (animation.equals("man")) {
            app.testConsoleMan();
        } else if (animation.equals("car")) {
            app.testConsoleCar();
        } else if (animation.equals("snow")) {
            app.testSnow();
        } else if (animation.equals("tank")) {
            app.testTank();
        }
        else {
            System.out.println("Add arg man/car/snow to program and run");
        }
    }

    private void testTank() throws Exception {
        int sleepTime = 11;
        int iterations = 10000;
        Screen screen = new Screen(140, 15, 1);
        screen.setAnimation(ImageUtils.getAnimationTank());
        screen.setIterationsForStep(4);
        screen.setShowOnePictureTimes(5);
        screen.setMovingMode(Screen.MOVING_LEFT);
        screen.fillLineXStatic(screen.getAnimation().getCharsImage().length, '─');
        testScreenAnimation(screen, sleepTime, iterations);
    }

    private void testSnow() throws Exception {
        int sleepTime = 15;
        int iterations = 1000;
        Screen screen = new Screen(50, 10, 1);
        screen.setAnimation(ImageUtils.getAnimationSnow());
        screen.setIterationsForStep(0);
        screen.setShowOnePictureTimes(20);
        testScreenAnimation(screen, sleepTime, iterations);
    }

    private void testConsoleCar() throws Exception {
        int sleepTime = 15;
        int iterations = 1000;
        Screen screen = new Screen(120, 10, 1);
        screen.setAnimation(ImageUtils.getAnimationCar());
        screen.setIterationsForStep(2);
        screen.setShowOnePictureTimes(5);
        testScreenAnimation(screen, sleepTime, iterations);
    }

    private void testConsoleMan() throws Exception {
        int sleepTime = 40;
        int iterations = 1000;
        Screen screen = new Screen(50, 10, 1);
        screen.setAnimation(ImageUtils.getAnimationMan());
        screen.setIterationsForStep(screen.getAnimation().imagesCount() / 5);
        screen.setShowOnePictureTimes(25 / screen.getAnimation().imagesCount());
        testScreenAnimation(screen, sleepTime, iterations);
    }

    private void testScreenAnimation(Screen screen, int sleepTime, int times) throws Exception {
        for (int i = 0; i < times; i++) {
            String s = ImageUtils.asString(screen.next(), WITH_NEW_LINE);
            System.out.println(s);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

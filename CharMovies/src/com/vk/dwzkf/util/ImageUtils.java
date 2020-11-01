package com.vk.dwzkf.util;

import com.vk.dwzkf.animation.Animation;
import com.vk.dwzkf.animation.Image;
import com.vk.dwzkf.screen.Screen;

import java.util.Arrays;
import java.util.List;

public class ImageUtils {
    private static final int WIDTH = 60;
    private static final StringBuilder sb = new StringBuilder();

    public static Animation getAnimationCar() {
        Animation animation = new Animation();
        String s1 =
                "                         _________________                     \n" +
                        "*                       /       |         \\                   \n" +
                        " .*               _____/________|__________\\__________        \n" +
                        "    *..  * .    __|.|    ..       ..               |..\\       \n" +
                        "       *     * |__|     __                      __     \\      \n" +
                        "                  \\____/..\\____________________/..\\___/     \n" +
                        "                       \\__/                    \\__/          ";
        Image image1 = imageFromString(s1);

        String s2 =
                "                         _________________                     \n" +
                        ".                       /       |         \\                   \n" +
                        " .**              _____/________|__________\\__________        \n" +
                        "    *..  * .*   __|.|    ..       ..               |..\\       \n" +
                        "       *     . |__|     __                      __     \\      \n" +
                        "                  \\____/..\\____________________/..\\___/     \n" +
                        "                       \\__/                    \\__/          ";
        Image image2 = imageFromString(s2);

        String s3 =
                "                         _________________                     \n" +
                        "*                       /       |         \\                   \n" +
                        " .*.              _____/________|__________\\__________        \n" +
                        "    **.  .*..   __|.|    ..       ..               |..\\       \n" +
                        "       .*   .* |__|     __                      __     \\      \n" +
                        "                  \\____/..\\____________________/..\\___/     \n" +
                        "                       \\__/                    \\__/          ";

        Image image3 = imageFromString(s3);

        String s4 =
                "                         _________________                     \n" +
                        "*                       /       |         \\                   \n" +
                        " .*               _____/________|__________\\__________        \n" +
                        "    *..  *..    __|.|    ..       ..               |..\\       \n" +
                        "       *     * |__|     __                      __     \\      \n" +
                        "                  \\____/..\\____________________/..\\___/     \n" +
                        "                       \\__/                    \\__/          ";

        Image image4 = imageFromString(s4);
        animation.addImage(image1);
        animation.addImage(image2);
        animation.addImage(image3);
        animation.addImage(image4);
        return animation;
    }

    public static Animation getAnimationMan() {
        Animation animation = new Animation();
        List<String> strings = Arrays.asList("   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /|\\    \n" +
                        "  / | \\   \n" +
                        "   / \\    \n" +
                        "  /   \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /|\\    \n" +
                        "  / | |   \n" +
                        "   / \\    \n" +
                        "  |   \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /|\\    \n" +
                        "  / | |   \n" +
                        "   / \\    \n" +
                        "   |  \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /|\\    \n" +
                        "  / ||    \n" +
                        "   / \\    \n" +
                        "   |  \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /|\\    \n" +
                        "  | /     \n" +
                        "   / \\    \n" +
                        "   |  \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /||    \n" +
                        "   |/     \n" +
                        "   | \\    \n" +
                        "   |  \\   ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /||    \n" +
                        "   |/     \n" +
                        "   | \\    \n" +
                        "   | |    ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   /||    \n" +
                        "    X     \n" +
                        "   | \\    \n" +
                        "   | |    ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   |||    \n" +
                        "    X     \n" +
                        "   | \\    \n" +
                        "   | |    ",
                "   ___    \n" +
                        "  /   \\   \n" +
                        "  \\___/   \n" +
                        "    |     \n" +
                        "   |||    \n" +
                        "    X     \n" +
                        "   | |    \n" +
                        "   | |    ");
        strings.stream().forEach(s -> animation.addImage(imageFromString(s)));
        for (int i = strings.size()-1; i>=0; i--) {
            animation.addImage(imageFromString(strings.get(i)));
        }
        return animation;
    }

    public static Animation getAnimationSnow() {
        Animation animation = new Animation();
        List<String> list = Arrays.asList(" *    \n" +
                "      \n" +
                "      \n" +
                "      \n" +
                "______",
                " |    \n" +
                        " *    \n" +
                        "      \n" +
                        "      \n" +
                        "______",
                " .    \n" +
                        " |    \n" +
                        " *    \n" +
                        "      \n" +
                        "______",
                " .    \n" +
                        " .    \n" +
                        " |    \n" +
                        " *    \n" +
                        "______",
                "      \n" +
                        " .    \n" +
                        " .    \n" +
                        " |    \n" +
                        "_*____",
                "      \n" +
                        "      \n" +
                        " .    \n" +
                        " |    \n" +
                        "_.____",
                "      \n" +
                        "      \n" +
                        "      \n" +
                        " .    \n" +
                        "______",
                "      \n" +
                        "      \n" +
                        "      \n" +
                        "      \n" +
                        "______");
        list.forEach(s -> animation.addImage(imageFromString(s)));
        return animation;
    }

    public static void showArr(char[][] arr) {
        for (int i = 0; i<arr.length; i++) {
            for (int j = 0; j<arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void showArr(Character[][] arr) {
        for (int i = 0; i<arr.length; i++) {
            for (int j = 0; j<arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static Image imageFromString(String s) {
        String[] img1Strings = s.split("\n");
        char[][] image1Chars = new char[img1Strings.length][img1Strings[0].length()];
        for (int i = 0; i<img1Strings.length; i++) {
            char[] chars = img1Strings[i].toCharArray();
            image1Chars[i] = chars;
        }
        return new Image(image1Chars);
    }

    public static String asString(Character[][] arr, boolean withNewLine) {
        clearStringBuilder();
        for (int i = 0; i<arr.length; i++) {
            for (int j = 0; j<arr[i].length; j++) {
                sb.append(arr[i][j]);
            }
            if (withNewLine) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static void clearStringBuilder() {
        sb.delete(0,sb.length());
    }
}

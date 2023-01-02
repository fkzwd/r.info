package com.vk.dwzkf.utils.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * @author Roman Shageev
 * @since 31.12.2022
 */
public class Main {
    public static void main(String[] args)  throws Exception {
        Checker checker = new Checker();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            final int minLen = random.nextInt(60) + 3;
            System.out.print("Generate "+minLen+": ");
            System.out.println(checker.generate(minLen));
        }
        System.out.println(checker.check("0x-(1+5)+5-9+(5x/82,5^462)-(-8)*(-1x^2)+93/2*(-8x/5x/2)"));
//        final int[] terminalModesArray = checker.findTerminalModesArray(checker.getCharMode(')'), 0);
//        StringBuilder sb = new StringBuilder();
//        checker.appendModes(sb, new Random(), terminalModesArray);
//        System.out.println(sb.toString());
//        Stack<Integer> stack = new Stack<>();
//        stack.add(4);
//        checker.findWay(stack, 84, 0);
    }

}

package com.vk.dwzkf.utils.calc;

import java.util.*;
import java.util.stream.Collectors;

public class Checker {
    private static final char[] MATH_OPERATIONS = {'+','-','/','*','^'};                //1  --  1
    private static final char[] HOOKS = {'(',')'};                                     //2  --  1 << 1
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8','9'};    //4  --  1 << 2
    private static final char[] DELIMITERS = {'.',','};                                    //8  --  1 << 3
    private static final char[] MINUS = {'-'};                                         //16 --  1 << 4
    private static final char[] CLOSE_HOOK = {')'};                                     //32 --  1 << 5
    private static final char[] OPEN_HOOK = {'('};                                      //64 --  1 << 6
    private static final char[] VARIABLES = {'x'};

    private final Map<Integer, char[]> maskMapping = new LinkedHashMap<>();

    Map<Integer, Integer> mapping = new LinkedHashMap<>();

    {
        maskMapping.put(1, MATH_OPERATIONS);
        maskMapping.put(4, DIGITS);
        maskMapping.put(8, DELIMITERS);
        maskMapping.put(16, MINUS);
        maskMapping.put(32, CLOSE_HOOK);
        maskMapping.put(64, OPEN_HOOK);
        maskMapping.put(128, VARIABLES);
        mapping.put(getModeOf(MATH_OPERATIONS), getModeOf(DIGITS, OPEN_HOOK));
        mapping.put(getModeOf(OPEN_HOOK), getModeOf(DIGITS, OPEN_HOOK, MINUS));
        mapping.put(getModeOf(CLOSE_HOOK), getModeOf(MATH_OPERATIONS, CLOSE_HOOK));
        mapping.put(getModeOf(DIGITS), getModeOf(MATH_OPERATIONS, DIGITS, DELIMITERS, CLOSE_HOOK, VARIABLES));
        mapping.put(getModeOf(DELIMITERS), getModeOf(DIGITS));
        mapping.put(getModeOf(VARIABLES), getModeOf(MATH_OPERATIONS));
    }

    public final int EXIT_MODE_1 = 1 + 4 + 8 + 32 + 128;
    public final int EXIT_MODE_2 = 1 + 32;
    public final int START_MODE = 4 + 16 + 64 + 128;
    public final int BRACKETS_MAX_DEPTH = 1;

    public boolean check(String expression) {
        if (expression==null || expression.isBlank()) return false;

        char[] chars = removeBlanks(expression).toCharArray();
        if (chars.length<3) return false;

        int i = 0;
        return check(chars,i, START_MODE, 0,0);
    }

    public String generate(int minLen) {
        if (minLen < 3) {
            throw new IllegalArgumentException("Length cannot be < 3");
        }
        int mode = START_MODE;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int openHooks = 0;
        int delimitersCount = 0;

        while (true) {
            char currChar = getRandomValidChar(mode, random);
            if (delimitersCount == 1 && contains(DELIMITERS, currChar)) {
                mode = mode ^ getModeOf(DELIMITERS);
                continue;
            }
            if (openHooks == 0 && contains(CLOSE_HOOK, currChar)) {
                mode = mode ^ getModeOf(CLOSE_HOOK);
                continue;
            }
            if (openHooks == BRACKETS_MAX_DEPTH && contains(OPEN_HOOK, currChar)) {
                mode = mode ^ getModeOf(OPEN_HOOK);
                continue;
            }
            if (contains(CLOSE_HOOK, currChar)) openHooks--;
            if (contains(DELIMITERS, currChar))  delimitersCount++;
            if (contains(OPEN_HOOK, currChar)) openHooks++;

            sb.append(currChar);
            mode = getCharMode(currChar);
            if (sb.length() < minLen) {
               continue;
            }
            //terminal
            if (openHooks != 0 && (mode == EXIT_MODE_2 || mode == EXIT_MODE_1)) {
                while (openHooks > 0) {
                    sb.append(getRandomValidChar(getModeOf(CLOSE_HOOK), random));
                    openHooks--;
                }
                break;
            }

            if (mode == EXIT_MODE_1 || mode == EXIT_MODE_2) break;

            int[] terminalModes = findTerminalModesArray(mode, openHooks);
            if (terminalModes.length == 0) {
                throw new IllegalStateException("Unable to find terminal way!");
            }
            appendModes(sb, random, terminalModes);
            break;
        }

        return sb.toString();
    }

    public void appendModes(StringBuilder sb, Random random, int[] terminalModes) {
        for (int terminalMode : terminalModes) {
            sb.append(getRandomValidChar(terminalMode, random));
        }
    }


    //find array of single modes for reach to terminal state
    public int[] findTerminalModesArray(int mode, int openHooks) {
        Stack<Integer> stack = new Stack<>();
        findWay(stack, mode, openHooks);
        int[] result = new int[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            result[i] = stack.get(i);
        }
        return result;
    }

    public int findWay(Stack<Integer> stack, int mode, int openHooks) {
        if ((mode == EXIT_MODE_1 || mode == EXIT_MODE_2) && openHooks == 0) {
            return mode;
        }
        if (openHooks != 0 && (getModeOf(CLOSE_HOOK) & mode) != 0) {
            while (openHooks > 0) {
                stack.add(getModeOf(CLOSE_HOOK));
                openHooks--;
            }
            return EXIT_MODE_2;
        }
        int[] modes = getAvailableModes(mode);
        int result = -1;
        for (int currentMode : modes) {
            if (stack.contains(currentMode)) continue;
            int nextMode = getCharMode(getCharsForMode(currentMode)[0]);
            stack.push(currentMode);
            result = findWay(stack, nextMode, openHooks);
            if (result != EXIT_MODE_1 && result != EXIT_MODE_2) {
                stack.pop();
            } else {
                return result;
            }
        }
        return result;
    }

    public int getModeOf(char[] array) {
        return maskMapping.entrySet().stream()
                .filter(e -> Arrays.equals(e.getValue(), array))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public int getModeOf(char[]... array) {
        int result = 0;
        for (char[] chars : array) {
            result = result | getModeOf(chars);
        }
        return result;
    }

    public char getRandomValidChar(int mode, Random random) {
        int[] available = getAvailableModes(mode);
        int choosenMode = available[random.nextInt(available.length)];
        char[] availableWords = getCharsForMode(choosenMode);
        int choosenChar = random.nextInt(availableWords.length);
        return availableWords[choosenChar];
    }

    public char[] getCharsForMode(int choosenMode) {
        char[] chars = maskMapping.get(choosenMode);
        if (chars == null)
            throw new IllegalArgumentException("Not found chars for mode = "+choosenMode);
        return chars;
    }

    public int[] getAvailableModes(int mode) {
        if (mode == -1) {
            throw new IllegalArgumentException("No chars for mode = -1");
        }
        int[] modes = new int[32];
        int idx = 0;
        for (int i = 0; i < 32; i++) {
            if ((mode & (1 << i)) != 0) {
                modes[idx++] = 1 << i;
            }
        }
        return Arrays.copyOf(modes, idx);
    }

    public boolean check(char[] chars, int i, int mode, int openHooks, int delimeters) {
        if (mode==-1) {
            return false;
        }
        if (openHooks<0) {
            return false;
        }
        if (delimeters>1) {
            return false;
        }
        if (i >=chars.length) {
            if (mode != EXIT_MODE_1 && mode != EXIT_MODE_2) {
                return false;
            }
            else return true;
        }
        else {
            boolean result = isAvailable(chars[i], mode);
            if (!result) {
                return false;
            }
            if (contains(CLOSE_HOOK,chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks-1, 0);
            }
            if (contains(OPEN_HOOK, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks+1, 0);
            }
            if (contains(DELIMITERS, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks, delimeters+1);
            }
            if (contains(DIGITS, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks, delimeters);
            }
            return check(chars, i+1, getCharMode(chars[i]), openHooks, 0);
        }
    }

    public int getCharMode(char c) {
        return maskMapping.entrySet()
                .stream()
                .filter(e -> contains(e.getValue(), c))
                .findFirst()
                .map(m -> mapping.getOrDefault(m.getKey(), -1))
                .orElse(-1);
    }

    public boolean isAvailable(char c, int mode) {
        boolean result = false;
        List<Integer> modes = maskMapping.keySet().stream()
                .filter(k -> (k & mode) != 0)
                .collect(Collectors.toList());
        for (Integer modeKey : modes) {
            result = result || contains(maskMapping.get(modeKey), c);
        }
        return result;
    }

    public boolean contains(char[] source, char c) {
        for (char ch : source) {
            if (ch==c) return true;
        }
        return false;
    }

    public String removeBlanks(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
           if (c == ' ' || c <= 13) continue;
           sb.append(c);
        }
        return sb.toString();
    }
}

package com.vk.dwzkf.utils.calc;

public class Checker {
    private final char[] mathOperations = {'+','-','/','*','^'};                //1  --  1
    private final char[] hooks = {'(',')'};                                     //2  --  1 << 1
    private final char[] digits = {'0','1','2','3','4','5','6','7','8','9'};    //4  --  1 << 2
    private final char[] delimiters = {'.',','};                                    //8  --  1 << 3
    private final char[] minus = {'-'};                                         //16 --  1 << 4
    private final char[] closeHook = {')'};                                     //32 --  1 << 5
    private final char[] openHook = {'('};                                      //64 --  1 << 6

    public Checker() {

    }

    public boolean check(String expression) {
        if (expression==null || expression.isBlank()) return false;

        char[] chars = removeBlanks(expression).toCharArray();
        if (chars.length<3) return false;

        int i = 0;
        int mode = 4+16+64;
        return check(chars,i,mode, 0,0);
    }

    private boolean check(char[] chars, int i, int mode, int openHooks, int delimeters) {
        if (mode==-1) return false;
        if (openHooks<0) return false;
        if (delimeters>1) return false;
        if (i >=chars.length) {
            if (mode != 1+4+8+32 && mode != 1+32) return false;
            else return true;
        }
        else {
            boolean result = isAvailable(chars[i], mode);
            if (!result) return false;
            if (contains(closeHook,chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks-1, 0);
            }
            if (contains(openHook, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks+1, 0);
            }
            if (contains(delimiters, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks, delimeters+1);
            }
            if (contains(digits, chars[i])) {
                return check(chars, i+1, getCharMode(chars[i]), openHooks, delimeters);
            }
            return check(chars, i+1, getCharMode(chars[i]), openHooks, 0);
        }
    }

    private int getCharMode(char c) {
        if (contains(mathOperations, c)) {
            return 4+64;
        }
        else if (contains(openHook, c)) {
            return 4+16+64;
        }
        else if (contains(closeHook, c)) {
            return 1+32;
        }
        else if (contains(digits,c)) {
            return 1+4+8+32;
        }
        else if (contains(delimiters, c)) {
            return 4;
        }
        return -1;
    }

    private boolean isAvailable(char c, int mode) {
        boolean result = false;
        if ((mode & 1) != 0) {
            result = contains(mathOperations,c);
        }
        if ((mode & (1<<2)) != 0) {
            result = result || contains(digits, c);
        }
        if ((mode & (1<<3)) != 0) {
            result = result || contains(delimiters, c);
        }
        if ((mode & (1<<4)) != 0) {
            result = result || contains(minus, c);
        }
        if ((mode & (1<<5)) != 0) {
            result = result || contains(closeHook, c);
        }
        if ((mode & (1<<6)) != 0) {
            result = result || contains(openHook,c);
        }
        return result;
    }

    private boolean contains(char[] source, char c) {
        for (char ch : source) {
            if (ch==c) return true;
        }
        return false;
    }

    public String removeBlanks(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (contains(digits,c) || contains(delimiters,c) || contains(hooks,c) || contains(mathOperations,c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

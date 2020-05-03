package com.example.pattern_demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jt
 * @date 2019-6-24
 */
public class Test {

    public static void main(String[] args) {

        Pattern compile = Pattern.compile("[a-z]{2}");

        Matcher matcher = compile.matcher("abac");
        boolean b0 = matcher.matches();
        boolean b1 = matcher.find();
        boolean b2 = matcher.lookingAt();
        System.out.println(b0);//false
        System.out.println(b1);//true
        System.out.println(b2);//true
    }
}

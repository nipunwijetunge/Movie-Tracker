package com.example.movietracker.utils;

import android.text.Spanned;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * this class is defined to implement minimum and maximum ranges for edit text fields
 *
 * @author Nipun Wijetunge
 * @version 1.0
 * @since 2021-04-13*/
public class MinMaxFilter implements InputFilter{
    private int min, max;

    public MinMaxFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public MinMaxFilter(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}

package de.sebastianknopf.javascriptapp;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.Random;

public class InteractiveInterface {

    @JavascriptInterface
    public int getRandomNumber() {
        Random random = new Random();

        return random.nextInt(100);
    }

    @JavascriptInterface
    public void setCalculationResult(int result) {
        Log.d("InteractiveInterface", String.format("Result: %d", result));
    }

    @JavascriptInterface
    public void verbose(String log) {
        Log.v("InteractiveInterface", log);
    }

}

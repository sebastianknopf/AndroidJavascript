package de.sebastianknopf.javascriptapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class NativeInterface {

    private MainActivity context;

    public NativeInterface(MainActivity context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendMessage(String message) {
        this.context.runOnUiThread(() -> {
            Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
        });
    }
}

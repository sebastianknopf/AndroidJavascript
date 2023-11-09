package com.android.javascript;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class JavascriptInterpreter {

    protected WebView webView;

    protected OnJavascriptResultListener onResultListener;
    protected OnJavascriptErrorListener onErrorListener;

    public JavascriptInterpreter(Context context) {
        this.webView = new WebView(context);
        this.webView.getSettings().setJavaScriptEnabled(true);

        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                    if (onErrorListener != null) {
                        onErrorListener.onJavascriptError(consoleMessage.message());
                    }
                }

                return super.onConsoleMessage(consoleMessage);
            }
        });

        this.webView.addJavascriptInterface(this, "javascriptinterpreter");
    }

    public JavascriptInterpreter(Context context, OnJavascriptResultListener resultListener) {
        this.webView = new WebView(context);
        this.webView.getSettings().setJavaScriptEnabled(true);

        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                    if (onErrorListener != null) {
                        onErrorListener.onJavascriptError(consoleMessage.message());
                    }
                }

                return super.onConsoleMessage(consoleMessage);
            }
        });

        this.webView.addJavascriptInterface(this, "javascriptinterpreter");

        this.onResultListener = resultListener;
    }

    public JavascriptInterpreter(Context context, OnJavascriptResultListener resultListener, OnJavascriptErrorListener errorListener)  {
        this.webView = new WebView(context);
        this.webView.getSettings().setJavaScriptEnabled(true);

        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                    if (onErrorListener != null) {
                        onErrorListener.onJavascriptError(consoleMessage.message());
                    }
                }

                return super.onConsoleMessage(consoleMessage);
            }
        });

        this.webView.addJavascriptInterface(this, "javascriptinterpreter");

        this.onResultListener = resultListener;
        this.onErrorListener = errorListener;
    }

    public void setOnResultListener(OnJavascriptResultListener listener) {
        this.onResultListener = listener;
    }

    public void setOnErrorListener(OnJavascriptErrorListener listener) {
        this.onErrorListener = listener;
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object interfaceObject, String name) {
        this.webView.addJavascriptInterface(interfaceObject, name);
    }

    public void execute(String javascriptCode) {
        this.webView.loadUrl("javascript:javascriptinterpreter.resultCallback(" + javascriptCode + ")");
    }

    @JavascriptInterface
    public void resultCallback(String result) {
        if (onResultListener != null) {
            onResultListener.onJavascriptResult(result);
        }
    }

}

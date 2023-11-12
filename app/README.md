# JavaScript Interpreter for Android
This project provides a very simple and lightweight interpreter for running local javascript files
within an android app. Internally, it uses an android WebView for evaluating javascript statements and
therefore, it es especially not meant for running large scripts of hundreds of KBs. To achieve this, please 
refer to [AndroidJSCode](https://github.com/ericwlange/AndroidJSCore) library instead.

The most interesting use case is loading dynamic code and dynamic implementations of small functions without using Reflection 
or dynamic features of Google Play.

## Basic Usage
To use the javascript interpreter, see the following example code in your android app:

```java
JavascriptInterpreter interpreter = new JavascriptInterpreter();
interpreter.setOnResultListener(result -> {
    // do whatever you want here with result of type String
});
interpreter.setOnErrorListener(error -> {
    // do whatever you want here with error message of type String
});

interpreter.execute("10 + 5"); // results in value '15' in onResultListener callback
```

As you see, the return value of your executed javascript code is passed into onResultListener callback parameter as String.

You can also execute small scripts (stored in *.js files in assets for example) by loading them into
a string value and executing them. See following javascript code as reference:

```javascript
(function() {
    let variable = 10;
    return variable * 10;
})();
```

Similar to the example above, the return value of the self-invoked function is passed into onResultListener callback parameter as String.

## Native Code
It is also possible to call native code from javascript. This is achieved by using the method `addJavascriptInterface(interface, name)`. You need to set an unique name for each interface you add. Your interface should be defined as follows:

```java
public class NativeInterface {

    @JavascriptInterface
    public int getRandomNumber() {
        Random random = new Random();

        return random.nextInt(100);
    }

    @JavascriptInterface
    public void setCalculationResult(int result) {
        Log.d("InteractiveInterface", String.format("Result: %d", result));
    }
}
```

Note the @JavascriptInterface annotations above the methods you want to expose to javascript. Then, add your interface with an unique name:

```java
interpreter.addJavascriptInterface(new NativeInterface(), "app");
```

Finally, you can call the native interface methods in javascript as follows:

```javascript
(function () {
    let number = app.getRandomNumber();
    // ...
})();
```
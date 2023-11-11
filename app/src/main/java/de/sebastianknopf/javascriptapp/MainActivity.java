package de.sebastianknopf.javascriptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.javascript.JavascriptInterpreter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "JavascriptInterpreter";

    private JavascriptInterpreter interpreter;

    private TextView txtResultOrError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.interpreter = new JavascriptInterpreter(this.getApplicationContext());
        this.interpreter.setOnResultListener(result -> {
            this.runOnUiThread(() -> {
                this.txtResultOrError.setText("OnResult: " + result);
            });

            Log.d(TAG, "OnResult: " + result);
        });
        this.interpreter.setOnErrorListener(error -> {
            this.runOnUiThread(() -> {
                this.txtResultOrError.setText("OnError: " + error);
            });

            Log.d(TAG, "OnError: " + error);
        });

        this.interpreter.addJavascriptInterface(new NativeInterface(this), "android");
        this.interpreter.addJavascriptInterface(new InteractiveInterface(), "api");

        this.txtResultOrError = this.findViewById(R.id.txtJsResultOrError);

        Button btnAddition = this.findViewById(R.id.btnAddition);
        btnAddition.setOnClickListener(view -> this.runAdditionScript());

        Button btnError = this.findViewById(R.id.btnError);
        btnError.setOnClickListener(view -> this.runErrorScript());

        Button btnInterface = this.findViewById(R.id.btnInterface);
        btnInterface.setOnClickListener(view -> this.runInterfaceScript());

        Button btnInterfaceII = this.findViewById(R.id.btnInterfaceII);
        btnInterfaceII.setOnClickListener(view -> this.runInterfaceIIScript());

        Button btnInline = this.findViewById(R.id.btnInline);
        btnInline.setOnClickListener(view -> this.runInlineScript());
    }

    private void runAdditionScript() {
        String addition = this.readAssetScript("addition.js", null);
        this.interpreter.execute(addition);
    }

    private void runErrorScript() {
        String error = this.readAssetScript("error.js", null);
        this.interpreter.execute(error);
    }

    private void runInterfaceScript() {
        String ivf = this.readAssetScript("interface.js", null);
        this.interpreter.execute(ivf);
    }

    public void runInterfaceIIScript() {
        String ivf = this.readAssetScript("interface2.js", null);
        this.interpreter.execute(ivf);
    }

    public void runInlineScript() {
        this.interpreter.execute("5 + 5");
    }

    private String readAssetScript(String scriptName, Charset charset) {
        try {
            InputStream is = this.getResources().getAssets().open(scriptName);
            byte[] buffer = new byte[1024];

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int length = is.read(buffer); length != -1; length = is.read(buffer)) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            is.close();
            byteArrayOutputStream.close();

            return charset == null ? new String(byteArrayOutputStream.toByteArray()) : new String(byteArrayOutputStream.toByteArray(), charset);
        } catch (IOException ex) {
        }

        return null;
    }
}
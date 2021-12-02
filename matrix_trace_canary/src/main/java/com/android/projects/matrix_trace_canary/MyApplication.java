package com.android.projects.matrix_trace_canary;

import android.app.Application;
import android.content.Context;

import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

public class MyApplication extends Application {
    private static Context sContext;

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();


        Matrix.Builder builder = new Matrix.Builder(this);
        TracePlugin tracePlugin = new TracePlugin(new TraceConfig.Builder()
                .enableEvilMethodTrace(true)
                .build());
        builder.plugin(tracePlugin);

        Matrix matrix = builder.build();
        Matrix.init(matrix);

        matrix.startAllPlugins();
        tracePlugin.getEvilMethodTracer().modifyEvilThresholdMs(200);
    }
}

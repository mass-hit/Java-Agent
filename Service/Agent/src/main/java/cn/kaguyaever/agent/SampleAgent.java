package cn.kaguyaever.agent;

import java.lang.instrument.Instrumentation;

public class SampleAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new SampleTransformer(), true);
    }
}

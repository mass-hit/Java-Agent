package cn.kaguyaever.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class SampleTransformer implements ClassFileTransformer {

    private static final String CLASS_NAME = "cn.kaguyaever.service.SampleService";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String cName = className.replaceAll("/", ".");
        if (!CLASS_NAME.equals(cName))
            return classfileBuffer;
        ClassPool pool = ClassPool.getDefault();
        CtClass cc;
        try {
            cc = pool.get(cName);
            CtMethod[] ctMethods = cc.getDeclaredMethods();
            for (CtMethod ctMethod : ctMethods) {
                ctMethod.insertBefore("System.out.println(\"" + ctMethod.getName() + " start" + "\");");
                ctMethod.insertAfter("System.out.println(\"" + ctMethod.getName() + " end" + "\");");
            }
            return cc.toBytecode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

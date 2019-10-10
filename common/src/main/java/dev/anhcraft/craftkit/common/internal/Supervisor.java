package dev.anhcraft.craftkit.common.internal;

public class Supervisor {
    public static final String INTERNAL_PACKAGE_1 = "dev.anhcraft.craftkit";
    public static final String INTERNAL_PACKAGE_2 = "sun";
    public static final String INTERNAL_PACKAGE_3 = "java";

    public static void checkAccess(){
        StackTraceElement stacktrace = Thread.currentThread().getStackTrace()[3];
        if(!stacktrace.getClassName().startsWith(INTERNAL_PACKAGE_1) && !stacktrace.getClassName().startsWith(INTERNAL_PACKAGE_2) && !stacktrace.getClassName().startsWith(INTERNAL_PACKAGE_3)){
            throw new UnsupportedOperationException();
        }
    }
}

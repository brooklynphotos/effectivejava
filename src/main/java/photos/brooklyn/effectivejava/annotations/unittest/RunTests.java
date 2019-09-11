package photos.brooklyn.effectivejava.annotations.unittest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {
    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (IllegalAccessException e) {
                    System.out.println("Not allowed to run method: " + m);
                } catch (InvocationTargetException e) {
                    System.out.println(m + " failed: " + e.getCause());
                } catch (RuntimeException rx) {
                    System.out.println("Some other error happented: "+m);
                    rx.printStackTrace();
                }
            } else if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                Class<? extends Throwable> expectedException = m.getAnnotation(ExceptionTest.class).value();
                try {
                    m.invoke(null);
                    System.out.printf("Failed test %s, was expecting exception %s%n",m, expectedException);
                } catch (InvocationTargetException ie) {
                    Throwable exc = ie.getCause();
                    if (expectedException.isInstance(exc)) {
                        passed++;
                    } else {
                        System.out.printf("Test %s failed: expected %s but got %s%n", m, expectedException.getName(), exc);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Not allowed to run method: " + m);
                } catch (RuntimeException rx) {
                    System.out.println("Some other error happented: "+m);
                    rx.printStackTrace();
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests-passed);
    }
}

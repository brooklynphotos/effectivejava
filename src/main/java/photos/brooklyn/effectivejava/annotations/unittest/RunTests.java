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
                Class<? extends Throwable>[] expectedExceptions = m.getAnnotation(ExceptionTest.class).value();
                try {
                    m.invoke(null);
                    System.out.printf("Failed test %s, was expecting one of the %d exceptions%n",m, expectedExceptions.length);
                } catch (InvocationTargetException ie) {
                    Throwable exc = ie.getCause();
                    if (contains(expectedExceptions, exc)) {
                        passed++;
                    } else {
                        System.out.printf("Test %s failed: none of the %d exceptions were found: %s%n", m, expectedExceptions.length, exc);
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

    private static final boolean contains(final Class<? extends Throwable>[] expectedExceptions, final Throwable exc){
        for (Class<? extends Throwable> ex : expectedExceptions) {
            if (ex.isInstance(exc)) {
                return true;
            }
        }
        return false;
    }
}

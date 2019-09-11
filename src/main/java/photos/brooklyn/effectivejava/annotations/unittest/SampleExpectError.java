package photos.brooklyn.effectivejava.annotations.unittest;

public class SampleExpectError {
    @ExceptionTest(IllegalArgumentException.class)
    public static void m1(){
        throw new IllegalArgumentException("Missing something");
    }
    @ExceptionTest(IllegalArgumentException.class)
    public static void m2(){
        throw new NullPointerException("Pointing to nothing");
    }
    @ExceptionTest(IllegalArgumentException.class)
    public static void m3(){}

    @ExceptionTest({IllegalArgumentException.class, ArithmeticException.class})
    public static void m4(){}

    @ExceptionTest({IllegalArgumentException.class, ArithmeticException.class})
    public static int m5(){ return 1/0;}
}

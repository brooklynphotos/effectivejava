package photos.brooklyn.effectivejava.annotations.unittest;

public class Sample {
    @Test public static void m1(){}
    @Test public static void m3(){
        throw new RuntimeException("Uh oh");
    }
    @Test public void m4(){} // will compile but testing tool should check if this is ok
}

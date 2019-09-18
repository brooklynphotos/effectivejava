package photos.brooklyn.effectivejava.annotations.unittest;

import java.lang.ref.Cleaner;

/**
 * Item 8 avoiding finalizer and cleaners
 * An autocloseable class with a cleaner as a safety net
 */
public class Room implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    /**
     * resource that requires cleaning. Must not refer to the Room or else we have circular reference and gc won't find
     */
    private static class State implements Runnable {
        int numJunkPiles; // Number of junk piles in this room

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        /**
         * will be closed by try-with-resource or the cleaner
         */
        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    // the state of this room, shared with cleanable
    private final State state;

    // Cleans the room when it's eligible for gc
    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        // cleanable now knows about the state
        cleanable = cleaner.register(this, state);
    }

    /**
     * does what the cleaner does
     * @throws Exception
     */
    @Override
    public void close() {
        cleanable.clean();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            try (Room r = new Room(7)) {
                System.out.println("Adult in the room");
            }
        } else {
            new Room(99);
            System.gc();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Teenager left the room");
        }
    }
}

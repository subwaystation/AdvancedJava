package live_hacking;

/**
 * Created by heumos on 30.11.15.
 */
public abstract class Animal {

    public void dailyRoutine() {
        System.out.println("Get up.");
        eat();
        System.out.println("Go to sleep.");
    }

    public abstract void eat();
}

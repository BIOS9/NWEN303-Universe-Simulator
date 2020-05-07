package test;

import datasets.DataSetLoader;
import model.Model;
import model.ModelParallel;
import org.junit.jupiter.api.Test;

import javax.jws.WebParam;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {
    /**
     * Measures the time to do an operation.
     * Since Java can use lazy loading, objects may not be initialized before the
     * timer starts meaning the initialization will be included in the time.
     * This method uses a warm up period to ensure the JVM has initialized all objects.
     *
     * @param r Runnable that contains the operation to be timed.
     * @param warmUp How many times to run the operation for the JVM warmup.
     * @param runs How many times to run the runnable, gives consistency.
     * @return How long the operations took in milliseconds.
     */
    long timeOf(Runnable r, int warmUp, int runs) {
        System.gc();
        for (int i = 0; i < warmUp; i++) {
            r.run();
        }
        long time0 = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            r.run();
        }
        long time1 = System.currentTimeMillis();
        return time1 - time0;
    }

   void runSteps(Model m, String name) {
        long time = timeOf(() -> {
            for (int i = 0; i < 10000; ++i) {
                m.step();
            }
        }, 100, 10);//realistically 20.000 to make the JIT do his job..
        System.out.println(name + " 10000 steps takes " + time / 1000d + " seconds");
    }

    @Test
    public void testPerformance() {
        Model model = DataSetLoader.getElaborate(Model.class, 200, 300, 2, 0.99);
        Model modelParallel = DataSetLoader.getElaborate(ModelParallel.class, 200, 300, 2, 0.99);

        runSteps(model, "Sequential");
        runSteps(modelParallel, "Parallel");
    }
}

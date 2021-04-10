import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Reading input
        System.out.print("Python interpreter path: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String path = reader.readLine();
        final int[] seconds = {0};
        // Creating timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time elapsed: " + ++seconds[0] + "s");
            }
        }, 1000, 1000);

        // Creating process to execute command
        var processBuilder = new ProcessBuilder();
        processBuilder.command(path, "-m", "timeit", "-r 10");
        var process = processBuilder.start();
        // Waiting for completion process
        process.waitFor();
        // Stop timer
        timer.cancel();

        // Printing process output
        try (var processReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = processReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

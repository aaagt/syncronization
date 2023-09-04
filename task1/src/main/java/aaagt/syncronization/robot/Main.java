package aaagt.syncronization.robot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();

    public static void main(String[] args) throws InterruptedException {

        // создаём и запускаем потоки
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(Main::run);
            threads.add(thread);
            thread.start();
        }

        // ждём завершения потоков
        for (Thread thread : threads) {
            thread.join();
        }

        // выводим результат
        var maxRate = sizeToFreq.keySet().stream()
                .reduce(Integer::max)
                .orElse(0);
        System.out.printf(
                "Самое частое количество повторений %d (встретилось %d раз)\n",
                maxRate,
                sizeToFreq.get(maxRate));
        System.out.println("Другие размеры:");
        for (var entry : sizeToFreq.entrySet()) {
            System.out.printf("- %d (%d раз)\n", entry.getKey(), entry.getValue());
        }
    }

    public static void run() {
        var route = generateRoute("RLRLR", 100);
        var count = countR(route);
        synchronized (sizeToFreq) {
            if (sizeToFreq.containsKey(count)) {
                var oldRate = sizeToFreq.get(count);
                sizeToFreq.put(count, oldRate + 1);
            } else {
                sizeToFreq.put(count, 1);
            }
        }
    }

    private static int countR(String route) {
        var count = 0;
        for (int i = 0; i < route.length(); i++) {
            if (route.charAt(i) == 'R') {
                count++;
            }
        }
        return count;
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}

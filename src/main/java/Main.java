import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static BlockingQueue<String> queueStringA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueStringB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueStringC = new ArrayBlockingQueue<>(100);

    public static String stringA;
    public static String stringB;
    public static String stringC;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("abc", 3 + random.nextInt(3));
                try {
                    queueStringA.put(texts[i]);
                    queueStringB.put(texts[i]);
                    queueStringC.put(texts[i]);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        Runnable runnable1 = () -> {
            int maxCount = 0;

            for (int i = 0; i < 100; i++) {
                String text;
                int count = 0;

                try {
                    text = queueStringA.take();

                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'a') {
                            count++;
                        }
                    }

                    if (count > maxCount) {
                        maxCount = count;
                        stringA = text;
                    }

                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };


        Runnable runnable2 = () -> {
            int maxCount = 0;

            for (int i = 0; i < 100; i++) {
                String text;
                int count = 0;

                try {
                    text = queueStringB.take();

                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'b') {
                            count++;
                        }
                    }

                    if (count > maxCount) {
                        maxCount = count;
                        stringB = text;
                    }
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        Runnable runnable3 = () -> {
            int maxCount = 0;

            for (int i = 0; i < 100; i++) {
                String text;
                int count = 0;

                try {
                    text = queueStringC.take();

                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'c') {
                            count++;
                        }
                    }

                    if (count > maxCount) {
                        maxCount = count;
                        stringC = text;
                    }
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        List<Thread> threads = new ArrayList<>();

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);

        thread1.start();
        thread2.start();
        thread3.start();

        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Текст с максимальным количеством символов а: " + stringA);
        System.out.println("Текст с максимальным количеством символов b: " + stringB);
        System.out.println("Текст с максимальным количеством символов c: " + stringC);

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counterFree = new AtomicInteger(0);
    public static AtomicInteger counterFour = new AtomicInteger(0);
    ;
    public static AtomicInteger counterFive = new AtomicInteger(0);
    ;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable runnable1 = () -> {
            for (int i = 0; i < texts.length; i++) {
                String text = texts[i];

                if (checkingForPalindrome(text)) {
                    incCounter(text.length());
                }
            }
        };


        Runnable runnable2 = () -> {
            for (int i = 0; i < texts.length; i++) {
                String text = texts[i];

                if (checkingForCharacterEquality(text)) {
                    incCounter(text.length());
                }
            }
        };

        Runnable runnable3 = () -> {
            for (int i = 0; i < texts.length; i++) {
                String text = texts[i];
                if (checkingForCharacterAscending(text)) {
                    incCounter(text.length());
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

        System.out.println("Красивых слов с длиной 3: " + counterFree + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterFour + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterFive + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean checkingForPalindrome(String text) {
        boolean isCounter = true;

        for (int j = 0; j < text.length() / 2; j++) {
            if (text.charAt(j) != text.charAt((text.length() - 1) - j)) {
                isCounter = false;
                break;
            }
        }

        return isCounter;
    }

    public static boolean checkingForCharacterEquality(String text) {
        boolean isCounter = true;

        for (int j = 0; j < text.length() - 1; j++) {
            if (text.charAt(j) != text.charAt(j + 1)) {
                isCounter = false;
                break;
            }
        }

        return isCounter;
    }

    public static boolean checkingForCharacterAscending(String text) {
        boolean isCounter = true;

        for (int j = 0; j < text.length() - 1; j++) {
            int res = Character.compare(text.charAt(j + 1), text.charAt(j));
            if (res == -1) {
                isCounter = false;
                break;
            }
        }

        return isCounter;
    }

    public static void incCounter(int length) {
        switch (length) {
            case 3 -> counterFree.getAndIncrement();
            case 4 -> counterFour.getAndIncrement();
            case 5 -> counterFive.getAndIncrement();
        }
    }

}

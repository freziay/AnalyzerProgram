import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;


public class AnalyzerProgram {
    private static int countA = 0;
    private static int countB = 0;
    private static int countC = 0;

    private static ArrayBlockingQueue<String> arrayBlockingQueueA = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> arrayBlockingQueueB = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> arrayBlockingQueueC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        String[] texts = new String[10_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 100_000);
        }
        new Thread(() -> {

            for (String text : texts) {
                try {
                    arrayBlockingQueueA.put(text);
                    arrayBlockingQueueB.put(text);
                    arrayBlockingQueueC.put(text);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();
        new Thread(() -> {
            for (String text : texts) {
                setCountA(arrayBlockingQueueA);
            }

        }).start();
        new Thread(() -> {
            for (String text : texts) {
                setCountB(arrayBlockingQueueB);
            }

        }).start();
        new Thread(() -> {
            for (String text : texts) {
                setCountC(arrayBlockingQueueC);
            }
        }).start();
    }

    public static void setCountA(ArrayBlockingQueue<String> arrayBlockingQueueA) {
        try {
            String a = arrayBlockingQueueA.take();
            String[] words = a.split("");
            for (int j = 0; j < words.length; j++) {
                if (words[j].equals("a")) {
                    countA++;

                }
                if (words[j].equals("b")) {
                    countB++;
                }
                if (words[j].equals("c")) {
                    countC++;
                }
            }
            if (countA > countB & countA > countC) {
                System.out.println("match A - " + a);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setCountB(ArrayBlockingQueue<String> arrayBlockingQueueB) {
        try {
            String b = arrayBlockingQueueB.take();
            String[] words = b.split("");
            for (int j = 0; j < words.length; j++) {
                if (words[j].equals("a")) {
                    countA++;

                } else if (words[j].equals("b")) {
                    countB++;
                } else if (words[j].equals("c")) {
                    countC++;
                }
            }
            if (countB > countA & countB > countC) {
                System.out.println("match B - " + b);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void setCountC(ArrayBlockingQueue<String> arrayBlockingQueueC) {
        try {
            String c = arrayBlockingQueueC.take();
            String[] words = c.split("");
            for (int j = 0; j < words.length; j++) {
                if (words[j].equals("a")) {
                    countA++;

                } else if (words[j].equals("b")) {
                    countB++;
                } else if (words[j].equals("c")) {
                    countC++;
                }
            }
            if (countC > countB & countC > countA) {

                System.out.println("match C - " + c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static String generateText(String letters, int length) {
        int count = 0;
        //      while (count < 200) {// разобраться
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}



import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        double[] probTask = new double[]{0.31, 0.15, 0.13, 0.12, 0.104, 0.1, 0.03, 0.03, 0.02, 0.006};
        double entropySum = countEntropy(probTask);
        System.out.println("Ентропія і мінімальна довжина кодового слова - " + entropySum);
        System.out.println("\n\n\n");
        StringBuilder data = new StringBuilder();
        try {
            File myFile = new File("C:\\Users\\stasy\\IdeaProjects\\MMDP\\lr2_TI\\src\\text.txt");
            Scanner reader = new Scanner(myFile);
            while(reader.hasNextLine()) {
                data.append(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        Map<String, Integer> res = new HashMap<>();
        int countLetters = countFile(data.toString(), res);
        Map<String, Double> probRes = new HashMap<>();
        countProbMap(countLetters, res, probRes);
        List<Map.Entry<String, Double>> sortTable = sortMapByValue(probRes);
        System.out.println("Таблиця ймовірностей");
        for(Map.Entry<String, Double> el: sortTable) {
            System.out.print(el.getKey() + " - ");
            printDouble(el.getValue());
        }
        double entropySumFile = countEntropyFile(probRes);
        System.out.println("Ентропія тексту - " + entropySumFile);

        List<Map.Entry<String, Double>> qwe = new LinkedList<>();
        qwe.add(new AbstractMap.SimpleEntry<>("a", 0.31));
        qwe.add(new AbstractMap.SimpleEntry<>("в" , 0.15));
        qwe.add(new AbstractMap.SimpleEntry<>("й" , 0.13));
        qwe.add(new AbstractMap.SimpleEntry<>("д" , 0.12));
        qwe.add(new AbstractMap.SimpleEntry<>("з" , 0.104));
        qwe.add(new AbstractMap.SimpleEntry<>("г" , 0.1));
        qwe.add(new AbstractMap.SimpleEntry<>("є", 0.03));
        qwe.add(new AbstractMap.SimpleEntry<>("ж" , 0.03));
        qwe.add(new AbstractMap.SimpleEntry<>("е" , 0.02));
        qwe.add(new AbstractMap.SimpleEntry<>("б" , 0.006));

        //methodShen(sortTable);
        methodShen(qwe);
    }

    public static double countEntropy(double[] probMap) {
        double entropySum = 0;
        for(int i = 0; i < probMap.length; i++) {
            double entropySymbol = probMap[i] * Math.log(probMap[i]) / Math.log(2) * -1;
            entropySum += entropySymbol;
        }
        return entropySum;
    }

    public static int countFile(String data, Map<String, Integer> res) {
        int countLetters = 0;
        for(int i = 0; i < data.length(); i++) {
            char dataChar = data.charAt(i);
            if((int)dataChar >= 65 && (int)dataChar <= 122) {
                if((int)dataChar <= 90) {
                    addChar(res, String.valueOf(dataChar));
                } else if((int)dataChar >= 97) {
                    addChar(res, String.valueOf(dataChar));
                }
                countLetters++;
            }
        }
        return countLetters;
    }

    public static void addChar(Map map, String data) {
        if(map.containsKey(data)) {
            map.put(data, (Integer)map.get(data) + 1);
        } else {
            map.put(data, 1);
        }
    }

    public static void countProbMap(int allSymbols, Map<String, Integer> countMap, Map<String, Double> probMap) {
        for(Map.Entry<String, Integer> el : countMap.entrySet()) {
            probMap.put(el.getKey(), Double.valueOf(el.getValue())/allSymbols);
        }
    }

    public static void printDouble(Double number) {
        System.out.printf("%.9f\n", number);
    }

    public static List<Map.Entry<String, Double>> sortMapByValue(Map<String, Double> unsortedMap) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortedMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
           public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
               return (o2.getValue().compareTo(o1.getValue()));
           }
        });
        return list;
    }
    public static List<List<Integer>> stepsCode = new LinkedList<>(){};
    public static List<Map.Entry<String, Double>> globTable = new LinkedList<>();
    public static void methodShen(List<Map.Entry<String, Double>> table) {
        globTable = table;
        for(int i = 0; i < table.size(); i++) {
            stepsCode.add(new LinkedList<>());
        }
        divTable(table);
        stepsCode.get(stepsCode.size()-1).add(0);
        System.out.println("Кінцева таблиця:\n");
        printTable(stepsCode);
        System.out.print("2 курс КН-203 Ярець С.Б.");
    }
    public static void divTable(List<Map.Entry<String, Double>> table) {
        if(table.size() == 1)
            return;
        double sum = 0;
        for(Map.Entry<String, Double> el: table) {
            sum += el.getValue();
        }
        double max = 0;
        int i = 0;
        for(Map.Entry<String, Double> el: table) {
            max += el.getValue();
            if(sum - max*2 <= 0) {
                break;
            }
            i++;
        }
        if(i == 0)
            i = 1;
        int index = globTable.indexOf(table.get(0));
        i += index;
        for(int j = index, q = 0; q < table.size(); j++, q++) {
            if(j < i) {
                stepsCode.get(j).add(0);
            } else {
                stepsCode.get(j).add(1);
            }
        }
        System.out.println("Проміжна таблиця:\n");
        printTable(stepsCode);
        if(table.size() == 2)
            return;
        i -= index;
        List<Map.Entry<String, Double>> qwe = table.subList(0, i);
        divTable(qwe);
        List<Map.Entry<String, Double>> zxc = table.subList(i, table.size());
        divTable(zxc);

    }

    public static void printTable(List<List<Integer>> table) {
        for(int i = 0; i < table.size(); i++) {
            System.out.print(globTable.get(i) + " - ");
            for(int j = 0; j < table.get(i).size(); j++) {
                System.out.print(table.get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    public static double countEntropyFile(Map<String, Double> probMap) {
        double entropySum = 0;
        for(Map.Entry<String, Double> el: probMap.entrySet()) {
            double entropySymbol = el.getValue() * Math.log(el.getValue()) / Math.log(2) * -1;
            entropySum += entropySymbol;
        }
        return entropySum;
    }
}
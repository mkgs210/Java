import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class laba1_2 {
    static int k = 0;
    static Scanner input = new Scanner(System.in); // Объявляем Scanner

    public static void enter(List<Integer> myArray,int m, int n){
        System.out.println("Insert array elements" +m+":");
        /*Пройдёмся по всему массиву, заполняя его*/
        for (int i = 0; i < n; i++) {
            while (!input.hasNextInt()) {
                System.out.println("That not a number!");
                input.next(); // this is important!
            }
            myArray.add(input.nextInt()); // Заполняем массив элементами, введёнными с клавиатуры
            System.out.print(myArray.get(i) + " ");
        }
    }

    public static void zamena(List<Integer> myArray, int n){
        k=0;
        for (int i = 0; i < n; i++) {

            if (myArray.get(i) % 2 == 0) {
                myArray.set(i, -myArray.get(i));
            }
            if (myArray.get(i) < 0) {
                k++;
            }
        }
    }

    public static void out(List<Integer> myArray, int k){
        System.out.println(myArray);
        System.out.print("|| less zero=" + k);
        System.out.println("");
    }

    public static void main(String[] args) {

        List<Integer> myArray1 = new ArrayList<>();
        List<Integer> myArray2 = new LinkedList<>();

        enter(myArray1,1, 7);
        zamena(myArray1, 7);
        int k1=k;
        enter(myArray2,2, 5);
        zamena(myArray2, 5);
        int k2=k;

        System.out.print("\nOut array elements:\n");
        out(myArray1,k1);
        out(myArray2,k2);
    }
}


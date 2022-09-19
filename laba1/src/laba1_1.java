import java.util.Arrays;
import java.util.Scanner;

public class laba1_1 {
    static int k = 0;
    static Scanner input = new Scanner(System.in); // Объявляем Scanner

    public static int[] enter( int m, int n){
        int[] myArray = new int[n];
        System.out.println("Insert array elements" +m+":");
        /*Пройдёмся по всему массиву, заполняя его*/
        for (int i = 0; i < n; i++) {
            while (!input.hasNextInt()) {
                System.out.println("That not a number!");
                input.next(); // this is important!
            }
            myArray[i] = input.nextInt(); // Заполняем массив элементами, введёнными с клавиатуры
            System.out.print(myArray[i] + " ");
        }
        return myArray;
    }

    public static int[] zamena(int[] myArray, int m, int n){
        k=0;
        for (int i = 0; i < n; i++) {

            if (myArray[i] % 2 == 0) {
                myArray[i] = -myArray[i];
            }
            if (myArray[i] < 0) {
                k++;
            }
        }
        return myArray;
    }

    public static void out(int[] myArray, int k){
        System.out.println(Arrays.toString(myArray));
        System.out.print("|| less zero=" + k);
        System.out.println("");
    }

    public static void main(String[] args) {

        //System.out.println("Enter array length: ");
        //int size = input.nextInt(); // Читаем с клавиатуры размер массива и записываем в size
        int[] myArray1 = new int[7]; // Создаём массив int размером в size
        int[] myArray2 = new int[5];
        int[] myArray3 = new int[6];

        myArray1 = enter(1, 7);
        myArray1 = zamena(myArray1,1, 7);
        int k1=k;
        myArray2 = enter(2, 5);
        myArray2 = zamena(myArray2,2, 5);
        int k2=k;
        myArray3 = enter(3, 6);
        myArray3 = zamena(myArray3,3, 6);
        int k3=k;

        System.out.print("\nOut array elements:\n");
        out(myArray1,k1);
        out(myArray2,k2);
        out(myArray3,k3);

    }
}
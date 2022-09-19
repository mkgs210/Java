import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class laba_2 {

    public static void new_keyboard(List<Peripheral> masiv){
        Peripheral klava = new Keyboard();
        masiv.add(klava);
    }
    public static void new_monitor(List<Peripheral> masiv){
        Peripheral monik = new Monitor();
        masiv.add(monik);
    }

    public static void new_obj(List<Peripheral> masiv){
        System.out.println("Choose class: 0-monitor, 1-keyboard");
        while(true) {
            Scanner input = new Scanner(System.in);
            int k = 2;
            try {
                k = input.nextInt();
            } catch (Exception e) {
                System.out.println("Написано же ввести 0 или 1");
                continue;
            }
            switch (k) {
                case 0 -> new_monitor(masiv);
                case 1 -> new_keyboard(masiv);
                default -> {
                    System.out.println("Написано же ввести 0 или 1");
                    continue;
                }
            }
            break;
        }
        //for(Peripheral i:masiv){out_obj(i);}//Вывод всех obj для проверки
    }

    public static void out_masiv(List<Peripheral> masiv){
        if (masiv.size()!=0){
            System.out.println("Вывод устройств");
            for (int i=0; i<masiv.size(); i++){
                System.out.println(i + ": " + masiv.get(i));
            }
        }else{
            System.out.println("Нет доступных устройств");
        }
    }
    public static void choose_obj(List<Peripheral> masiv){
        out_masiv(masiv);
        int k = -1;
        System.out.println("Введите номер объекта");
        while(true) {
            Scanner input = new Scanner(System.in);
            k = -1;
            try {
                k = input.nextInt();
            } catch (Exception e) {
                System.out.println("Это не номер существующего объекта");
                continue;
            }
            if(0<=k & k<=masiv.size()){
                break;
            }else{
                System.out.println("Это не номер существующего объекта");
            }
        }
        change_obj(masiv.get(k));
    }

    private static void change_obj(Peripheral obj) {
        out_obj(obj);

        System.out.println("Какое свойство вы хотите поменять, введите его порядковый номер от 0 до 2. Введите 3, чтобы оставить без изменений");
        while(true) {
            Scanner input = new Scanner(System.in);
            int k = 2;
            try {
                k = input.nextInt();
            } catch (Exception e) {
                System.out.println("Написано же ввести номер свойства от 0 до 2");
                continue;
            }
            switch (k) {
                case 0:
                    obj.power_on_action();
                    break;
                case 1:
                    System.out.println("Какая фирма у этого устройства?");
                    obj.firma = input.next();
                    break;
                case 2:
                    if(obj.getClass().getSimpleName().equals("Monitor")){
                        System.out.println("Какая частота в герцах у этого устройства?");
                        obj.set(input.nextInt(), "");
                    }else{
                        System.out.println("Какая раскладка у этого устройства?");
                        obj.set(0, input.next());
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Написано же ввести 0 или 1");
                    continue;
            }
            out_obj(obj);
            System.out.println("Хотите еще что-нибудь поменять? Введите 1. Иначе любой символ.");
            Scanner input1 = new Scanner(System.in);
            if (input1.next().equals("1")){
                System.out.println("Какое свойство вы хотите поменять, введите его номер. Введите 3, чтобы ничего не изменять");
                out_obj(obj);
            }else{
                break;
            }
        }

    }
    static void out_obj(Peripheral obj){
        System.out.println(obj.getClass().getSimpleName());
        obj.get_OnOff();
        obj.get_firma();
        obj.get_unique();
    }

    static void count_company_children(List<Peripheral> masiv){
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (Peripheral i: masiv){
            if (dictionary.containsKey(i.firma)){
                dictionary.put(i.firma, dictionary.get(i.firma)+1);
            }else{
                dictionary.put(i.firma, 1);
            }
        }
        for (String i: dictionary.keySet()) {
            System.out.println(i+": "+dictionary.get(i));
        }
    }

    public static void main(String[] args) {
        /*Peripheral monik = new Monitor();
        Peripheral klava = new Keyboard();
        masiv.add(monik);
        masiv.add(klava);*/
        List masiv = new ArrayList<>();
        System.out.println("Добро пожаловать в меню. Вы можете по разному взаимодействовать с объектами класса.");
        while(true) {
            System.out.println("Введите нужный символ, чтобы начать.");
            System.out.println("0-Просмотреть доступные устройства");
            System.out.println("1-Создать устройство");
            System.out.println("2-Изменить одно из устройств");
            System.out.println("3-Вывести колличество устройств от разных компаний");
            System.out.println("q-Выход из программы");
            Scanner input = new Scanner(System.in);
            switch (input.next()) {
                case "0":
                    out_masiv(masiv);
                    break;
                case "1":
                    new_obj(masiv);
                    break;
                case "2":
                    choose_obj(masiv);
                    break;
                case "3":
                    count_company_children(masiv);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Я не знаю такую команду");
            }
        }
    }
}

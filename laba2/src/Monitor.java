public class Monitor extends Peripheral{
    private int hertz = 60;
    public void power_on_action(){
        if(!turn){
            System.out.println("Свечусь");
            this.turn = true;
        }else{
            System.out.println("Power-off");
            this.turn = false;
        }
    }

    public void set(int hertz, String m){
        this.hertz=hertz;
    }

    public void get_unique(){
        System.out.println("Частота обновления экрана: "+hertz);
    }
    public Monitor() {
        System.out.println("Отработал конструктор Monitor!");
    }

}

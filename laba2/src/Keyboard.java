public class Keyboard extends Peripheral {
    private String layout = "qwerty";

    public void power_on_action(){
        if(!turn){
            System.out.println("Тык тык тык");
            this.turn = true;
        }else{
            System.out.println("Power-off");
            this.turn = false;
        }
    }

    public void set(int n, String layout){
        this.layout=layout;
    }

    public void get_unique(){
        System.out.println("Раскладка клавиатуры: "+layout);
    }
    public Keyboard() {
        System.out.println("Отработал конструктор Keyboard!");
        this.firma = "Sony";
    }

}
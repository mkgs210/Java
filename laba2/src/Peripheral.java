public abstract class Peripheral {
    protected boolean turn = false;
    protected String firma = "Canon";

    protected Peripheral() {
        System.out.println("Отработал конструктор Peripheral");
    }
    protected void On(){
        System.out.println("Включили");
        this.turn = true;
    }
    protected void Off(){
        System.out.println("Выключили");
        this.turn = false;
    }
    protected void get_firma(){
        System.out.println("Производитель: "+ firma);
    }

    protected void get_OnOff(){
        if(turn){
            System.out.println("Состояние: Воркает");
        }else{
            System.out.println("Состояние: Не воркакет");
        }
    }

    protected abstract void power_on_action();

    protected abstract void set(int n, String m);

    public abstract void get_unique();
}
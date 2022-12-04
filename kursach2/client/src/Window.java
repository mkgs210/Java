//import org.apache.commons.lang3.StringUtils.isNumericSpace;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window extends JFrame{
    public String message;
    JLabel titleLable = new JLabel("Нейроночка");
    JLabel learning_rate_Lable = new JLabel("learning rate:");
    JLabel layers_Lable = new JLabel("hidden layers:");
    JTextField learning_rate = new JTextField();
    JTextField layers = new JTextField();
    JButton start = new JButton("Запуск");
    JButton exit = new JButton("Выход");
    public Window() {
        super("NN");
        this.setBounds(400, 280, 560, 340);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        Container mainPanel = this.getContentPane();
        mainPanel.setLayout(new GridLayout(3,2,2,2));

        mainPanel.add(titleLable);
        mainPanel.add(learning_rate_Lable);
        mainPanel.add(layers_Lable);
        mainPanel.add(learning_rate);
        mainPanel.add(layers);

        ButtonGroup b_group = new ButtonGroup();
        b_group.add(start);
        b_group.add(exit);
        mainPanel.add(start);
        mainPanel.add(exit);
        mainPanel.setLayout(null);
        start.addActionListener(new ButtonStart());
        exit.addActionListener(e -> {System.exit(1);});

        // Пользовательский размер и размещение компонента
        titleLable.setBounds(120, 40, 340, 35);
        // Установить шрифт, параметры: шрифт, статус, размер
        titleLable.setFont(new Font("Полужирный", Font.BOLD, 30));

        learning_rate_Lable.setBounds(94, 124, 90, 30);
        //nameLable.setFont(new Font("Полужирный", Font.BOLD, 24));

        layers_Lable.setBounds(94, 174, 90, 30);
        //passwordLable.setFont(new Font("Полужирный", Font.BOLD, 24));

        learning_rate.setBounds(204, 124, 260, 30);
        //text.setFont(new Font("Полужирный", Font.BOLD, 24));

        layers.setBounds(204, 174, 260, 30);
        //password.setFont(new Font("Полужирный", Font.BOLD, 24));

        start.setBounds(157, 232, 100, 30);
        //login.setFont(new Font("Bold", Font.BOLD, 22));

        exit.setBounds(304, 232, 100, 30);
        //exit.setFont(new Font("Полужирный", Font.BOLD, 22));

        // Установить цвет фона формы
        mainPanel.setBackground(Color.WHITE);

    }
    class ButtonStart implements ActionListener {

        public static void dots(Integer port1,Integer port2,String message) throws IOException {
            FormDots f = new FormDots(port1,port2, message);
            new Thread(f).start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                if ((StringUtils.isNumericSpace(layers.getText())
                        | (layers.getText().replace(" ", "").equals("")))
                        & StringUtils.isNumericSpace(learning_rate.getText().replaceFirst("\\.",""))
                        & !(learning_rate.getText().replaceFirst("\\.","").equals(""))){
                    double lr = Double.parseDouble(learning_rate.getText().replace(" ",""));
                    if(lr>0){
                        String layer = layers.getText().replace("  "," ").replace(" ", ">");
                        System.out.println(layer);
                        message =(lr+">"+0+">"+ 2 + ">"+ layer +">" + 2).replace(">>", ">");


                        setVisible(false);
                        System.out.println(message);
                        System.out.println("connect to server");
                        dots(50005,50006,message);
                    }else{
                        System.out.println("learning rate должен быть > 0");
                    }

                }else{
                    System.out.println("False");
                }
            }catch (IOException ex){
                System.out.println("Похоже сервер недоступен");
                setVisible(true);
                JOptionPane.showMessageDialog(null, "Похоже сервер недоступен", "No connection", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}

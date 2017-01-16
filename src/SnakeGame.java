import javax.swing.*;
import java.awt.*;


/**
 * Created by ivhmao on 16.01.2017.
 */
public class SnakeGame {
    JFrame appForm;
    MyJPanel playField;
    boolean gameOver = false;
    int countX = 30;
    int countY = 30;
    int sizeOneField = 20;


    public static void main(String[] args){
        new SnakeGame().go();
    }

    public void go(){
        appForm = new JFrame("SnakeGame v.0.1");
        appForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appForm.setSize(sizeOneField*countX,sizeOneField*countY);
        appForm.setLocationRelativeTo(null);
        //appForm.setResizable(false);

        //appForm.getRootPane().getHeight();
        playField = new MyJPanel();
        playField.setPreferredSize(new Dimension(sizeOneField*countX,sizeOneField*countY));


        appForm.getContentPane().add(playField, BorderLayout.CENTER);
        appForm.pack();
        appForm.setVisible(true);
        System.out.println(appForm.getRootPane().getX());
        System.out.println(appForm.getRootPane().getWidth());
        System.out.println(appForm.getRootPane().getY());
        System.out.println(appForm.getRootPane().getHeight());
        while (!gameOver){
            appForm.repaint();
        }



        System.out.println("go!!");
    }

    class MyJPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.white);
            g.fillRect(0,0,sizeOneField*countX-1,sizeOneField*countY-1);

            g.setColor(Color.blue);
            g.drawRect(0,0,sizeOneField*countX-1,sizeOneField*countY-1);

        }
    }


}

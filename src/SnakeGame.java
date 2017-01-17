import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by ivhmao on 16.01.2017.
 * Snake v0.1
 */
public class SnakeGame {
    private JFrame appForm;
    private MyJPanel playField;
    private boolean gameOver = false;
    private final int COUNTX = 30;
    private final int COUNTY = 30;
    private final int SIZEONEFIELD = 20;
    private final int INITX = 10;
    private final int INITY = 10;
    private final int RIGHT = 39;
    private final int LEFT = 37;
    private final int DOWN = 40;
    private final int UP = 38;
    private final int MOVE_PAUSE = 150;
    // Segment s;
    private Snake snake;
    private Food food;



    public static void main(String[] args){
        new SnakeGame().go();
    }

    private void go(){
        appForm = new JFrame("SnakeGame v.0.1");
        appForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appForm.setSize(SIZEONEFIELD*COUNTX,SIZEONEFIELD*COUNTY);
        appForm.setLocationRelativeTo(null);
        //appForm.setResizable(false);
        playField = new MyJPanel();
        playField.setFocusable(true);
        playField.setPreferredSize(new Dimension(SIZEONEFIELD*COUNTX,SIZEONEFIELD*COUNTY));
        playField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.setDirection(e.getKeyCode());
                super.keyPressed(e);
            }
        });


        appForm.getContentPane().add(playField, BorderLayout.CENTER);
        appForm.pack();
        appForm.setVisible(true);

      // s = new Segment(5,5);
        snake = new Snake(6);

        food = new Food(ThreadLocalRandom.current().nextInt(0,COUNTX+1),ThreadLocalRandom.current().nextInt(0,COUNTY+1))
                ;
        while (!gameOver){
            snake.move();
            if (snake.isFood(food)){
                snake.segments.add(0,new Segment(food.getX(),food.getY()));
                food.setX(ThreadLocalRandom.current().nextInt(0,COUNTX+1));
                food.setY(ThreadLocalRandom.current().nextInt(0,COUNTY+1));
            }
            appForm.repaint();

            try {
                Thread.sleep(MOVE_PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



        System.out.println("go!!");
    }

    class MyJPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.white);
            g.fillRect(0,0,SIZEONEFIELD*COUNTX-1,SIZEONEFIELD*COUNTY-1);

            g.setColor(Color.blue);
            g.drawRect(0,0,SIZEONEFIELD*COUNTX-1,SIZEONEFIELD*COUNTY-1);

            snake.paint(g);
            food.paint(g);

        }
    }

    class Food extends Segment{
        private Food(int xi, int yi) {
            super(xi, yi);
        }

        @Override
        public void paint(Graphics g){
            g.setColor(Color.GREEN);
            g.fillOval(SIZEONEFIELD*x,SIZEONEFIELD*y,SIZEONEFIELD,SIZEONEFIELD);
        }

    }
    class Segment{
        int x;
        int y;
        private Segment(int xi, int yi){
            x = xi;
            y = yi;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void paint(Graphics g){
            g.setColor(Color.BLUE);
            g.fillOval(SIZEONEFIELD*x,SIZEONEFIELD*y,SIZEONEFIELD,SIZEONEFIELD);
        }
    }
    class Snake{
        private ArrayList<Segment> segments;
        private int direction = RIGHT;

        private boolean isFood(Food f){
            return f.getX() == segments.get(0).getX() && f.getY() == segments.get(0).getY();
        }

        private void setDirection(int direction) {
            this.direction = direction;
        }

        private Snake(int initSize){
            segments = new ArrayList<Segment>();
            for (int i = 0; i < initSize ; i++){
                segments.add(new Segment(INITX-i,INITY));
            }
        }

        public void paint(Graphics g){
            for (Segment segment:segments) {
                segment.paint(g);
            }
        }

        public void move(){
            int x = segments.get(0).getX();
            int y = segments.get(0).getY();

            switch (direction){
                case UP: y--;
                    if (y < 0) y=COUNTY-1;
                    break;
                case DOWN: y++;
                    if (y >= COUNTY) y=0;
                    break;
                case LEFT: x--;
                    if (x < 0) x=COUNTX-1;
                    break;
                case RIGHT: x++;
                    if (x >= COUNTX) x=0;
                    break;
            }

            for (Segment s : segments){
                if (s.getX()==x && s.getY()==y) gameOver = true;
            }
            segments.add(0,new Segment(x,y));
            segments.remove(segments.size()-1);
        }

        public int getDirection() {
            return direction;
        }
    }

}

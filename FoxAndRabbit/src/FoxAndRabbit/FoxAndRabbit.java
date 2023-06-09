package FoxAndRabbit;

import animal.Animal;
import animal.Fox;
import animal.Rabbit;
import cell.Cell;
import field.Field;
import field.Location;
import field.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FoxAndRabbit
{
    private Field thefield;
    private View theview;
    private JFrame frame;

    public FoxAndRabbit(int size)
    {
        thefield = new Field(size,size);
        for(int row = 0;row < thefield.getHeight();row++)
        {
            for(int col = 0; col < thefield.getHeight();col++)
            {
                double probability = Math.random();
                if(probability < 0.05)
                {
                    thefield.place(row,col,new Fox());
                }
                else if(probability < 0.15)
                {
                    thefield.place(row,col,new Rabbit());
                }
            }
        }
        theview = new View(thefield);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("FoxAndRabbit");
        frame.add(theview);
        JButton btnSetp = new JButton("Single step");
        frame.add(btnSetp, BorderLayout.NORTH);
        btnSetp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("touch ");
                step();
                frame.repaint();
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    public void step()
    {
        for(int row = 0;row < thefield.getHeight();row++)
        {
            for(int col = 0;col < thefield.getWidth();col++)
            {
                Cell cell = thefield.get(row,col);
                if(cell != null)
                {
                    Animal animal = (Animal)cell;
                    animal.grow();
                    if( animal.isAlive()){
                        //move
                        Location loc = animal.move(thefield.getFreeNeighbour(row, col));
                        if( loc != null ){
                            thefield.move(row, col, loc);
                        }
                        //eat   animal.eat(thefield);
                        if( animal instanceof Fox){
                            Cell[] neighbour = thefield.getNeighbour(row, col);
                            ArrayList<Animal> listRabbit = new ArrayList<Animal>();
                            for( Cell an : neighbour ){
                                if( an instanceof Rabbit ){
                                    listRabbit.add( (Rabbit)an );
                                }
                            }
                            if( !listRabbit.isEmpty() ){
                                Animal fed = ((Fox) animal).feed(listRabbit);
                                if( fed != null ){
                                    thefield.remove((Cell)fed);
                                }
                            }
                        }
                        //breed
                        Animal baby = animal.breed();
                        if( baby != null ){
                            thefield.placeRandomAdj(row, col, (Cell)baby);
                        }
                    }else{
                        thefield.remove(row, col);
                    }
                }

                }
            }
        }

    public void start( int steps ){
        for( int i = 0; i < steps; i++){
            step();
            theview.repaint();
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FoxAndRabbit fnr = new FoxAndRabbit(30);
        //fnr.start(10);
    }
}

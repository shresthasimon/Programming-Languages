import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class ColorSampler extends JFrame{
    protected JButton save;
    protected JButton reset;
    protected JButton RedUp;
    protected JButton RedDown;
    protected JButton GreenUp;
    protected JButton GreenDown;
    protected JButton BlueUp;
    protected JButton BlueDown;

    protected JPanel Buttons;
    protected JPanel Selection;
    protected JPanel leftSide;

    protected JTextField red;
    protected JTextField green;
    protected JTextField blue;

    protected JList<String> colorList;
    protected DrawingTester draw;
    protected ColorType presentColor;
    protected ArrayList<ColorType> colorSet;
    protected String Colors[];
    protected int listSize;
    protected int ColorLocation;

    static ColorSampler program;

    public static void main (String[] args){
        try {
            program = new ColorSampler("Color Sampler");
        }
        catch(IOException e){
            System.out.println("File does not exist!");
            System.exit(0);
        }
    }

    public ColorSampler(String title) throws IOException{
        super(title);
        setBounds(100, 100, 450, 380);
        addWindowListener(new WindowDestroyer());

        colorList = new JList<String>();
        colorList.addListSelectionListener(new ListHandler());

        //save and reset buttons
        save = new JButton("Save");
        reset = new JButton("Reset");

        //red buttons
        RedDown = new JButton("-");
        RedUp = new JButton("+");

        //green buttons
        GreenDown = new JButton("-");
        GreenUp = new JButton("+");

        //blue buttons
        BlueDown = new JButton("+");
        BlueUp = new JButton("-");

        save.addActionListener(new ActionHandler());
        reset.addActionListener(new ActionHandler());

        RedUp.addActionListener(new ActionHandler());
        RedDown.addActionListener(new ActionHandler());

        GreenUp.addActionListener(new ActionHandler());
        GreenDown.addActionListener(new ActionHandler());

        BlueUp.addActionListener(new ActionHandler());
        BlueDown.addActionListener(new ActionHandler());

        //droawing the color screen
        draw = new DrawingTester();

        draw.setBounds(10, 10, 200 ,200);

        //initializing different panels
        Buttons = new JPanel();
        leftSide = new JPanel();
        Selection = new JPanel();

        //File stuff
        File inFile = new File("Colors.txt");
        if(!inFile.exists()){
            throw new IOException("File Doesn't Exist!");
        }
        FileInputStream stream = new FileInputStream(inFile);
        InputStreamReader read = new InputStreamReader(stream);
        StreamTokenizer token = new StreamTokenizer(read);

        colorSet = new ArrayList<ColorType>();

        while(token.nextToken() != token.TT_EOF){
            presentColor = new ColorType();
            presentColor.name = (String)token.sval;
            token.nextToken();
            presentColor.r = (int) token.nval;
            token.nextToken();
            presentColor.g = (int) token.nval;
            token.nextToken();
            presentColor.b = (int)token.nval;
            colorSet.add(presentColor);
            ColorLocation++;

        }

        stream.close();

        ColorLocation--;
        listSize = ColorLocation + 1;
        ColorLocation = 0;



    }

    private class ColorType{
        public int r;
        public int g;
        public int b;

        public String name;

        ColorType(){
            r = 0;
            g = 0;
            b = 0;
            name = "";
        }

        public ColorType( ColorType Other){
            this.r = Other.r;
            this.r = Other.g;
            this.r = Other.b;
            this.name = Other.name;
        }

        public String OutputString(){
            return name + "\t\t" + r + "\t" + g + "\t" + b;
        }
    }

    private class DrawingTester extends JComponent{
        public void paint(Graphics g){
            Dimension d = getSize();
            g.setColor(new Color(presentColor.r, presentColor.g, presentColor.b));
            g.fillRect(1, 1, d.width-2, d.width-2);
        }
    }

    private class WindowDestroyer extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }

        public void output() throws IOException{
            File inputFile = new File("colors.txt");
            if(!inputFile.exists()){
                throw new IOException("File not found");
            }
            FileOutputStream ostream = new FileOutputStream(inputFile);
            PrintWriter write = new PrintWriter(ostream, true);
            for(int i = 0; i < listSize; i++){
                write.println((colorSet.get(i)).OutputString());
            }
            ostream.close();
        }
    }

    private class ActionHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == save ){
                colorSet.set(ColorLocation, new ColorType(presentColor));
                program.setTitle("Color Sampler");
            }
            else if(e.getSource() == reset){
                presentColor = new ColorType(colorSet.get(ColorLocation));
                program.setTitle("Color Sampler");
            }
            else if(e.getSource() == RedUp){
                presentColor.r = Math.min( 255, presentColor.r+5);
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == RedDown){
                presentColor.r = Math.max(0, presentColor.r-5);
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == GreenUp){
                presentColor.g = Math.min(255, presentColor.g+5);
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == GreenDown){
                presentColor.g = Math.max(0,presentColor.g-5);
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == BlueUp){
                presentColor.b = Math.min(255, presentColor.b+5);
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == BlueDown){
                presentColor.b = Math.max(0, presentColor.b-5);
                program.setTitle("Color Sampler*");
            }

            red.setText(String.valueOf(presentColor.r));
            green.setText(String.valueOf(presentColor.g));
            blue.setText(String.valueOf(presentColor.b));
            draw.repaint();

        }
    }

    private class ListHandler implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){
            if(e.getSource() == colorList){
                if(!e.getValueIsAdjusting()){
                    ColorLocation = colorList.getSelectedIndex();
                    presentColor = new ColorType(colorSet.get(ColorLocation));
                    red.setText(String.valueOf(presentColor.r));
                    green.setText(String.valueOf(presentColor.g));
                    blue.setText(String.valueOf(presentColor.b));
                    draw.repaint();
                    program.setTitle("Color Sampler");

                }
            }
        }
    }
}


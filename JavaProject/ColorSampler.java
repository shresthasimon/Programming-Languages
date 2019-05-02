import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.lang.Math;

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
    protected int ColorIndex;

    static ColorSampler program;

    public static void main (String[] args) throws IOException{

            program = new ColorSampler("Color Sampler");
    }

    public void ImportFileColors () throws IOException{
        File inFile = new File("Colors.txt");

        FileInputStream stream = new FileInputStream(inFile);
        InputStreamReader read = new InputStreamReader(stream);
        StreamTokenizer tokens = new StreamTokenizer(read);



        while(tokens.nextToken() != tokens.TT_EOF){
            presentColor = new ColorType();
            presentColor.name = (String)tokens.sval;
            tokens.nextToken();
            presentColor.r = (int) tokens.nval;
            tokens.nextToken();
            presentColor.g = (int) tokens.nval;
            tokens.nextToken();
            presentColor.b = (int)tokens.nval;
            colorSet.add(presentColor);
            ColorIndex++;

        }

        stream.close();
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
        BlueDown = new JButton("-");
        BlueUp = new JButton("+");

        save.addActionListener(new ActionHandler());
        reset.addActionListener(new ActionHandler());

        RedUp.addActionListener(new ActionHandler());
        RedDown.addActionListener(new ActionHandler());

        GreenUp.addActionListener(new ActionHandler());
        GreenDown.addActionListener(new ActionHandler());

        BlueUp.addActionListener(new ActionHandler());
        BlueDown.addActionListener(new ActionHandler());

        //drAwing the color screen
        draw = new DrawingTester();

        draw.setBounds(10, 10, 200 ,200);

        //initializing different panels
        Buttons = new JPanel();
        leftSide = new JPanel();
        Selection = new JPanel();

        colorSet = new ArrayList<ColorType>();
        //File stuff
        ImportFileColors();

        ColorIndex--;
        listSize = ColorIndex + 1;
        ColorIndex = 0;

        presentColor = new ColorType(colorSet.get(ColorIndex));
        Colors = new String[listSize];

        for(int i = 0; i < listSize; i++){
            Colors[i] = (colorSet.get(i)).name;
            //System.out.println(colorSet.get(i).OutputString());
        }
        colorList.setListData(Colors);
        //System.out.print(colorList.getModel().getSize());
        //System.out.print(Colors.length);
        //colorList.setSelectedIndex(1);
        Selection.setLayout(new GridLayout(3,4,5,5));

        Buttons.add(save);
        Buttons.add(reset);

        red = new JTextField(String.valueOf(presentColor.r));
        Selection.add(new JLabel("Red:"));
        Selection.add(red);
        Selection.add(RedDown);
        Selection.add(RedUp);


        green = new JTextField(String.valueOf(presentColor.g));
        Selection.add(new JLabel("Green: "));
        Selection.add(green);
        Selection.add(GreenDown);
        Selection.add(GreenUp);


        blue = new JTextField(String.valueOf(presentColor.b));
        Selection.add(new JLabel("Blue: "));
        Selection.add(blue);
        Selection.add(BlueDown);
        Selection.add(BlueUp);


        leftSide.setLayout(null);
        leftSide.add(draw);
        leftSide.add(Selection);
        leftSide.add(Buttons);

        draw.setBounds(10,10,250,150);
        Selection.setBounds(10,170,250,100);
        Buttons.setBounds(50,290,200,50 );
        leftSide.setBounds(10,10,270,400);
        colorList.setBounds(300,20,130,310);

        getContentPane().setLayout(null);
        getContentPane().add(leftSide);
        getContentPane().add(colorList);

        setVisible(true);
        ColorIndex = 0;

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
            this.g = Other.g;
            this.b = Other.b;
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
            try{
                output();
            }
            catch(IOException er){
                System.out.println("File Doesn't Exist!");
            }
            System.exit(0);
        }

        public void output() throws IOException{
            File inputFile = new File("Colors.txt");
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
            int newValue;
            if(e.getSource() == save ){
                colorSet.set(ColorIndex, new ColorType(presentColor));
                program.setTitle("Color Sampler");
            }
            else if(e.getSource() == reset){
                presentColor = new ColorType(colorSet.get(ColorIndex));
                program.setTitle("Color Sampler");
                try{
                    colorSet.clear();
                    ImportFileColors();
                    //ColorIndex = 0;
//                    Colors = new String[listSize];
//
//                    for(int i = 0; i < listSize; i++){
//                        Colors[i] = (colorSet.get(i)).name;
//                    }
//                    colorList.setListData(Colors);
//                    colorList.setSelectedIndex(0);
                    ColorIndex = colorList.getSelectedIndex();
                    presentColor = colorSet.get(ColorIndex);
                    red.setText(String.valueOf(presentColor.r));
                    green.setText(String.valueOf(presentColor.g));
                    blue.setText(String.valueOf(presentColor.b));
                    draw.repaint();
                    program.setTitle("Color Sampler");
                }
                catch(IOException e1){
                    System.out.println("Couldn't open file to be imported");
                }

            }
            else if(e.getSource() == RedUp){
                newValue = Math.min(255, colorSet.get(ColorIndex).r + 5);
                colorSet.get(ColorIndex).r = newValue;
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == RedDown){
                newValue = Math.max(0, colorSet.get(ColorIndex).r-5);
                colorSet.get(ColorIndex).r = newValue;
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == GreenUp){
                newValue = Math.min(255, colorSet.get(ColorIndex).g+5);
                colorSet.get(ColorIndex).g = newValue;
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == GreenDown){
                newValue = Math.max(0,colorSet.get(ColorIndex).g-5);
                colorSet.get(ColorIndex).g = newValue;
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == BlueUp){
                newValue = Math.min(255, colorSet.get(ColorIndex).b+5);
                colorSet.get(ColorIndex).b = newValue;
                program.setTitle("Color Sampler*");
            }
            else if(e.getSource() == BlueDown){
                newValue = Math.max(0, colorSet.get(ColorIndex).b-5);
                colorSet.get(ColorIndex).b = newValue;
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
                    ColorIndex = colorList.getSelectedIndex();
                    presentColor = colorSet.get(ColorIndex);
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



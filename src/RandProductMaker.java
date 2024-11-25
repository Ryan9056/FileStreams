import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductMaker extends JFrame{


    JPanel mainPnl;
    JPanel displayPnl;
    JPanel controlPnl;
    JPanel buttonPnl;

    JButton writeButton;
    JButton readButton;
    JTextArea displayTA;
    JScrollPane scroller;


    JButton quitBtn;


    String name;
    String description;
    String ID;
    double cost;

    String serachName;

    int i = 0;

    ArrayList<Product> products = new ArrayList<>();











    public RandProductMaker()
    {


        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());


        createButtonPanel();
        mainPnl.add(buttonPnl, BorderLayout.NORTH);

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createControlPanel();
        mainPnl.add(controlPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setSize(810, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createButtonPanel()
    {

        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1, 2));
        writeButton = new JButton("Make a product");
        readButton = new JButton("Read product");

        readButton.addActionListener((ActionEvent ae) -> {

                  readFromFile();

                }
        );

        writeButton.addActionListener((ActionEvent ae) -> {

                productMaker();
                writeToTxt();
                writeToFile();


            }
        );
        buttonPnl.add(writeButton);
        buttonPnl.add(readButton);



    }





    private void createDisplayPanel()
    {

        displayPnl = new JPanel();
        displayTA = new JTextArea(10, 35);
        displayTA.setFont(new Font("Georgia", Font.PLAIN, 14));
        displayTA.setEditable(false);
        scroller = new JScrollPane(displayTA);
        displayPnl.add(scroller);
    }


    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 1));


        quitBtn = new JButton("Quit!");
        quitBtn.setFont(new Font("Verdana", Font.PLAIN, 20));
        quitBtn.addActionListener((ActionEvent ae) -> {
                    System.exit(0);
                }
        );


        controlPnl.add(quitBtn);

    }

    private void productMaker() {
        name = JOptionPane.showInputDialog("Input a product name (max 35 characters)");

        description = JOptionPane.showInputDialog("Input a product description (max 75 characters)");

        ID = JOptionPane.showInputDialog("Input a product ID (max 6 characters)");

        cost = Double.parseDouble(JOptionPane.showInputDialog("Input a product cost"));

        name.trim();
        description.trim();
        ID.trim();

        name = name.substring(0, Math.min(name.length(), 35));
        description = description.substring(0, Math.min(description.length(), 75));
        ID = ID.substring(0, Math.min(ID.length(), 6));


        products.add(new Product(name, description, ID, cost));



    }

    private void writeToTxt() {

        File workingDirectory = new File("data2.txt");

        if (!workingDirectory.exists()) {
            try {
                workingDirectory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path txtfile = Paths.get(workingDirectory.getPath());





        try
        {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(txtfile, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));


                for (int c = 0; c < products.size(); c++) {
                    writer.write(products.get(c).getName());
                    writer.newLine();
                    writer.write(products.get(c).getDesription());
                    writer.newLine();
                    writer.write(products.get(c).getID());
                    writer.newLine();
                    writer.write(String.valueOf(products.get(c).getCost()));
                    writer.newLine();
                }



                writer.close();



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void writeToFile() {

        File fileLoaction = new File("data.dat");

        if (!fileLoaction.exists()) {
            try {
                fileLoaction.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {


            RandomAccessFile file = new RandomAccessFile("data.dat", "rw");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(products.get(i));
            byte[] bytes = baos.toByteArray();

            file.seek(file.length());
            file.writeInt(bytes.length);
            file.write(bytes);

            file.close();
            i++;

            displayTA.setText("Products recorded: " + i);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void readFromFile() {

        serachName = JOptionPane.showInputDialog("Input a product name you would like to search");

        try {
            RandomAccessFile file = new RandomAccessFile("data.dat", "r");



            while (file.getFilePointer() < file.length()) {
                int size = file.readInt();
                byte[] bytes = new byte[size];
                file.readFully(bytes);

                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);

                Product readProduct = (Product) ois.readObject();


                if (readProduct.getName().equals(serachName)) {
                    displayTA.setText(readProduct.toString());
                    file.close();
                    return;
                }



            }

            displayTA.setText("product not found");
            file.close();



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}

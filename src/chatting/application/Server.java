package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class Server extends JFrame implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea a1;

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;

    Server()
    {
        //div:header
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,400,50);
        add(p1);

        //div:Configuration of Images and text at header
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("chatting/application/images/arrow.png"));
        Image i2=i1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(7,15,15,20);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("chatting/application/images/Person_img.png"));
        Image i5=i4.getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel l2=new JLabel(i6);
        l2.setBounds(20,1,60,50);
        p1.add(l2);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("chatting/application/images/video.png"));
        Image i8=i7.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel l5=new JLabel(i9);
        l5.setBounds(270,15,25,25);
        p1.add(l5);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("chatting/application/images/phone.png"));
        Image i11=i10.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel l6=new JLabel(i12);
        l6.setBounds(320,15,25,25);
        p1.add(l6);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("chatting/application/images/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,20, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel l7=new JLabel(i15);
        l7.setBounds(370,15,10,25);
        p1.add(l7);

        JLabel l3=new JLabel("Person A");
        l3.setFont(new Font("SAN_SERIF",Font.BOLD,15));
        l3.setForeground(Color.WHITE);
        l3.setBounds(80,12,100,15);
        p1.add(l3);

        JLabel l4=new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        l4.setForeground(Color.WHITE);
        l4.setBounds(85,30,100,13);
        p1.add(l4);
        //div images and text configuration-ends-----------------
        //div:header-ends--------------
        Timer t=new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!typing){
                    l4.setText("Active Now");
                    //String name=l4.getText();
                }
            }
        });
        t.setInitialDelay(2000);


        //div:body
        a1=new JTextArea();
        a1.setBounds(5,55,390,450);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);
        //div:body-ends-----------



        //div-footer
        t1=new JTextField();
        t1.setBounds(5,510,300,30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
        add(t1);
        
        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");
                t.stop();

                typing = true;
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                typing=false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1=new JButton("Send");
        b1.setBounds(305,510,93,29);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        b1.addActionListener(this);
        add(b1);
        //div-ends------------------



        //div:frame configuration
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(400,550);
        setLocation(250,150);
        setUndecorated(true);
        setVisible(true);
        //div-ends-------------------
    }

    public void actionPerformed(ActionEvent ae)
    {
        try {
            String out = t1.getText();
            a1.setText(a1.getText() + "\n\t\t" + out);
            //t1.setText("");
            dout.writeUTF(out);
            t1.setText("");

        }catch(Exception e)
        {

        }
    }
    public static void main(String []args)
    {
        new Server().setVisible(true);
        String msginput="";
        try{
            skt=new ServerSocket(10404);
            s=skt.accept();
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            msginput=din.readUTF();
            a1.setText(a1.getText()+"\n"+msginput);
            skt.close();
            s.close();
        }catch(Exception e){}
    }
}

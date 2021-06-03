package chatting.application;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
public class Client extends JFrame implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;
    Client()
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

        JLabel l3=new JLabel("Person B");
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
        a1=new JPanel();
        a1.setBounds(5,55,390,450);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
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
        setLocation(900,150);
        setUndecorated(true);
        setVisible(true);
        //div-ends-------------------
    }

    public void actionPerformed(ActionEvent ae)
    {
        try {
            String out = t1.getText();
	    sendText(out);
            JPanel p2=formatLabel(out);
            //a1.add(p2);
            a1.setLayout(new BorderLayout());
            JPanel right=new JPanel(new BorderLayput());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            a1.add(vertical,BorderLayout.PAGE_START);
            //t1.setText("");
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void sendText(String message) throws FileNotFoundException
    {
        try(FileWriter f=new FileWriter("chat.txt",true);
            PrintWriter p= new PrintWriter(new BufferedWriter(f));){
            p.println("Person B:" +message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    public static void main(String[] args){
        new Client().f1.setVisible(true);
        
        try{
            
            s = new Socket("127.0.0.1", 10404);
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            String msginput = "";
            
	    while(true){
                a1.setLayout(new BorderLayout());
	        msginput = din.readUTF();
            	JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            }
            
        }catch(Exception e){}
    }    
}

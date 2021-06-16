package chatting.application;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.text.SimpleDateFormat;
public class Server extends JFrame implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1=new JFrame();
    static Box vertical=Box.createVerticalBox();
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;

    Server()
    {
        //div:header
	f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,400,50);
        f1.add(p1);

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
        a1=new JPanel();
        //a1.setBounds(5,55,390,450);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        //f1.add(a1);
        //div:body-ends-----------

	//scrollbar-start
        JScrollPane sp=new JScrollPane(a1);
        sp.setBounds(5,55,390,450);
        sp.setBorder(BorderFactory.createEmptyBorder());

        ScrollBarUI ui=new BasicScrollBarUI(){
            protected JButton createDecreaseButton(int orientation){
                JButton button=super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(7,94,84);
                return button;
            }
            protected JButton createIncreaseButton(int orientation){
                JButton button=super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(7,94,84);
                return button;
            }
        };
        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);
        //scrollbar ends


        //div-footer
        t1=new JTextField();
        t1.setBounds(5,510,300,30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
       f1.add(t1);
        
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
        f1.add(b1);
        //div-ends------------------



        //div:frame configuration
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(400,550);
        f1.setLocation(250,150);
        f1.setUndecorated(true);
        f1.setVisible(true);
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
            JPanel right=new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
	    vertical.add(Box.createVerticalStrut(15));
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
            p.println("Person A:" +message);
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
        new Server().f1.setVisible(true);
        
        String msginput = "";
        try{
            skt = new ServerSocket(10404);
            while(true){
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            
	        while(true){
	                msginput = din.readUTF();
                        JPanel p2 = formatLabel(msginput);
                        
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(p2, BorderLayout.LINE_START);
                        vertical.add(left);
                        f1.validate();
            	}
                
            }
        }catch(Exception e){}
    }    
}

import java.net.Socket;

import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client extends JFrame{

    Socket socket;

    
    BufferedReader br;   //to read
    PrintWriter out;     //to write

    //declare component
    private JLabel heading=new JLabel("Client Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    //private Font font=new Font("Roboto",Font.PLAIN,20);




    //constructor
    public Client()
    {
        try {
             System.out.println("sendind req to server");
            socket=new Socket("127.0.0.1",4356);
            System.out.println("connection done ...");


            br=new BufferedReader(new InputStreamReader(
                socket.getInputStream()
            ));               //socket.getinputstream->byte e return korbe client er data
                              //inputstream->convert byte data into characters
                              //bufferreader makes the characters into buffer

            
            out=new PrintWriter(socket.getOutputStream());
                                //client theke data peye write korche

            

            createGUI();
            handleEventS();

            
            startReading();
           // startWriting();


        } catch (Exception e) {
            
        }
    }

    //message listen kore textbox e dhukbe
    private void handleEventS() {

        messageInput.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent e) {
                
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("key released"+e.getKeyCode());
                if(e.getKeyCode()==10) //keycode of enter is 10
                {
                   // System.out.println("pressed enter button");
                    String contenttosend=messageInput.getText();
                    messageArea.append("Me:"+contenttosend+"\n");
                    out.println(contenttosend);
                    out.flush();
                    messageInput.setText("");
                }
            }
            
        });

    }

    private <font> void createGUI()
    {
        this.setTitle("Client Messenger");
        this.setSize(400,400);
        this.setLocationRelativeTo(null); //center e niye ashe kichu likhle
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cross button e tiple bondho hobe
        this.setVisible(true);


        //coding for component
    
        heading.setHorizontalAlignment(SwingConstants.CENTER); //heading ta center e anbar jonno
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        messageArea.setEditable(false);

        //frame layout
        this.setLayout(new BorderLayout());  //north south east west center

        //adding components to frame
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
    }

    public void startReading()
    {
            //thread - read karke deta rahega

            Runnable r1=()->{
                System.out.println("reader started..");
                try
                {
                while(true)          //jokhn i data ashbe read korbo
                {
                  
                        String msg=br.readLine();             //client er message
                        if(msg.equals("exit"))      //client jodi exit lekhe terminate 
                        {
                            System.out.println("server terminated!");
                            socket.close();
                            break;
                        }
                        //System.out.println("Server : "+msg);      //client j likheche
                         messageArea.append("Server : "+msg +"\n");
                   
                }
            }catch(Exception e){}
            };
            new Thread(r1).start();
    }

    public void startWriting()
    {
           //thread- data user nebe and client ke send korbe

           Runnable r2=()->{
            try {
                while(true && !socket.isClosed())             //bar bar likhte chaile
                {
                    
                   
                        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));      //system.in mane keyboard theke input check
                                                                                                     //then coverted into character then buffer
                        String content=br1.readLine(); //keyboard er puro mal ta line e stored
                        out.println(content);    //out i.e printwriter ta content print korbe
                        out.flush();

                        if(content.equals("exit"))
                        {
                            socket.close();
                            break;
                        }
                        
                    

                }} catch (Exception e) {
                   
                }
           };

           new Thread(r2).start();
    }


    public static void main(String args[])
        
    {
        System.out.print("This is Client");
        try {
            new Client();
            
        } catch (Exception e) {
            
        }

}
}
import java.net.*;
import java.io.*;
public class Server 
{
    ServerSocket server;  //server
    Socket socket;  //client

    BufferedReader br;   //to read
    PrintWriter out;     //to write
    
    
    public Server()
    {
        try {
            System.out.println("hello");
            server=new ServerSocket(4356);  //server is created
            System.out.println("server is ready to accept connection");
            System.out.println("waiting");
            socket=server.accept(); //server client er req accept korbe and it will return as an object of socket i.e client
                                    //connection accepted of client

            br=new BufferedReader(new InputStreamReader(
                socket.getInputStream()
            ));               //socket.getinputstream->byte e return korbe client er data
                              //inputstream->convert byte data into characters
                              //bufferreader makes the characters into buffer

            
            out=new PrintWriter(socket.getOutputStream());
                                //client theke data peye write korche

            
            startReading();
            startWriting();


        } catch (Exception e) {
            
        }
        


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
                            System.out.println("client terminated!");
                            socket.close();
                            break;
                        }
                        System.out.println("Client : "+msg);      //client j likheche
                    
                    
                }}catch(Exception e){}
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
            System.out.print("This is Server");
            new Server();
    }

}
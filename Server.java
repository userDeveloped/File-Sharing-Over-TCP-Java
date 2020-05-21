import java.io.*; 
import java.text.*; 
import java.util.Scanner; 
import java.net.*; 
 
public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
         
        ServerSocket ss = new ServerSocket(8080); // server is listening on port 8080
        System.out.println("Server Started");
        System.out.println("Waiting For Connections ");  
        
        while (true)  
        { 
            Socket s = null;   
            try 
            { 
                 
                s = ss.accept(); // socket object to receive incoming client requests
                System.out.println("A new client is connected : " + s);
                DataInputStream dis = new DataInputStream(s.getInputStream()); // obtaining input and out streams 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
                Thread t = new ClientHandler(s, dis, dos);   // create a new thread object
                t.start();  // Invoking the start() method
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  
{ 
   
    private DataInputStream dis; 
    private DataOutputStream dos; 
    private Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  
    { 
        String received; 
        while (true)  
        { 
            try
            { 
                received = dis.readUTF(); 
                String theFileName = dis.readUTF(); 
                if(received.equals("GET"))
                {
                    File myFile = new File(theFileName);                   
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    DataInputStream bis = new DataInputStream(new FileInputStream(myFile));
                    bis.read(mybytearray, 0, mybytearray.length);                   
                    DataOutputStream os = new DataOutputStream(this.s.getOutputStream());
                    os.write(mybytearray, 0, mybytearray.length);
                    dos.writeUTF("File has been recieved");
                    break;
                } 
                else if(received.equals("PUT"))
                {
                    byte[] my = new byte[32768];
                    DataInputStream c = new DataInputStream(this.s.getInputStream());
                    FileOutputStream b = new FileOutputStream("FileFromClient.txt");
                    DataOutputStream k = new DataOutputStream(b);
                    int brr = c.read(my,0,my.length);
                    k.write(my,0,brr);
                    dos.writeUTF("Recieved the File");
                    break;

                }
                
            }
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 
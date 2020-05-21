import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the data: ");
        String str = input.nextLine();
        String [] splited = str.split("\\s+");
        String theHost = splited[1];
        int thePort = Integer.parseInt(splited[2]);
        String toDo = splited[3];
        String fileName = splited[4];
        //System.out.print(theHost + thePort + toDo + fileName  );

        try
        { 
            InetAddress ip = InetAddress.getByName(theHost); // getting localhost ip 
            Socket s = new Socket(ip, thePort); // establish the connection with server port 
            System.out.println("Establishing a connection"); 
            DataInputStream dis = new DataInputStream(s.getInputStream());  // obtaining input and out streams
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true)  
            {  
                 
                dos.writeUTF(toDo); 
                dos.writeUTF(fileName);
                  
                 if (toDo.equals("GET"))
                {
                    System.out.println("Getting the File");
                    byte[] mybytearray = new byte[32768];
                    DataInputStream is = new DataInputStream(s.getInputStream());
                    FileOutputStream fos = new FileOutputStream("FileFromServer.txt");
                    DataOutputStream bos = new DataOutputStream(fos);
                    System.out.println("File Recieved");
                    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    bos.write(mybytearray, 0, bytesRead);
                    break;
                }
                else if(toDo.equals("PUT"))
                {
                    System.out.println("Sending the File");
                    File myFile = new File(fileName);
                    byte [] my = new byte[(int) myFile.length()];
                    DataInputStream aaa = new DataInputStream(new FileInputStream(myFile));
                    aaa.read(my,0,my.length);
                    DataOutputStream bbb = new DataOutputStream(s.getOutputStream());
                    bbb.write(my,0,my.length);
                    System.out.println("File Sent");
                    break;
                }
            }   

            System.out.println("Servers Response: " + dis.readUTF());
            // closing resources 
            //scn.close(); 
            dis.close(); 
            dos.close(); 
            System.out.println("Closing the connection");
        }
        catch(Exception e){ 
            e.printStackTrace(); 
        } 
        
    } 
} 
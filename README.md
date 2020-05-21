# File-Sharing-Over-TCP-Java

Simple file sharing between Client and Server in java.

# How to compile correctly?

First: 	

Compile and run the MyServer.java file. The default port inside the server code is 8080. If you need to try
different port, you need to manually change it from the source code. To exit from Server you must ctrl-C


Second:

Compile and run the MyClient.java file. Then the program will output the message "Enter the data". The data
that is inputed should be in the format of ("myclient IPADDRESS PORT GET/PUT fileName.txt"). Make sure if 
the option "PUT" is selected, the file you want to send is in the same directory of the java code. If you 
select a different port don't forget to change it in the server code as well. 



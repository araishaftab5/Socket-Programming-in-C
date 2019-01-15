Solution is provided corresponding to following problems:

1. The basic assignment is to write both an Echo Client and an Echo Server in C using UDP  
Datagram  Sockets.   The  client  and   server   implement  the   echo   service  while   running   on 
different Linux machines and communicating with each other using UDP.
 
The Echo Client:
The basic Echo client connects to the Echo server and sends its data to the server. The data 
that the client sends is a string provided as the second client command­line argument. The 
basic Echo client prints the single string of data sent back by the Echo server.
The form of the command line and print line for the basic Echo server are:
Compilation:
Server> gcc  <RegNo>_EchoServer.c ­o <RegNo>_EchoServer
Output command: Server> ./<RegNo>_EchoServer
Server started ... waiting for connection ...
The form of the command line and the print line for the basic Echo client are:
Compilation:
Client> gcc  <RegNo>_EchoClient.c ­o <RegNo>_EchoClient
Output command: Client> ./<RegNo>_EchoClient 172.31.132.x “echo this string!!”
Received from server: echo this string!!
172.31.132.x 
:: the first argument in the output command line is the dotted­quad  
notation IP address of the Echo server.
RegNo
:: should be replaced by the registration number of the student.
The Echo client accepts strings of length 1 to 32 bytes inclusive and prints out an error 
message for any out­of­range input string.
The Echo Server:
After  connecting   to  the  basic   Echo   client,   the  basic   Echo  server   (which   is   started   first) 
simply echoes the string it receives back to the client, disconnects and terminates.
The strings sent and received must be displayed on both client and server consoles.


2.  Write   a  server  program  which   sends  the  time  of   day   information  to   the  client.  Also 
develop a  client  interface for  interacting   with   the  server. The client  should  first  send  a 
request message to the server asking for the time of day information. The server in turn 
responds to the client with its time of day information. The IP address and port number of 
the server is to be passed as command line arguments to the client code. Similar naming  
conventions,   compilation   and   output   commands   are   to   be   used   as   mentioned   in   the 
previous question. Use proper display messages on both the client and server consoles. Use 
UDP sockets. (Naming Convention – TimeOfDayClient.c, TimeOfDayServer.c)

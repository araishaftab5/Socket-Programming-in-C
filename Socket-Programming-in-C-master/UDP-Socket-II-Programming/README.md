
The goal of this assignment is to implement a UDP client and server. Your UDP client/server 
will communicate over the network and exchange data. 
The server will start in passive mode listening on a specified port for a transmission from a 
client. Separately, the client will be started and will contact the server on a given IP address 
and port number that must be entered via the command line. The client will pass the server 
a string consisting of a sequence of characters. If the string contains anything but numbers, 
the server will respond with "Sorry, cannot compute!" and exit. If the string contains all 
numbers, the individual digits will be added together and returned as a string (see below 
for an example). If the server sends a "Sorry" response to the client it will immediately exit. 
If the server receives a string of numbers, it will (1) add the digits together, (2) send the 
value back to the client, and (3) will not exit unless the response is a single digit. This 
process will be repeated until there is only one digit remaining. Note: the server will send a  
new packet each time Step (2) is executed, and the client will expect to receive a packet 
until there is only a single digit. See below for the exact output. 
Procedure:

Starting the Server ­
Assume that you started a server on machine, 172.31.132.x; listening on port 
number, p. The syntax should look like the following: 
machine1> gcc <RegNo>_UDPServer.c  ­o  <RegNo>_UDPServer
machine1> ./<RegNo>_UDPServer  p

• The server should not produce any output but should end after interacting 
with a client.
 
Starting the Client ­
machine2> gcc <RegNo>_UDPClient.c  ­o  <RegNo>_UDPClient
machine2> ./<RegNo>_UDPClient  172.31.132.x  p
Client Input/Output for Non­Numeric Example ­
machine2> gcc <RegNo>_UDPClient.c  ­o  <RegNo>_UDPClient
machine2> ./<RegNo>_UDPClient  172.31.132.x  p
Enter string: I don't like addition!! 
From server: Sorry, cannot compute! 
machine2> 
Client Input/Output for Numeric Example ­
machine2> gcc <RegNo>_UDPClient.c  ­o  <RegNo>_UDPClient
machine2> ./<RegNo>_UDPClient  172.31.132.x  p 
Enter string: 123456789101234567891012345678910 
From server: 138 
From server: 12 
From server: 3 
machine2> 

Program #1

String Reversal Concurrent Server using TCP Sockets

A concurrent server is a server that waits on the welcoming socket
and then creates a new thread or process to handle the incoming request.
In this program use fork() system call to create new processes. There is a single process accepting
incoming connections. After a connection is made, the server forks a copy of itself to process the
request and then returns to accepting incoming connections. Modify your string reversal server code
to fork a new process after accepting a new connection. The child process should handle the actual
string reversal task, whereas the parent should continue to accept new connections. Make sure you
close sockets in the right places.

Program #2

Write a program named CalcClientTCP.c (or .cpp) that performs the following functions:
1. Take a server hostname and a port number as command-line arguments.
Note: Your client must resolve the hostname into an IP address.
2. Connect to the server at the given hostname and port using TCP.
3. Print the IP address and port of the server.
4. Ask the user for a simple arithmetic expression to calculate.
5. Send the expression to the server.
6. Read the answer from the server.
7. Display the answer to the user.
8. Repeat the steps 4-7 until the user enters the sentinel value given in the user prompt. When the user
enters the sentinel, the client must close the connection to the server and quit.
Write a program named CalcServerTCP.c (or .cpp) that performs the following functions:
1. Take a port number as a command-line argument.
2. Listen for a TCP connection on the port specified.
3. Print the IP address and port of the connected client.
4. Receive data from the client.
5. Evaluate the arithmetic expression.
6. Send the result back to the client.
7. If the connection is still open, repeat the steps 4-6 until the user presses Ctrl-C. If the connection is
closed, repeat steps 2-6 until the user presses Ctrl-C.

Now create a server is concurrent that can handle multiple clients at a time. In this
program use POSIX threads to create concurrency. Test your code by connecting multiple clients at
the same time.


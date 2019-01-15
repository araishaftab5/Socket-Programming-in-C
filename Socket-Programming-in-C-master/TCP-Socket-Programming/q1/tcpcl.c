#include<stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
int main(int argc,char *argv[])
{
	int servsocket,clientsocket;
	clientsocket=socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	
	struct sockaddr_in serverAddr,clientAddr;
		
	serverAddr.sin_family = AF_INET;
  	serverAddr.sin_port = htons(atoi(argv[1]));
  	serverAddr.sin_addr.s_addr = inet_addr(argv[2]);
	connect(clientsocket, (struct sockaddr *) &serverAddr,sizeof(serverAddr));
	while(1)
	{
	
	char buf[1024],buff[1024];
	printf("Message for server");
	scanf("%s",buf);
	int len=strlen(buf)+1;
	send(clientsocket, buf, len, 0);

	recv(clientsocket,buff,1024,0);
	printf("From server:%s",buff);

	
	}
close(clientsocket);
}

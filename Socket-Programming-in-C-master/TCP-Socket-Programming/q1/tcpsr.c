#include<stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
int main(int argc,char *argv[])
{
	int servsocket,clientsocket,i,j;
	servsocket=socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	
	struct sockaddr_in serverAddr,clientAddr;
		
	serverAddr.sin_family = AF_INET;
  	serverAddr.sin_port = htons(atoi(argv[1]));
  	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	bind(servsocket,(struct sockaddr *) &serverAddr, sizeof(serverAddr));
	listen(servsocket,5);
	
	printf("Server listening on port %d",servsocket);
	
	for(;;)
	{
		
		int clientLen=sizeof(clientAddr);
		clientsocket=accept(servsocket,(struct sockaddr *)&clientAddr,&clientLen);
		printf("Client connected on port %d",clientsocket);
		while(1){
		char buf[1024];
		int recvMsgSize = recv(clientsocket, buf, 1024, 0);
		if(recvMsgSize==0)goto end;
		printf("client sent %s",buf);
		int len=strlen(buf);
		for(i=0,j=len-1;i<=j;i++,j--)
		{
			char t=buf[i];
			buf[i]=buf[j];
			buf[j]=t;
			printf("hi");
		}
		printf("server sent %s",buf);
		send(clientsocket, buf, strlen(buf)+1, 0);
		}end:
		close(clientsocket);
		goto end1;			
	}
	end1:
	close(servsocket);
}

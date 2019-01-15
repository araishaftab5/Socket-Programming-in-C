/*Name = Ria Jain
  Reg.no = 20165067
  concurrent server program */

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
	int port=atoi(argv[1]);
	printf("Server listening on port and socket id %d %d\n",port,servsocket);
	for(;;)
	{
		int buf[1024];
		int clientLen=sizeof(clientAddr);
		clientsocket=accept(servsocket,(struct sockaddr *)&clientAddr,&clientLen);
		inet_ntop(AF_INET, &(clientAddr.sin_addr), buf, clientLen);
		printf("Client connected on port and clietn socket  %d  %d\n",port,clientsocket);
		int pid=fork();
		if(pid==0)
		{
			close(servsocket);
			int len = recv(clientsocket, buf, 1024, 0);
			//printf("client sent %s\n",buf);
			//int len=strlen(buf);
			for(i=0;i<len;i++)
			{
				for(j=0;j<n-i-1;j++)
				{if(a[j]>a[j+1]){
				int t=a[j];
				a[j]=a[j+1];
				a[j+1]=t;}}
			}
			
			send(clientsocket, a,len, 0);
			close(clientsocket);
			exit(0);
		}
		close(clientsocket);	
	}
	close(servsocket);
}

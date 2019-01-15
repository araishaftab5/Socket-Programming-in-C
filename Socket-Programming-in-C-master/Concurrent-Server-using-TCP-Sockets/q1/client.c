/*Name = Ria Jain
  Reg.no = 20165067
  concurrent client program */

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
	char buf[1024],buff[1024];
	int a[1024],i,n;
	scanf("%d",&n);
	printf("Message for server");
	for(i=0;i<n;i++)
	scanf("%d",&a[i]);
	int len=strlen(buf)+1;
	send(clientsocket, a, n, 0);
	recv(clientsocket,a,1024,0);
	for(i=0;i<n;i++)
	printf("From server:%d",a[i]);
	close(clientsocket);
}

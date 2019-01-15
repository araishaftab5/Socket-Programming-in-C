#include<stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
int main(int argc,char *argv[])
{
	int servsocket,nBytes,i;
	char buf[1024];
	struct sockaddr_in serverAddr,clientAddr;
	servsocket=socket(PF_INET, SOCK_DGRAM, 0);
	socklen_t addrsize, clientsize;

	serverAddr.sin_family = AF_INET;
  	serverAddr.sin_port = htons(atoi(argv[1]));
  	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);

	bind(servsocket,(struct sockaddr *) &serverAddr, sizeof(serverAddr));
	
	addrsize=sizeof(clientAddr);
	nBytes = recvfrom(servsocket,buf,1024,0,(struct sockaddr *)&clientAddr, &addrsize);
	printf("%s",buf);
	sendto(servsocket,buf,strlen(buf)+1,0,(struct sockaddr *)&clientAddr,addrsize);		
	close(servsocket);
	return 0;
}


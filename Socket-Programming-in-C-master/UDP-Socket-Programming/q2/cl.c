#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include<netdb.h>
#include<time.h>
int main(int argc,char *argv[])
{
	
	int clsocket,nBytes;
	char buf[1024];
	struct sockaddr_in serverAddr;
	socklen_t addr_size;
		
	clsocket = socket(PF_INET, SOCK_DGRAM, 0);
	
	serverAddr.sin_family = AF_INET;
  	serverAddr.sin_port = htons(atoi(argv[1]));
  	serverAddr.sin_addr.s_addr =  inet_addr(argv[2]);
	
	int addrsize=sizeof(serverAddr);
	
	nBytes = strlen(argv[3]) + 1;
	sendto(clsocket,argv[3],nBytes,0,(struct sockaddr *)&serverAddr,addrsize);
	recvfrom(clsocket,buf,1024,0,(struct sockaddr *)&serverAddr, &addrsize);
	printf("From server %s\n",buf);
	close(clsocket);

}
		

#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include<netdb.h>
int check(char a[1024])
{
	int i,n=strlen(a);
	for(i=0;i<n;i++)
	{
		if(a[i]<48||a[i]>57)
		return -1;
	}
return 1;
}
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
	printf("enter string");
	scanf("%s",buf);
	nBytes = strlen(buf) + 1;
	sendto(clsocket,buf,nBytes,0,(struct sockaddr *)&serverAddr,addrsize);
	if(check(buf)==-1)
	goto end;
	
	while(1)
	{
		recvfrom(clsocket,buf,1024,0,(struct sockaddr *)&serverAddr, &addrsize);
		printf("From server %s\n",buf);
		if(strlen(buf)==1)
		goto end;
		
	}
	end:
	close(clsocket);

}
		

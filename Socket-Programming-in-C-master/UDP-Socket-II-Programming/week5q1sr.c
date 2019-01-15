#include<stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
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
int find(char a[1024])
{
	int i,n=strlen(a),su=0;
	for(i=0;i<n;i++)
	su+=(a[i]-'0');
	return su;
}
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
	printf("hi");
	nBytes = recvfrom(servsocket,buf,1024,0,(struct sockaddr *)&clientAddr, &addrsize);
	printf("%s",buf);
	int b=check(buf);
	if(b==-1)
	{
			printf("Sorry could not compute");
			goto end;
	}
	while(1)
	{
			char buff[100];
			int sum=find(buf);						
			sprintf(buff,"%d",sum);
			sendto(servsocket,buff,strlen(buff)+1,0,(struct sockaddr *)&clientAddr,addrsize);		
			if(strlen(buff)==1)
			goto end;
			for(i=0;i<1024;i++)
                    	buf[i] = 0;
			sprintf(buf,"%d",sum);
		
	}
	end:
	close(servsocket);
	return 0;
}


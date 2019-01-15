/*Name = Ria Jain
  Reg.no = 20165067
  concurrent server program */

#include<stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <math.h>
#include<pthread.h>
void *ThreadMain(void *arg);
int power(int x,int y) 
{ 
    if (y == 0) 
        return 1; 
    else if (y%2 == 0) 
        return power(x, y/2)*power(x, y/2); 
    else
        return x*power(x, y/2)*power(x, y/2); 
} 
double calc(double a,double b,char c)
{
	switch(c)
	{
		case '+': 
			return a+b;
		case '-': 
			return a-b;
		case '*': 
			return a*b;
		case '/': 
			return a/b;
		case '^': 
			return power(a,b);
		default: 
			return 0;
	}
}
struct ThreadArgs
{
	int clientsocket;
};
int main(int argc,char *argv[])
{
	int servsocket,clientsocket,i,j;
	servsocket=socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	pthread_t threadID;
	struct sockaddr_in serverAddr,clientAddr;
	struct ThreadArgs *threadArgs;	
	serverAddr.sin_family = AF_INET;
  	serverAddr.sin_port = htons(atoi(argv[1]));
  	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	bind(servsocket,(struct sockaddr *) &serverAddr, sizeof(serverAddr));
	listen(servsocket,5);
	int port=atoi(argv[1]);
	printf("Server listening on port and socket id %d %d\n",port,servsocket);
	for(;;)
	{
		char buf[1024];
		int clientLen=sizeof(clientAddr);
		clientsocket=accept(servsocket,(struct sockaddr *)&clientAddr,&clientLen);	
		inet_ntop(AF_INET, &(clientAddr.sin_addr), buf, clientLen);
		printf("Client connected on port and clietn socket  %d  %d\n",port,clientsocket);
		threadArgs = (struct ThreadArgs *) malloc(sizeof(struct ThreadArgs));
		threadArgs -> clientsocket = clientsocket;		
		pthread_create(&threadID, NULL, ThreadMain, (void *) threadArgs);
	}
	close(servsocket);
}
void *ThreadMain(void *threadArgs)
{
		int clntSock;
		clntSock = ((struct ThreadArgs *) threadArgs) -> clientsocket;
		free(threadArgs);
		char a[20]="";
		recv(clntSock,a,20, 0);	
		printf("Client sent : %s \n",a);
		double d=atof(strtok(a," "));
		char *ch=strtok(NULL," ");
		char c=ch[0];
		double b=atof(strtok(NULL," "));
		int num = atoi(a);
		if(num==-1)
		{
			printf("Bye !");
			return 0;
		}
		for(int i=1;i<20;i++)
		{
			if( (a[i]=='+' || a[i]=='-' || a[i]=='*' || a[i]=='/' || a[i]=='^')  ) 
			{
				c=a[i];
				break; 
			}
		}
		char ans[10];
		sprintf(ans,"%0.3lf",calc(d,b,c));
		printf("Ans =%s \n\n",ans);
		send(clntSock,ans,10,0);
		return (NULL);
}

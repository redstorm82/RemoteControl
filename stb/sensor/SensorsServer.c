#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/stat.h>
#include <errno.h>
#include <sys/time.h>
#include <cutils/sockets.h>
//#include "version.h"
#include "SensorsServer.h"

#include <utils/Log.h>

#define EB_LOG_NORMAL ANDROID_LOG_INFO
//#define EB_LOGD(level, fmt, ...) __android_log_print(level, "SENSOR4", fmt, ##__VA_ARGS__)
//#define EB_LOGE(fmt, ...) __android_log_print(ANDROID_LOG_ERROR, "SENSOR4", fmt, ##__VA_ARGS__)
#define EB_LOGD(level, fmt, ...)  ((void)0)
#define EB_LOGE(fmt, ...)  ((void)0)



void PrintData(unsigned char *pData, int nLen)
{
	while (nLen > 0)
	{
		if (nLen >= 16)
		{
			__android_log_print(ANDROID_LOG_ERROR, "SENSOR4", 
				"0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x", 
				pData[0], pData[1], pData[2], pData[3], pData[4], pData[5], pData[6], pData[7], pData[8], pData[9], pData[10], pData[11], pData[12], pData[13], pData[14], pData[15], pData[16]);
			nLen -= 16;
			pData+=16;
		}
		else
		{
			__android_log_print(ANDROID_LOG_ERROR, "SENSOR4", "0x%02x", *pData++);
			nLen--;
		}
	}
}



int MakeSocketIn()
{
    struct sockaddr_in sClientAddr;
    int nSocketFd = -1;

    nSocketFd = socket(AF_INET, SOCK_DGRAM, 0);
    EB_LOGD(EB_LOG_NORMAL, "nSocketFd = %d", nSocketFd);
    if (nSocketFd < 0) {
        EB_LOGE("create socket failed, errno = %d", errno);
        return -1;
    }

    memset(&sClientAddr, 0, sizeof(sClientAddr));
    sClientAddr.sin_family = AF_INET;
    sClientAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    sClientAddr.sin_port = htons(UDP_LISTEN_PORT);

    if(bind(nSocketFd, (struct sockaddr *)(&sClientAddr), sizeof(sClientAddr)) < 0) {
        EB_LOGE("socket bind failed, errno = %d", errno);
        close(nSocketFd);
        nSocketFd = -1;
        return -1;
    }

    return nSocketFd;
}

int RecvSensorsData(int nSocketFd, void *pvBuff, int bufSize)
{
   int  nRecvLen;
    int nAddrLen;
    struct sockaddr_in recvAddr;
    char acData[1024];
    int contentLen;
	unsigned char *pData = NULL;
	unsigned int dwDataLen = 0;

//    EB_LOGD(EB_LOG_NORMAL, "%s", VERSION_NUMBER);

    nAddrLen = sizeof(recvAddr);

    do {
        nRecvLen = recvfrom(nSocketFd, acData, sizeof(acData), 0, (struct sockaddr *)(&recvAddr), &nAddrLen);
        EB_LOGD(EB_LOG_NORMAL, "socket recvfrom, nRecvLen = %d", nRecvLen);
		EB_LOGE("socket recvfrom, nRecvLen = %d", nRecvLen);
		pData = (unsigned char *)acData;
		
#if 0		
		//������(2BYTE) 	Type(1BYTE) 	������(1BYTE) 	���ɳ��ȣ�2BYTE�� 	У���루2BYTE�� 	���� 
		//AABB 				0 				0-255ѭ�� 		�������ݳ��� 		�������ݵ�CRC16У�� ���������� 
		#define HEAD_LEN (8)
		#define DATA_MAX (65535)
		if (nRecvLen <= HEAD_LEN || nRecvLen >=  (DATA_MAX + HEAD_LEN))
		{
			continue;
		}

		if (pData[0] != 0xAA || pData[1] != 0xBB || pData[2] != 0)
		{
			continue;
		}

		dwDataLen = pData[4] << 8 | pData[5];
		if ((dwDataLen + HEAD_LEN) != nRecvLen)
		{
			continue;
		}
		//�����룬У���� ��������

		bufSize = bufSize > dwDataLen ? dwDataLen : bufSize;
		memcpy(pvBuff, pData+HEAD_LEN, bufSize);
#else
		#define HEAD_LEN (32)
		if (nRecvLen > HEAD_LEN)
		{
			bufSize = nRecvLen-HEAD_LEN;
			memcpy(pvBuff, pData+HEAD_LEN, bufSize);
			
			//PrintData(pData+48, bufSize);
			//pData[nRecvLen] = 0;
			//EB_LOGE("xcy---%s", (char *)(pData+48));
		}
#endif

        /*if(nRecvLen > 0) {
            EasybusAddr addr;
            EasybusMsg data;

            memset((void *)&addr, 0, sizeof(addr));
            strcpy(addr.ip, inet_ntoa(recvAddr.sin_addr));
            addr.port = ntohs(recvAddr.sin_port);
            EB_LOGD(EB_LOG_NORMAL, "recvAddr:%s:%d", addr.ip, addr.port);

            memset((void *)&data, 0, sizeof(data));
            decompose_frame(acData, nRecvLen, &addr, &data);
            EB_LOGD(EB_LOG_NORMAL, "data.msgData = %s", data.msgData);
            contentLen = strlen(data.msgData);
            memcpy(pvBuff, data.msgData, contentLen < bufSize ? contentLen : bufSize);
		}*/

        EB_LOGD(EB_LOG_NORMAL, "errno = %d", errno);
    } while (nRecvLen < 0 && errno == EINTR);
	
//	memset(pvBuff, 0x88, bufSize);

    EB_LOGD(EB_LOG_NORMAL, "contentLen", contentLen);
    return bufSize;
}


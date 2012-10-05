/*
    jhid - Java API for Human Interaction Devices
    (c) Copyright 2004 Guillaume Pothier
    Project home page: jhid.sourceforge.net

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

#include <jni.h>
#include "net_jhid_impl_linux_LinuxNative.h"

#include <stdlib.h>
#include <stdio.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <asm/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <linux/input.h>

JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_getFD
  (JNIEnv *env, jclass class, jstring fileName)
{
	const char *str = (*env)->GetStringUTFChars(env, fileName, 0);
	int fd = open(str, O_RDONLY);
    (*env)->ReleaseStringUTFChars(env, fileName, str);
    
    return fd;
}

JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGNAME
  (JNIEnv *env, jclass class, jint fd, jbyteArray jbuffer)
{
	jsize len = (*env)->GetArrayLength(env, jbuffer);
	jbyte *buffer = (*env)->GetByteArrayElements(env, jbuffer, 0);

	int result = ioctl(fd, EVIOCGNAME(len), buffer);
	
	(*env)->ReleaseByteArrayElements(env, jbuffer, buffer, 0);
	
    return result;
}

JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGID
  (JNIEnv *env, jclass class, jint fd, jbyteArray jbuffer)
  
{
	jbyte *buffer = (*env)->GetByteArrayElements(env, jbuffer, 0);

	int result = ioctl(fd, EVIOCGID, buffer);
	
	(*env)->ReleaseByteArrayElements(env, jbuffer, buffer, 0);
	
    return result;
}


JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGBIT
  (JNIEnv *env, jclass class, jint fd, jint type, jbyteArray jbuffer)
{
	jsize len = (*env)->GetArrayLength(env, jbuffer);
	jbyte *buffer = (*env)->GetByteArrayElements(env, jbuffer, 0);

	memset (buffer, 0, len);
	int result = ioctl(fd, EVIOCGBIT(type, len), buffer);
	
	(*env)->ReleaseByteArrayElements(env, jbuffer, buffer, 0);
	
    return result;
}




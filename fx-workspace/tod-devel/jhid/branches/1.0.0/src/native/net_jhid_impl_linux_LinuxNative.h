/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class net_jhid_impl_linux_LinuxNative */

#ifndef _Included_net_jhid_impl_linux_LinuxNative
#define _Included_net_jhid_impl_linux_LinuxNative
#ifdef __cplusplus
extern "C" {
#endif
/* Inaccessible static: BUFFER */
/*
 * Class:     net_jhid_impl_linux_LinuxNative
 * Method:    getFD
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_getFD
  (JNIEnv *, jclass, jstring);

/*
 * Class:     net_jhid_impl_linux_LinuxNative
 * Method:    ioctl_EVIOCGNAME
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGNAME
  (JNIEnv *, jclass, jint, jbyteArray);

/*
 * Class:     net_jhid_impl_linux_LinuxNative
 * Method:    ioctl_EVIOCGID
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGID
  (JNIEnv *, jclass, jint, jbyteArray);

/*
 * Class:     net_jhid_impl_linux_LinuxNative
 * Method:    ioctl_EVIOCGBIT
 * Signature: (II[B)I
 */
JNIEXPORT jint JNICALL Java_net_jhid_impl_linux_LinuxNative_ioctl_1EVIOCGBIT
  (JNIEnv *, jclass, jint, jint, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif
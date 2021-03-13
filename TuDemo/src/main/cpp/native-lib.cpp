#include "jni.h"
#include <string>
#include "jvmti.h"
#include "utils.h"
#include <android/log.h>

jvmtiEnv *mJvmtiEnv = NULL;
extern  "C"
JNIEXPORT jint JNICALL
Agent_OnAttach(JavaVM* vm, char* options, void* reserved) {
    vm->GetEnv(reinterpret_cast<void **>(&mJvmtiEnv), JVMTI_VERSION);
    // 开启jvmti的某些能力
    jvmtiCapabilities caps;
    // 获得所有支持的能力
    mJvmtiEnv->GetPotentialCapabilities(&caps);
    mJvmtiEnv->AddCapabilities(&caps);
    return JNI_OK;
}

/**
 * 开启jvmti的能力，使用jvmti的能力
 * 在java传下来的日历目录中生成一个日志文件
*/
extern  "C"
JNIEXPORT void JNICALL
Monitor.native_init(JNIEnv *env, jclass jclazz, jstring _path) {
    const char *path = env->GetStringUTFChars(_path, 0)

    env->ReleaseStringUTFChars(_path, path)
}

extern  "C"
JNIEXPORT void JNICALL
Monitor.native_release(JNIEnv *env, jclass jclazz) {
}
#include <jni.h>
#include <string.h>

#include "jvmti.h"
#include "MemoryFile.h"
#include "utils.h"

#include <android/log.h>

#define LOG_TAG "Monitor"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

MemoryFile *memoryFile = 0;

jlong tag = 0;

jvmtiEnv *mJvmtiEnv = 0;


//事件名称|时间戳 线程名 对象签名 对象大小|调用栈第一行(方法所在类签名 方法名 方法签名),调用栈第二行,调用栈第三行
void JNICALL objectAlloc(jvmtiEnv *jvmti_env,
                         JNIEnv *jni_env,
                         jthread thread,
                         jobject object,
                         jclass object_klass,
                         jlong size) {
    //给对象打tag
    tag += 1;
    jvmti_env->SetTag(object, tag);
    //获得线程信息
    jvmtiThreadInfo threadInfo;
    jvmti_env->GetThreadInfo(thread, &threadInfo);
    //获得创建的对象的类签名
    char *classSignature;
    jvmti_env->GetClassSignature(object_klass, &classSignature, nullptr);
    char *baseInfo;
    int64_t timeMillis = currentTimeMillis();
    asprintf(&baseInfo, "TIME:%lld THREAD:%s Class:%s Size:%lld TAG:%lld",
             timeMillis,
             threadInfo.name,
             classSignature,
             size,
             tag);

    jvmti_env->Deallocate((unsigned char *) classSignature);
    //获得堆栈信息
    char *stackInfo = createStackInfo(jvmti_env, jni_env, thread, 10);
    char *line;
    asprintf(&line, "ObjectAlloc|%s\n%s\n", baseInfo, stackInfo);
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
    free(baseInfo);
    free(stackInfo);
    free(line);
}

void JNICALL objectFree(jvmtiEnv *jvmti_env, jlong tag) {
    char *baseInfo;
    int64_t timeMillis = currentTimeMillis();
    asprintf(&baseInfo, "TIME:%lld TAG:%lld",
             timeMillis, tag);
    char *line;
    asprintf(&line, "ObjectFree|%s\n", baseInfo);
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
}


void JNICALL gcStart(jvmtiEnv *jvmti_env) {
    int64_t now = currentTimeMillis();

    char *line;
    asprintf(&line, "GC/S|%lld\n", now);
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
    free(line);
}

void JNICALL gcFinish(jvmtiEnv *jvmti_env) {
    char *line;
    asprintf(&line, "GC/F|%lld\n", currentTimeMillis());
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
    free(line);
}

void SetEventNotification(jvmtiEnv *jvmti, jvmtiEventMode mode,
                          jvmtiEvent event_type) {
    jvmti->SetEventNotificationMode(mode, event_type, nullptr);
}


void JNICALL methodEntry
        (jvmtiEnv *jvmti_env,
         JNIEnv *jni_env,
         jthread thread,
         jmethodID method) {
    //获得线程信息
    jvmtiThreadInfo threadInfo;
    jvmti_env->GetThreadInfo(thread, &threadInfo);
    //获得方法签名
    char *method_signature;
    char *method_name;
    jvmti_env->GetMethodName(method, &method_name, &method_signature, nullptr);
    //获取方法所在类的签名
    jclass declaringClass;
    char *classSignature;
    jvmti_env->GetMethodDeclaringClass(method, &declaringClass);
    jvmti_env->GetClassSignature(declaringClass, &classSignature, nullptr);

    char *line;
    asprintf(&line, "MethodEntry|METHOD:%s(%s) TIME:%lld THREAD:%s Class:%s\n", method_name,
             method_signature,
             currentTimeMillis(),
             threadInfo.name, classSignature);
    jvmti_env->Deallocate((unsigned char *) method_name);
    jvmti_env->Deallocate((unsigned char *) method_signature);
    jvmti_env->Deallocate((unsigned char *) classSignature);
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
    free(line);
}

void JNICALL methodExit(jvmtiEnv *jvmti_env,
                        JNIEnv *jni_env,
                        jthread thread,
                        jmethodID method,
                        jboolean was_popped_by_exception,
                        jvalue return_value) {

    //获得线程信息
    jvmtiThreadInfo threadInfo;
    jvmti_env->GetThreadInfo(thread, &threadInfo);

    //获得方法签名
    char *method_signature;
    char *method_name;
    jvmti_env->GetMethodName(method, &method_name, &method_signature, nullptr);
    //获取方法所在类的签名
    jclass declaringClass;
    char *classSignature;
    jvmti_env->GetMethodDeclaringClass(method, &declaringClass);
    jvmti_env->GetClassSignature(declaringClass, &classSignature, nullptr);

    char *line;
    asprintf(&line, "MethodExit|METHOD:%s(%s) TIME:%lld THREAD:%s Class:%s\n", method_name,
             method_signature,
             currentTimeMillis(),
             threadInfo.name, classSignature);
    jvmti_env->Deallocate((unsigned char *) method_signature);
    jvmti_env->Deallocate((unsigned char *) classSignature);
    if (memoryFile) {
        memoryFile->write(line, strlen(line));
    }
    free(line);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_tustar_demo_ui_optimize_Monitor_nInit(JNIEnv *env, jobject jclazz, jstring _path) {
    const char *path = env->GetStringUTFChars(_path, 0);
    char *filepath;
    asprintf(&filepath, "%s/%lld.log", path, currentTimeMillis());
    memoryFile = new MemoryFile(filepath);
    free(filepath);

    //3、设置事件监听
    jvmtiEventCallbacks callbacks;
    memset(&callbacks, 0, sizeof(callbacks));
    callbacks.VMObjectAlloc = &objectAlloc;
    callbacks.ObjectFree = &objectFree;
    callbacks.GarbageCollectionStart = &gcStart;
    callbacks.GarbageCollectionFinish = &gcFinish;
    callbacks.MethodEntry = &methodEntry;
    callbacks.MethodExit = &methodExit;
    mJvmtiEnv->SetEventCallbacks(&callbacks, sizeof(callbacks));

    //开启事件监听
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_VM_OBJECT_ALLOC);
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_OBJECT_FREE);
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_GARBAGE_COLLECTION_START);
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_GARBAGE_COLLECTION_FINISH);
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_METHOD_ENTRY);
    SetEventNotification(mJvmtiEnv, JVMTI_ENABLE, JVMTI_EVENT_METHOD_EXIT);


    env->ReleaseStringUTFChars(_path, path);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_tustar_demo_ui_optimize_Monitor_nRelease(JNIEnv *env, jobject clazz) {
    if (memoryFile) {
        delete memoryFile;
        memoryFile = 0;
    }
    //关闭事件监听
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_VM_OBJECT_ALLOC);
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_OBJECT_FREE);
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_GARBAGE_COLLECTION_START);
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_GARBAGE_COLLECTION_FINISH);
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_METHOD_ENTRY);
    SetEventNotification(mJvmtiEnv, JVMTI_DISABLE, JVMTI_EVENT_METHOD_EXIT);
}

JNIEXPORT jint JNICALL Agent_OnAttach(JavaVM *vm, char *options, void *reserved) {
    //1、获得jvmtiEnv
    jvmtiEnv *jvmtiEnv;
    jint result = vm->GetEnv(reinterpret_cast<void **>(&jvmtiEnv), JVMTI_VERSION_1_2);
    if (result != JNI_OK) {
        return JNI_ERR;
    }
    mJvmtiEnv = jvmtiEnv;
    //2、开启所有支持的能力
    jvmtiCapabilities caps;
    jvmtiEnv->GetPotentialCapabilities(&caps);
    jvmtiEnv->AddCapabilities(&caps);
    return JNI_OK;
}
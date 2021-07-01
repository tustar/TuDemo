#ifndef TUDEMO_UTILS_H
#define TUDEMO_UTILS_H

#include <sys/time.h>
#include <stdio.h>
#include <cstdlib>
#include "jvmti.h"


int64_t currentTimeMillis() {
    struct timeval tv = {};
    gettimeofday(&tv, NULL);
    int64_t factor = 1000;
    int64_t result = (int64_t) ((factor * tv.tv_sec) + (tv.tv_usec / 1000));
    return result;
}


/**
 * 根据线程和指定深度获取调用栈
 */
char *createStackInfo(jvmtiEnv *jvmti, JNIEnv *env, jthread thread, int stackDepth) {
    char *result = nullptr;
    jvmtiFrameInfo frames[stackDepth];
    jint count;
    jvmtiError err;

    //获得调用堆栈
    err = jvmti->GetStackTrace(thread, 0, stackDepth, frames, &count);
    if (err != JVMTI_ERROR_NONE) {
        return result;
    }
    if (count <= 0) {
        return result;
    }
    for (int i = 0; i < count; i++) {
        jvmtiFrameInfo info = frames[i];
        char *classSignature;
        char *methodName;
        //获取方法名
        err = jvmti->GetMethodName(info.method, &methodName, nullptr, nullptr);
        if (err != JVMTI_ERROR_NONE) {
            break;
        }
        //获取方法所在类
        jclass declaringClass;
        err = jvmti->GetMethodDeclaringClass(info.method, &declaringClass);
        if (err != JVMTI_ERROR_NONE) {
            break;
        }
        //获取方法所在类的签名
        err = jvmti->GetClassSignature(declaringClass, &classSignature, nullptr);
        if (err != JVMTI_ERROR_NONE) {
            break;
        }

        if (result == nullptr) {
            asprintf(&result, "%s#%s", classSignature, methodName);
        } else {
            char *stack;
            asprintf(&stack, "%s\n%s#%s",
                     result,
                     classSignature,
                     methodName);
            free(result);
            result = stack;
        }
        jvmti->Deallocate((unsigned char *) classSignature);
        jvmti->Deallocate((unsigned char *) methodName);
    }
    return result;
}

#endif //TUDEMO_UTILS_H

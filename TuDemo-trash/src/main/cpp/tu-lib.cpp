#include <jni.h>
#include <string>

void callJavaMethod(JNIEnv *env, jobject object) ;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_tustar_demo_module_ryg_ch14_RygCh14Activity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */object) {
    std::string hello = "Hello from C++";
//    callJavaMethod(env,object);
    return env->NewStringUTF(hello.c_str());
}

void callJavaMethod(JNIEnv *env, jobject object) {
    jclass  clazz = env -> FindClass("com/tustar/demo/module/ryg/ch14/RygCh14Activity");
    if (clazz == NULL) {
        printf("find class RygCh14Activity error!");
        return;
    }

    jmethodID id = env->GetMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");
    if (id == NULL) {
        printf("find method methodCalledByJni error!");
    }

    jstring msg = env->NewStringUTF("msg send by methodCalledByJni in app");
    env->CallVoidMethod(clazz, id, msg);
}

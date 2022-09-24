-------------------------------------------
        Android Native Service
-------------------------------------------
1.拷贝native文件夹下代码到aosp/external目录
2.cd aosp
  source build/envsetup.sh
  lunch
  选择平台 2. aosp_arm64-eng
  make -j4
  cd external/tu
  mm
  编译成功后, 会在/aosp/out/target/product/generic_arm64/system/bin目录生成tu_server和tu_client
3.adb root
  adb remount
  adb push tu_server /system/bin
  adb push tu_client /system/bin
4.adb shell logcat -s TuNativeService
5.开启窗口运行server
  adb shell
  /system/bin/tu_server
6.开启窗口运行client
  adb shell
  /system/bin/tu_client

-------------------------------------------
        Android Framework Binder
-------------------------------------------


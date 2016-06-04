Test case for issue #...
========================

Method `llvm.memset.p0i8.i32` don't define a return type

```scala
@extern
object Intrinsics { 
  def `llvm.memset.p0i8.i32`(dest: Ptr[Byte],
                             value: Byte,
                             len: Int,
                             align: Int,
                             isvolatile: Boolean) /* : Unit */ = extern
}
```

Compile and link are successful


```
francois@ubuntu:~/proyectos/oss/scala-native-fbd/sn-test-enforce_return_type_on_extern$ sbt run
[info] Loading project definition from /home/francois/proyectos/oss/scala-native-fbd/sn-test-enforce_return_type_on_extern/project
[info] Set current project to sn-test-enforce_return_type_on_extern (in build file:/home/francois/proyectos/oss/scala-native-fbd/sn-test-enforce_return_type_on_extern/)
warning: overriding the module target triple with x86_64-pc-linux-gnu [-Woverride-module]
1 warning generated.
[success] Total time: 2 s, completed Jun 4, 2016 3:28:27 PM
```

Segmentation fault (core dumped) when running.

```
francois@ubuntu:~/proyectos/oss/scala-native-fbd/sn-test-enforce_return_type_on_extern$ ./target/scala-2.11/sn-test-enforce_return_type_on_extern-out 
Segmentation fault (core dumped)
```

The main() function is truncated in ${PROJECTNAME}-out.ll.  The expected fprintf is replaced by "unreachable".

```
define void @"cl.fbd.Main::main_class.ssnr.ObjectArray_unit"(i8* %src.0, i8* %src.1)personality i8* bitcast (i32 (...)* @__gxx_personality_v0 to i8*) {
  
src.2:
  %src.3 = call i8* () @"load.scala.scalanative.runtime.GC"()
  %src.4 = call i8* (i64) @"GC_malloc"(i64 100)
  %src.6 = call i8* () @"load.cl.fbd.Intrinsics"()
  %src.7 = trunc i16 65 to i8
  call void (i8*, i8, i32, i32, i1) @"llvm.memset.p0i8.i32"(i8* %src.4, i8 %src.7, i32 10, i32 1, i1 false)
  unreachable
}
```



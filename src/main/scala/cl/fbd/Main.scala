package cl.fbd

import scalanative.native._
import scalanative.runtime.GC
import scalanative.libc.stdlib._

@extern
object Intrinsics { 
  /*
   * no return type defined
   * - compiler don't complain
   * - linker don't complain
   * - fail at runtime with segmentation fault
   */

  def `llvm.memset.p0i8.i32`(dest: Ptr[Byte],
                             value: Byte,
                             len: Int,
                             align: Int,
                             isvolatile: Boolean) /* : Unit */ = extern
}

object Main {
  def main (args : Array [String]) = {
    import scalanative.runtime
    
    val ptr = GC.malloc (100).cast[Ptr[Byte]]
      
    Intrinsics.`llvm.memset.p0i8.i32`(ptr: Ptr[Byte], 'A'.toByte, 10, 1, false)
    
    fprintf(stdout, c"%s\n", ptr)
    
    ()
  }
}

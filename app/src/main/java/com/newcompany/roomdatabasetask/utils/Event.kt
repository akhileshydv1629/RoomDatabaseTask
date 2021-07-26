package com.newcompany.roomdatabasetask.utils

 open class Event<out T>(private val content: T) {

    var hasbeenHandled =false
    private set// allows external read but not write


     /*
     * returns its content but prevents its use again
     * */

     fun getContentIfNotHandled():T?
     {
        return  if(hasbeenHandled)
         {
              null
         }
         else
         {
             hasbeenHandled = true
              content
         }
     }

   /*
   * returns content even if it is handled
   * */

     fun peakContent():T{
         return  content
     }

}
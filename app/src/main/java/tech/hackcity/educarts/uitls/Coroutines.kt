package tech.hackcity.educarts.uitls

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *Created by Victor Loveday on 4/27/23
 */
//object Coroutines {
//
//    fun main(work: suspend (() -> Unit)) =
//        CoroutineScope(Dispatchers.Main).launch {
//            work()
//        }
//
//    fun io(work: suspend (() -> Unit)) =
//        CoroutineScope(Dispatchers.IO).launch {
//            work()
//        }
//
//}

object Coroutines {
    fun onMain(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun onIO(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun onMainWithScope(coroutineScope: CoroutineScope, work: suspend (() -> Unit)) =
        coroutineScope.launch(Dispatchers.Main) {
            work()
        }

    fun onIOWithScope(coroutineScope: CoroutineScope, work: suspend (() -> Unit)) =
        coroutineScope.launch(Dispatchers.IO) {
            work()
        }
}

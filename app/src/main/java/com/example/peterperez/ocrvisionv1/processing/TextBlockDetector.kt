package com.example.peterperez.ocrvisionv1.processing


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.peterperez.ocrvisionv1.MainActivity
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock

class TextBlockDetector: Detector.Processor<TextBlock> {

    private val mUIHandler: Handler = Handler(Looper.getMainLooper())
    private var mListener: TextProcessingListener

    constructor(listener: MainActivity) {
        mListener = listener
    }

    override fun receiveDetections(p0: Detector.Detections<TextBlock>?) {
        val items = p0?.detectedItems
        var result = ""

        items?.let {
            for (i in 0..(items.size() - 1)) {
                val item = items.valueAt(i)
                result += item.value
                result += "\n"
            }
        }

        mUIHandler.post({ mListener.onTextRecognized(result) })
    }

    override fun release() {
        Log.d("MainActivity", "not available")
    }
}
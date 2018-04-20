package com.example.peterperez.ocrvisionv1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import com.example.peterperez.ocrvisionv1.camera.SurfaceHolderCallback
import com.example.peterperez.ocrvisionv1.processing.TextBlockDetector
import com.example.peterperez.ocrvisionv1.processing.TextProcessingListener
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TextProcessingListener {

    lateinit var cameraView: SurfaceView
    lateinit var textView: TextView
    lateinit var cameraSource:CameraSource
    private lateinit var textRecognizer: TextRecognizer
    lateinit var holdtxt:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraView=the_image
        textView=read_out

        var btn_hold =btn_hold
        btn_hold.setOnClickListener{
            holdtxt=heldtxt
            holdtxt.text=textView.text
        }

        this.loadCamera()


    }

      fun OnDestroy(){
        super.onDestroy()
        cameraSource?.release()
    }

    private fun loadCamera() {
        textRecognizer = TextRecognizer.Builder(applicationContext).build()
        textRecognizer.setProcessor(TextBlockDetector(this))

        if (textRecognizer.isOperational) {
            cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(4.0f)
                    .build()

            cameraView.holder.addCallback(SurfaceHolderCallback(cameraSource))
        } else {
            Log.d("MainActivity", "not available")
        }
    }

    override fun onTextRecognized(text: String?) {
        if (text.isNullOrEmpty()) {
            textView.setText("no_text")
        } else {
            textView.text = text
        }
    }
}
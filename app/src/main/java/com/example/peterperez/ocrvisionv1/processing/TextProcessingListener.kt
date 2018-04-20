package com.example.peterperez.ocrvisionv1.processing

interface TextProcessingListener {

    fun onTextRecognized(text: String?)
}
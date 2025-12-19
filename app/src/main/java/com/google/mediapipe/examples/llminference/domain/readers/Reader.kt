package com.google.mediapipe.examples.llminference.domain.readers

import java.io.InputStream

abstract class Reader {

    abstract fun readFromInputStream(inputStream: InputStream): String?
}

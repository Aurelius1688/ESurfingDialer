package com.rsplwe.esurfing.hook

import com.github.unidbg.AndroidEmulator
import com.github.unidbg.linux.android.dvm.DvmClass
import com.github.unidbg.linux.android.dvm.DvmObject
import com.rsplwe.esurfing.States
import org.apache.log4j.Logger
import java.util.*

class Session(zsm: ByteArray) {

    private val logger: Logger = Logger.getLogger(Session::class.java)
    private val mock: AndroidMock
    private val emulator: AndroidEmulator
    private val method: DvmClass
    private val sessionId: Long
    private val clientId: String

    init {
        logger.info("Initializing Session...")
        mock = AndroidMock()
        emulator = mock.getEmulator()
        method = mock.getJniMethod()
        sessionId = this.load(zsm)
        clientId = UUID.randomUUID().toString().lowercase()
        States.algoId = this.getAlgoId()
    }

    private fun load(zsm: ByteArray): Long {
        return method.callStaticJniMethodLong(emulator, "load([B)J", zsm)
    }

    fun decrypt(hex: String): String {
        val r: DvmObject<*> =
            method.callStaticJniMethodObject(emulator, "dec(J[B)[B", sessionId, hex.toByteArray(Charsets.UTF_8))
        return String((r.value as ByteArray))
    }

    fun getAlgoId(): String {
        val r: DvmObject<*> = method.callStaticJniMethodObject(emulator, "aid(J)Ljava/lang/String;", sessionId)
        return r.value as String
    }

    fun getSessionId(): Long {
        return this.sessionId
    }

    fun getKey(): String {
        val r: DvmObject<*> = method.callStaticJniMethodObject(emulator, "key(J)Ljava/lang/String;", sessionId)
        return r.value as String
    }

    fun encrypt(hex: String): String {
        val r: DvmObject<*> =
            method.callStaticJniMethodObject(emulator, "enc(J[B)[B", sessionId, hex.toByteArray(Charsets.UTF_8))
        return String((r.value as ByteArray))
    }

    fun free() {
        method.callStaticJniMethodObject<DvmObject<*>>(emulator, "free(J)V", sessionId)
        emulator.close()
    }
}
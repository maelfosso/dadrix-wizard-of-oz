package com.stockinos.mobile.wizardofoz.dto

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

abstract class MessageDTO (
    open var id: String,
    open var from: String,
    open var to: String? = null,
    open var timestamp: String,
    open var type: String,
) {
    var state: String? = "unknown"
        get() = field ?: "unknown"

    var iTimestamp: Long = 0 // timestamp.toLong()

    // var mTimestamp: Date = Date(timestamp.toLong())


    abstract fun summary(): String
}

data class MessageTextDTO(
    override var id: String,
    override var from: String,
    override var to: String?,
    override var timestamp: String,
    override var type: String,

    var textId: String,
    var body: String
) : MessageDTO (id, from, to, timestamp, type) {
    // constructor(): this("", "", "", "", "", "", "")

    override fun summary(): String {
        return body;
    }
}

data class MessageImageDTO (
    override var id: String,
    override var from: String,
    override var to: String?,
    override var timestamp: String,
    override var type: String,

    var imageId: String,
    var caption   : String?,
    var mimeType  : String?,
    var sha256    : String?,
) : MessageDTO (id, from, to, timestamp, type) {
    constructor(): this(
        "", "", "", "", "", "", "", "", ""
    )

    override fun summary(): String {
        TODO("Not yet implemented")
    }
}

data class MessageAudioDTO (
    override var id: String,
    override var from: String,
    override var to: String?,
    override var timestamp: String,
    override var type: String,

    var audioId: String,
    var mimeType  : String?,
    var sha256    : String?,
    var voice     : Boolean?
) : MessageDTO (id, from, to, timestamp, type) {
    constructor(): this(
        "", "", "", "", "", "",
        "", "", false
    )

    override fun summary(): String {
        TODO("Not yet implemented")
    }
}

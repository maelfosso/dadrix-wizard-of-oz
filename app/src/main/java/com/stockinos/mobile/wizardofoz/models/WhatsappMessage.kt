package com.stockinos.mobile.wizardofoz.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "whatsapp_messages")
data class WhatsappMessage (

    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("text_id", alternate = ["textid", "textId"])
    val textId: String? = null,
    @SerializedName("text")
    @Embedded val text: WhatsappMessageText? = null,

    @SerializedName("image_id")
    val imageId: String? = null,
    @SerializedName("image")
    @Embedded val image: WhatsappMessageImage? = null,

    @SerializedName("audio_id")
    val audioId: String? = null,
    @SerializedName("audio")
    @Embedded val audio: WhatsappMessageAudio? = null,
) {
    var state: String? = "unknown"
        get() = field ?: "unknown"

    @Ignore
    var receivedDate: Date = Date(timestamp.toLong())
}

@Entity(tableName = "whatsapp_messages_texts")
data class WhatsappMessageText (
    @PrimaryKey
    @ColumnInfo(name = "text_id")
    @SerializedName("id")
    val id: String,
    @SerializedName("body")
    val body: String
)

@Entity(tableName = "whatsapp_messages_images")
data class WhatsappMessageImage (
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    @SerializedName("id")
    val id: String,
    @SerializedName("caption")
    val caption   : String?,

    @ColumnInfo(name = "image_mime_type")
    @SerializedName("mime_type")
    val mimeType  : String?,

    @ColumnInfo(name = "image_sha_256")
    @SerializedName("sha256")
    val sha256    : String?,

)

@Entity(tableName = "whatsapp_messages_audios")
data class WhatsappMessageAudio (
    @PrimaryKey
    @ColumnInfo(name = "audio_id")
    @SerializedName("id")
    val audioId: String,

    @ColumnInfo(name = "audio_mime_type")
    @SerializedName("mime_type")
    val mimeType  : String?,

    @ColumnInfo(name = "audio_sha_256")
    @SerializedName("sha256")
    val sha256    : String?,
    @SerializedName("voice")
    val voice     : Boolean?
)

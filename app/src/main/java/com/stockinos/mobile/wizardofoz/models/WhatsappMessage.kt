package com.stockinos.mobile.wizardofoz.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "whatsapp_messages")
data class WhatsappMessage (

    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("from")
    val from: String,
    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("text_id")
    val textId: String?,
    @SerializedName("text")
    @Embedded val text: WhatsappMessageText?,

    @SerializedName("image_id")
    val imageId: String?,
    @SerializedName("image")
    @Embedded val image: WhatsappMessageImage?,

    @SerializedName("audio_id")
    val audioId: String?,
    @SerializedName("audio")
    @Embedded val audio: WhatsappMessageAudio?
)

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
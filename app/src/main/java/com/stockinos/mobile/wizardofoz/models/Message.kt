package com.stockinos.mobile.wizardofoz.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "messages")
data class Message (

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    // @Embedded(prefix = "from_")
    @SerializedName("from")
    val from: String, // User,

    // @Embedded(prefix = "to_")
    @SerializedName("to")
    // @ColumnInfo(defaultValue = "+000")
    val to: String? = null,

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("text_id", alternate = ["textid", "textId"])
    val textId: String? = null,
    @SerializedName("text")
    @Embedded val text: MessageText? = null,

    @SerializedName("image_id")
    val imageId: String? = null,
    @SerializedName("image")
    @Embedded val image: MessageImage? = null,

    @SerializedName("audio_id")
    val audioId: String? = null,
    @SerializedName("audio")
    @Embedded val audio: MessageAudio? = null,
) {
    var state: String? = "unknown"
        get() = field ?: "unknown"

    @Ignore
    var iTimestamp: Long = timestamp.toLong()

    @Ignore
    var mTimestamp: Date = Date(timestamp.toLong())
}

@Entity(tableName = "messages_texts")
data class MessageText (
    @PrimaryKey
    @ColumnInfo(name = "text_id")
    @SerializedName("id")
    val id: String,
    @SerializedName("body")
    val body: String
)

@Entity(tableName = "messages_images")
data class MessageImage (
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

@Entity(tableName = "messages_audios")
data class MessageAudio (
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


data class MessageWithUser (
    @Embedded val message: Message,

    @Relation(
        parentColumn = "from",
        entityColumn = "phone_number"
    )
    val from: User,

    @Relation(
        parentColumn = "to",
        entityColumn = "phone_number"
    )
    val to: User,
)
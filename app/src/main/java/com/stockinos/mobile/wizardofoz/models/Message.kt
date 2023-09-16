package com.stockinos.mobile.wizardofoz.models

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

const val MESSAGE_TYPE_TEXT = "text"
const val MESSAGE_TYPE_IMAGE = "image"
const val MESSAGE_TYPE_AUDIO = "audio"

@Entity(tableName = "messages")
open class Message (

    @PrimaryKey
    @SerializedName("id")
    open var id: String,

    @SerializedName("from")
    open var from: String, // User,

    @SerializedName("to")
    open var to: String? = null,

    @SerializedName("timestamp")
    open var timestamp: String,

    @SerializedName("type")
    open var type: String,
)

@Entity(
    tableName = "messages_texts",
    foreignKeys = [
        ForeignKey(
            entity = Message::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageText(
    @ColumnInfo(name = "id", index = true)
    var messageId: String,

    @PrimaryKey
    @ColumnInfo(name = "text_id")
    @SerializedName("text_id", alternate = ["id", "textid", "textId"])
    var textId: String,

    @SerializedName("body")
    var body: String
)

@Entity(
    tableName = "messages_images",
    foreignKeys = [
        ForeignKey(
            entity = Message::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageImage (
    @ColumnInfo(name = "id", index = true)
    var messageId: String,

    @PrimaryKey
    @ColumnInfo(name = "image_id")
    @SerializedName("id")
    var imageId: String,
    @SerializedName("caption")
    var caption   : String?,

    @ColumnInfo(name = "image_mime_type")
    @SerializedName("mime_type")
    var mimeType  : String?,

    @ColumnInfo(name = "image_sha_256")
    @SerializedName("sha256")
    var sha256    : String?,
)

@Entity(
    tableName = "messages_audios",
    foreignKeys = [
        ForeignKey(
            entity = Message::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageAudio (
    @ColumnInfo(name = "id")
    var messageId: String,

    @PrimaryKey
    @ColumnInfo(name = "audio_id")
    @SerializedName("audio_id", alternate = ["id", "audioid", "audioId"])
    var audioId: String,

    @ColumnInfo(name = "audio_mime_type")
    @SerializedName("mime_type")
    var mimeType  : String?,

    @ColumnInfo(name = "audio_sha_256")
    @SerializedName("sha256")
    var sha256    : String?,
    @SerializedName("voice")
    var voice     : Boolean?
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

data class MessageWithText (
    @Embedded val message: Message,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val text: MessageText
)

data class MessageWithImage (
    @Embedded val message: Message,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val image: MessageImage
)

data class MessageWithAudio (
    @Embedded val message: Message,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val audio: MessageAudio
)
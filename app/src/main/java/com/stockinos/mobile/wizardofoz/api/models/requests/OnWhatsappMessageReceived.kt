package com.stockinos.mobile.wizardofoz.api.models.requests

import com.google.gson.annotations.SerializedName
import com.stockinos.mobile.wizardofoz.models.*

data class OnWhatsappMessageReceived(
    @SerializedName("id")
    val id: String,

    @SerializedName("from")
    val from: User,

    @SerializedName("to")
    val to: User,

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("text_id", alternate = ["textid", "textId"])
    val textId: String,
    @SerializedName("text")
    val text: MessageText,

    @SerializedName("image_id")
    val imageId: String? = null,
    @SerializedName("image")
    val image: MessageImage? = null,

    @SerializedName("audio_id")
    val audioId: String? = null,
    @SerializedName("audio")
    val audio: MessageAudio? = null,
) {
    fun toMessage(): Message {
        return Message(
            id = this.id,
            from = this.from.phoneNumber,
            to = this.to.phoneNumber,
            timestamp = this.timestamp,
            type = this.type,

            textId = this.textId,
            text = this.text,

            imageId = this.imageId,
            image = this.image,

            audioId = this.audioId,
            audio = this.audio
        );
    }
}

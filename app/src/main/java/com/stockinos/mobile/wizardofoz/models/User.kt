package com.stockinos.mobile.wizardofoz.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

const val USER_TYPE_CUSTOMER = "customer"

@Entity(tableName = "users")
data class User(
    @SerializedName("ID", alternate = ["id"])
    val id: Int,

    @SerializedName("name")
    val name: String,

    @PrimaryKey
    @SerializedName("phone_number", alternate = ["phoneNumber"])
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String
)

data class UserWithSentMessages(
    @Embedded val user: User,

    @Relation(
        parentColumn = "phone_number",
        entityColumn = "from"
    )
    val messages: List<Message>
)

data class UserWithReceivedMessages(
    @Embedded val user: User,

    @Relation(
        parentColumn = "phone_number",
        entityColumn = "to"
    )
    val messages: List<Message>
)

data class UserWithMessages(
    @Embedded val user: User,
    val messages: List<Message>
)
package kr.co.seonguk.application.fastcampuschatting.chatList

data class ChatRoomItem(
    val chatRoomId: String,
    val otherUserName: String,
    val lastMessage: String
)
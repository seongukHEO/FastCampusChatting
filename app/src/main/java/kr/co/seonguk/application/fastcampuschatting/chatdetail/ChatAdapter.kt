package kr.co.seonguk.application.fastcampuschatting.chatdetail

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.seonguk.application.fastcampuschatting.chatList.ChatRoomItem
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemChatBinding
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemChatroomBinding
import kr.co.seonguk.application.fastcampuschatting.userList.UserItem

class ChatAdapter() : ListAdapter<ChatItem, ChatAdapter.ViewHolder>(differ) {

    var otherUserItem: UserItem? = null

    inner class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ChatItem){
            if (item.userId == otherUserItem?.userId){
                //상대방이 보냈을 때
                binding.userNameTextView.isVisible = true
                binding.userNameTextView.text = otherUserItem?.userName
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.START
            }else{
                //내가 보냈을 때
                binding.userNameTextView.isVisible = false
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.END
            }


        }
    }

    companion object{
        val differ = object : DiffUtil.ItemCallback<ChatItem>(){
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem.chatId == newItem.chatId
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
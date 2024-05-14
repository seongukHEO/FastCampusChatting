package kr.co.seonguk.application.fastcampuschatting.chatList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemChatroomBinding
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemUserBinding

class ChatListAdapter : ListAdapter<ChatRoomItem, ChatListAdapter.ViewHolder>(differ) {

    inner class ViewHolder(val binding: ItemChatroomBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ChatRoomItem){
            binding.nicknameTextView.text = item.otherUserName
            binding.lastMessageTextView.text = item.lastMessage
        }
    }

    companion object{
        val differ = object : DiffUtil.ItemCallback<ChatRoomItem>(){
            override fun areItemsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem.chatRoomId == newItem.chatRoomId
            }

            override fun areContentsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatroomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
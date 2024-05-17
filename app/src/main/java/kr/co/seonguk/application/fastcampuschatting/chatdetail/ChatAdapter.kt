package kr.co.seonguk.application.fastcampuschatting.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemChatBinding
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemChatroomBinding

class ChatAdapter : ListAdapter<ChatItem, ChatAdapter.ViewHolder>(differ) {

    inner class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ChatItem){
            binding.messageTextView.text = item.message
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
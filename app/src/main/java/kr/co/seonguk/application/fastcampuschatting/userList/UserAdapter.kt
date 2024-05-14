package kr.co.seonguk.application.fastcampuschatting.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.seonguk.application.fastcampuschatting.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserItem, UserAdapter.ViewHolder>(differ) {

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: UserItem){
            binding.nicknameTextView.text = item.userName
            binding.descriptionTextView.text = item.description
        }
    }

    companion object{
        val differ = object : DiffUtil.ItemCallback<UserItem>(){
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
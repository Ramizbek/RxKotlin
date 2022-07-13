package ramizbek.aliyev.rxkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ramizbek.aliyev.rxkotlin.database.User
import ramizbek.aliyev.rxkotlin.databinding.ItemRvBinding

class UserAdapter(var onItemCLickListener: OnItemCLickListener):ListAdapter<User, UserAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(var itemRv: ItemRvBinding):RecyclerView.ViewHolder(itemRv.root){

        fun onBind(user: User){
            itemRv.tvLocation.text = user.id.toString()
            itemRv.tvName.text = user.userName
            itemRv.tvCode.text = user.password

            itemRv.root.setOnClickListener {
                onItemCLickListener.onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil:DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface OnItemCLickListener{
        fun onItemClick(user: User)
    }

}
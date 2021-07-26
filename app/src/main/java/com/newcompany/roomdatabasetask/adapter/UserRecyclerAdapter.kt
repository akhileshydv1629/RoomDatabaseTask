package com.newcompany.roomdatabasetask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newcompany.roomdatabasetask.R
import com.newcompany.roomdatabasetask.databinding.ListUserItemsBinding
import com.newcompany.roomdatabasetask.db.User

class UserRecyclerAdapter(private val clickListener: (User) -> Unit, private val clickListener2: (User) -> Unit):
    RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {
    private  val  userList= ArrayList<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding:ListUserItemsBinding= DataBindingUtil.inflate(layoutInflater, R.layout.list_user_items, parent, false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], clickListener, clickListener2)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setList(userlist: List<User>)
    {
          userList.clear()
          userList.addAll(userlist)
    }

    class UserViewHolder(val binding: ListUserItemsBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(user: User, clickListener: (User) -> Unit, clickListener2: (User) -> Unit)
        {
            binding.tvName.text=user.user_name
            binding.tvMobile.text=user.user_mobile
            binding.tvBook.text=user.user_book

            binding.itemCLickListener.setOnClickListener {
                clickListener(user)
            }
            binding.btnDelete.setOnClickListener {
                clickListener2(user)
            }

        }

    }



}
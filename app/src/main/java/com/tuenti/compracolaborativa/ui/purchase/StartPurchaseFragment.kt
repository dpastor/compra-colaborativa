package com.tuenti.compracolaborativa.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuenti.compracolaborativa.R
import com.tuenti.compracolaborativa.data.model.LoggedInUser

class StartPurchaseFragment : Fragment() {

    private lateinit var startPurchaseViewModel: StartPurchaseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        startPurchaseViewModel =
                ViewModelProviders.of(this).get(StartPurchaseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_start_purchase, container, false)
        val list: RecyclerView = root.findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(context);
        list.adapter = UserListAdapter(listOf(
            LoggedInUser("1", "Pepito"),
            LoggedInUser("2", "Juanito"),
            LoggedInUser("3", "Alfonsito"),
            LoggedInUser("4", "Pepito"),
            LoggedInUser("5", "Juanito"),
            LoggedInUser("6", "Alfonsito"),
            LoggedInUser("7", "Pepito"),
            LoggedInUser("8", "Juanito"),
            LoggedInUser("9", "Alfonsito"),
            LoggedInUser("10", "Pepito"),
            LoggedInUser("11", "Juanito"),
            LoggedInUser("12", "Alfonsito")
        ))
        return root
    }
}

private class UserListAdapter(
    private val userList: List<LoggedInUser>
): RecyclerView.Adapter<UserListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListViewHolder {
        return UserListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.user_requested_lists_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.title).text = userList[position].displayName
    }

    override fun getItemCount() = userList.size
}

private class UserListViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view)
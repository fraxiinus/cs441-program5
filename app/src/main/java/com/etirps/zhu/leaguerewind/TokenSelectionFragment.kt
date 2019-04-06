package com.etirps.zhu.leaguerewind

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class TokenSelectionFragment: Fragment() {

    companion object {
        fun newInstance(title: String, names: Array<String>): TokenSelectionFragment {
            val fragment = TokenSelectionFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putStringArray("entryNames", names)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        var names = arguments?.getStringArray("entryNames")
        var title = arguments?.getString("title")

        if(names == null) {
            names = arrayOf("")
        }

        if(title == null) {
            title = "Error"
        }

        // Create content array
        val content = ArrayList<HashMap<String, String>>()
        // Each element is a map of name, to value
        for (name in names) {
            val hm = HashMap<String, String>()
            hm["list_name"] = name
            // Get the id (int) of the resource
            hm["list_img"] = activity?.resIdByName(name.clean(), "drawable").toString()
            content.add(hm)
        }

        // Key to tell adapter how to use content
        // list name goes to R.id.tv1
        // list img goes to R.id.iv1
        val fromMap = arrayOf("list_name", "list_img")
        val toMap = arrayOf(R.id.tv1, R.id.iv1)

        // Inflate the layout
        val layout = inflater.inflate(R.layout.fragment_tokens, container, false)

        // Set the title text
        layout.findViewById<TextView>(R.id.tooltitle).text = title

        // Get the list view from the layout
        val list = layout.findViewById<ListView>(R.id.token_selection_listview)

        // Setup the adapter and give it to the list
        val arrayAdapter = SimpleAdapter(layout.context, content, R.layout.select_item_layout, fromMap, toMap.toIntArray())
        list.adapter = arrayAdapter

        // Set up the click listener
        list.setOnItemClickListener { parent: AdapterView<*>, view1: View, position: Int, id: Long ->
            val appData = activity?.application as ApplicationData
            appData.drawMode = DrawingModes.TOKEN
            appData.tokenIndex = position
            appData.isEnemy = (view?.findViewById<Switch>(R.id.enemy_switch)?.isChecked) ?: false
            appData.isWard = title.startsWith("Ward")

            Toast.makeText(layout.context, "Tap to place token", Toast.LENGTH_SHORT).show()
        }

        // Set up listener for switch
        val switch = layout.findViewById<Switch>(R.id.enemy_switch)
        switch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            (activity?.application as ApplicationData).isEnemy = b
        }

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
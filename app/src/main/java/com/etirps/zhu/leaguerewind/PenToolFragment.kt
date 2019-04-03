package com.etirps.zhu.leaguerewind

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PenToolFragment: Fragment() {

    companion object {
        fun newInstance(title: String): PenToolFragment {
            val fragment = PenToolFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        var title = arguments?.getString("title")

        if(title == null) {
            title = "Error"
        }

        // Inflate the layout
        val layout = inflater.inflate(R.layout.fragment_pen, container, false)

        // Set the title text
        layout.findViewById<TextView>(R.id.pentitle).text = title

        return layout
    }
}
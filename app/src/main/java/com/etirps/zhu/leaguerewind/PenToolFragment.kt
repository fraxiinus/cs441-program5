package com.etirps.zhu.leaguerewind

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class PenToolFragment: Fragment(), SeekBar.OnSeekBarChangeListener {

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

        // set initial positions
        val colorInt = (activity?.application as ApplicationData).colorInt
        val strokeSize = (activity?.application as ApplicationData).strokeSize
        layout.findViewById<SeekBar>(R.id.sb_redvalue).progress = ((Color.red(colorInt) / 255f) * 100f).toInt()
        layout.findViewById<SeekBar>(R.id.sb_greenvalue).progress = ((Color.green(colorInt) / 255f) * 100f).toInt()
        layout.findViewById<SeekBar>(R.id.sb_bluevalue).progress = ((Color.blue(colorInt) / 255f) * 100f).toInt()
        layout.findViewById<SeekBar>(R.id.sb_sizevalue).progress = strokeSize * 4

        // Set initial text
        layout.findViewById<TextView>(R.id.tv_redvalue).text = Color.red(colorInt).toString()
        layout.findViewById<TextView>(R.id.tv_greenvalue).text = Color.green(colorInt).toString()
        layout.findViewById<TextView>(R.id.tv_bluevalue).text = Color.blue(colorInt).toString()
        layout.findViewById<TextView>(R.id.tv_sizevalue).text = (strokeSize).toString()

        // set color listeners
        layout.findViewById<SeekBar>(R.id.sb_redvalue).setOnSeekBarChangeListener(this)
        layout.findViewById<SeekBar>(R.id.sb_greenvalue).setOnSeekBarChangeListener(this)
        layout.findViewById<SeekBar>(R.id.sb_bluevalue).setOnSeekBarChangeListener(this)
        layout.findViewById<SeekBar>(R.id.sb_sizevalue).setOnSeekBarChangeListener(this)

        return layout
    }

    override fun onStop() {
        val colorInt = (activity?.application as ApplicationData).colorInt

        Toast.makeText(view?.context, "Stop: $colorInt", Toast.LENGTH_SHORT).show()

        super.onStop()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(seekBar?.id != R.id.sb_sizevalue) {
            view?.findViewById<TextView>(R.id.tv_previewcolor)?.setBackgroundColor(getColor())
            when(seekBar?.id) {
                R.id.sb_redvalue -> view?.findViewById<TextView>(R.id.tv_redvalue)?.text = Color.red(getColor()).toString()
                R.id.sb_greenvalue -> view?.findViewById<TextView>(R.id.tv_greenvalue)?.text = Color.green(getColor()).toString()
                R.id.sb_bluevalue -> view?.findViewById<TextView>(R.id.tv_bluevalue)?.text = Color.blue(getColor()).toString()
            }
        } else if(seekBar.id == R.id.sb_sizevalue) {
            view?.findViewById<TextView>(R.id.tv_sizevalue)?.text = getSize().toString()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // Do nothing
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        (activity?.application as ApplicationData).colorInt = getColor()
        (activity?.application as ApplicationData).strokeSize = getSize()

        Toast.makeText(view?.context, "TouchUp: ${Color.red(getColor())}", Toast.LENGTH_SHORT).show()

    }

    private fun getSize(): Int {
        val size =  view?.findViewById<SeekBar>(R.id.sb_sizevalue)?.progress ?: 0
        return size / 4
    }

    private fun getRed(): Int {
        return view?.findViewById<SeekBar>(R.id.sb_redvalue)?.progress ?: 0
    }

    private fun getGreen(): Int {
        return view?.findViewById<SeekBar>(R.id.sb_greenvalue)?.progress ?: 0
    }

    private fun getBlue(): Int {
        return view?.findViewById<SeekBar>(R.id.sb_bluevalue)?.progress ?: 0
    }

    private fun getColor(): Int {
        return Color.rgb(getRed() / 100f, getGreen() / 100f, getBlue() / 100f)
    }
}
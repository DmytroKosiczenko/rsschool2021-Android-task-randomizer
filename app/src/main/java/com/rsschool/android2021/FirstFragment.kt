package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private var minEdit: EditText? = null
    private var maxEdit: EditText? = null

    private var listener: GenerateButtonClickedListenerInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as GenerateButtonClickedListenerInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minEdit = view.findViewById(R.id.min_value)
        maxEdit = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            var min = Integer.valueOf(minEdit?.text.toString())
            var max = Integer.valueOf(maxEdit?.text.toString())

            if(min<max) {
                listener?.onGenerateButtonClicked(min, max)
            } else {
              Toast.makeText(context, "Minimal value should be less then Maximal", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface GenerateButtonClickedListenerInterface {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}
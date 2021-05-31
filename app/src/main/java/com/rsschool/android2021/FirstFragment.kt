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
            var minStr = minEdit?.text.toString()
            var maxStr = maxEdit?.text.toString()

            if(validateInputValues(minStr, maxStr)) {
                var min = Integer.valueOf(minStr)
                var max = Integer.valueOf(maxStr)
                listener?.onGenerateButtonClicked(min, max)
            }
        }
    }

    fun validateInputValues(minStr: String, maxStr:String) :Boolean {
        var max = 0;
        var min = 0;

        if(minStr.length == 0) {
            Toast.makeText(context, "Minimal value is empty", Toast.LENGTH_LONG).show()
            return false;
        }

        if(maxStr.length == 0) {
            Toast.makeText(context, "Maximal value is empty", Toast.LENGTH_LONG).show()
            return false;
        }

        try {
            min = Integer.valueOf(minStr)
        } catch (e: Exception){
            Toast.makeText(context, "Incorrect MIN value", Toast.LENGTH_LONG).show()
            return false;
        }
        try {
            max = Integer.valueOf(maxStr)
        } catch (e: Exception){
            Toast.makeText(context, "Incorrect MAX value", Toast.LENGTH_LONG).show()
            return false;
        }

        if(min>=max) {
            Toast.makeText(context, "Minimal value should be less then Maximal", Toast.LENGTH_LONG).show()
            return false;
        }

        return true;
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
package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.random.Random

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var listener: BackKeyPressedInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as BackKeyPressedInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            listener?.onBackKeyPressed(result?.text.toString().toInt())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        arguments?.let {
            val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
            val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
            result?.text = generate(min, max).toString()
        }

        backButton?.setOnClickListener {
            listener?.onBackKeyPressed(result?.text.toString().toInt())
        }
    }

    private fun generate(from: Int, until: Int): Int {
        return Random.nextInt(from, until)
    }

    interface BackKeyPressedInterface {
        fun onBackKeyPressed(previous : Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            fragment.apply {
                arguments = bundleOf(
                    MIN_VALUE_KEY to min,
                    MAX_VALUE_KEY to max
                )
            }
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}
package jp.neechan.akari.fragment_callback_argument

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import jp.neechan.akari.fragment_callback_argument.databinding.FragmentMainBinding

/**
 * Этот фрагмент избавлен от проблем при повороте экрана. Мы передаем все параметры через
 * setArguments(), этот Bundle переживает все пересоздания фрагмента при повороте экрана.
 * И стейт всегда возвращается из аргументов Bundle.
 */
class SafeFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private var text = "Safe fragment default text"
    private var callback: (() -> Unit)? = null

    init {
        Log.d(TAG, "init: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        Log.d(TAG, "onViewCreated: ")

        with(requireArguments()) {
            text = getString(ARGUMENT_TEXT, "")

            // Kotlin lambdas are Serializable: https://discuss.kotlinlang.org/t/are-closures-serializable/1620
            @Suppress("UNCHECKED_CAST")
            callback = getSerializable(ARGUMENT_CALLBACK) as? () -> Unit
        }

        textView.text = text
        button.setOnClickListener {
            callback?.invoke() ?: Toast.makeText(
                requireContext(),
                "Callback is null",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val TAG = "SafeFragment"

        private const val ARGUMENT_TEXT = "ARGUMENT_TEXT"
        private const val ARGUMENT_CALLBACK = "ARGUMENT_CALLBACK"

        fun newInstance(text: String, callback: () -> Unit): SafeFragment {
            Log.d(TAG, "newInstance: ")

            return SafeFragment().apply {
                this.arguments = bundleOf(
                    ARGUMENT_TEXT to text,
                    ARGUMENT_CALLBACK to callback
                )
            }
        }
    }
}

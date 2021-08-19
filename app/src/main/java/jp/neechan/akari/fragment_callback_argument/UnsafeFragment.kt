package jp.neechan.akari.fragment_callback_argument

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import jp.neechan.akari.fragment_callback_argument.databinding.FragmentMainBinding

/**
 * Проблема этого фрагмента в том, что при повороте экрана он пересоздается, и значения полей
 * сбрасываются. Т.к. поля инициализируются через сеттеры в newInstance(), их значения не
 * сохраняются, ведь ОС пересоздает фрагмент через дефолтный конструктор, а не через newInstance().
 */
class UnsafeFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private var text = "Unsafe fragment default text"
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
        private const val TAG = "UnsafeFragment"

        fun newInstance(text: String, callback: () -> Unit): UnsafeFragment {
            Log.d(TAG, "newInstance: ")
            return UnsafeFragment().apply {
                this.text = text
                this.callback = callback
            }
        }
    }
}

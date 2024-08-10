package com.bookmoa.android.group

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityMakeBinding
import com.bookmoa.bookmoa.GroupActivity

class MakeActivity : AppCompatActivity() {
    private val binding: ActivityMakeBinding by lazy { ActivityMakeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        setupKeyboardVisibilityListener()
    }

    private fun setupUI() {
        setupPasswordEditText()
        setupIntroduceEditText()
        setupNoticeEditText()
        setupInitialVisibility()
    }

    private fun setupListeners() {
        setupBackButtonListener()
        setupSwitchListener()
    }

    private fun setupBackButtonListener() {
        binding.makeBackIv.setOnClickListener {
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupKeyboardVisibilityListener() {
        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private var lastHeight = rootView.height

            override fun onGlobalLayout() {
                val newHeight = rootView.height
                if (newHeight < lastHeight) {
                    binding.makeBottomnav.visibility = View.GONE
                } else if (newHeight > lastHeight) {
                    binding.makeBottomnav.visibility = View.VISIBLE
                }
                lastHeight = newHeight
            }
        })
    }

    private fun setupInitialVisibility() {
        binding.makeLockIv.visibility = View.VISIBLE
        binding.makePerpendicularIv.visibility = View.GONE
        binding.makePasswordTv.visibility = View.GONE
        binding.makePasswordEt.visibility = View.GONE
        binding.makePasswordhintTv.visibility = View.GONE
    }

    private fun setupSwitchListener() {
        binding.makeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateVisibilityBasedOnSwitch(isChecked)
        }
    }

    private fun updateVisibilityBasedOnSwitch(isChecked: Boolean) {
        if (isChecked) {
            binding.makeLockIv.visibility = View.GONE
            binding.makePerpendicularIv.visibility = View.VISIBLE
            binding.makePasswordTv.visibility = View.VISIBLE
            binding.makePasswordEt.visibility = View.VISIBLE
            binding.makePasswordhintTv.visibility = View.VISIBLE
        } else {
            binding.makeLockIv.visibility = View.VISIBLE
            binding.makePerpendicularIv.visibility = View.GONE
            binding.makePasswordTv.visibility = View.GONE
            binding.makePasswordEt.visibility = View.GONE
            binding.makePasswordhintTv.visibility = View.GONE
        }
    }

    private fun setupPasswordEditText() {
        binding.makePasswordEt.filters = arrayOf(InputFilter.LengthFilter(6))

        binding.makePasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val filtered = it.toString().filter { char ->
                        char.isLetterOrDigit()
                    }

                    if (filtered != it.toString()) {
                        binding.makePasswordEt.setText(filtered)
                        binding.makePasswordEt.setSelection(filtered.length)
                    }
                }
            }
        })
    }

    private fun setupIntroduceEditText() {
        binding.makeIntroduceEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.makeIntroduceTv.text = "$length / 20"

                if (length > 20) {
                    binding.makeIntroduceEt.setText(s?.subSequence(0, 20))
                    binding.makeIntroduceEt.setSelection(20)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupNoticeEditText() {
        binding.makeNoticeEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                binding.makeNoticeTv.text = "$length / 200"

                if (length > 200) {
                    binding.makeNoticeEt.setText(s?.subSequence(0, 200))
                    binding.makeNoticeEt.setSelection(200)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}

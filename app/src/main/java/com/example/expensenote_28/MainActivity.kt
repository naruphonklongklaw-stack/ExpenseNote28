package com.example.expensenote_28

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etAmount = findViewById(R.id.etAmount)
        etNote = findViewById(R.id.etDescription)
        btnAdd = findViewById(R.id.btnAdd)
        btnClear = findViewById(R.id.btnClear)
        tvLastItem = findViewById(R.id.tvLatest)
        tvTotal = findViewById(R.id.tvTotal)

        // ปุ่มเพิ่มรายการ
        btnAdd.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงินให้ถูกต้อง", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            total += amount

            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"
            
            // แสดงรายการล่าสุด และยอดรวม
            tvLastItem.text = "รายการล่าสุด: $showNote %.2f บาท".format( amount)
            tvTotal.text = "ยอดรวมวันนี้: %.2f บาท".format(total)

            // ล้างข้อมูลช่องกรอกหลังเพิ่มรายการ
            etAmount.text.clear()
            etNote.text.clear()
        }

        // ปุ่มเคลียร์ข้อมูล
        btnClear.setOnClickListener {
            total = 0.0
            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการล่าสุด: -"
            tvTotal.text = "ยอดรวมวันนี้: 0.00 บาท"
        }
    }
}

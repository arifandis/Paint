package com.example.paint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    private lateinit var btnUndo: ImageView
    private lateinit var btnRedo: ImageView
    private lateinit var btnPen: ImageView
    private lateinit var btnEraser: ImageView
    private lateinit var paintLayout: DrawPath
    private lateinit var cvSize: CardView
    private lateinit var cvColor: CardView
    private lateinit var btnSize10: CardView
    private lateinit var btnSize20: CardView
    private lateinit var btnPurple: CardView
    private lateinit var btnTeal: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnUndo = findViewById(R.id.ivUndo)
        btnRedo = findViewById(R.id.ivRedo)
        paintLayout = findViewById(R.id.paintLayout)
        btnPen = findViewById(R.id.ivPen)
        btnEraser = findViewById(R.id.ivEraser)
        cvSize = findViewById(R.id.cvSize)
        cvColor = findViewById(R.id.cvColor)
        btnSize10 = findViewById(R.id.v10)
        btnSize20 = findViewById(R.id.v20)
        btnPurple = findViewById(R.id.cvPurple)
        btnTeal = findViewById(R.id.cvTeal)

        btnUndo.setOnClickListener {
            paintLayout.setUndo()
        }

        btnRedo.setOnClickListener {
            paintLayout.setRedo()
        }

        btnPen.setOnClickListener {
            paintLayout.disableEraser()
            cvSize.visibility = View.VISIBLE
            cvColor.visibility = View.VISIBLE
        }

        btnEraser.setOnClickListener {
            paintLayout.enableEraser()
            cvColor.visibility = View.GONE
        }

        btnSize10.setOnClickListener {
            paintLayout.setSize(10f)
        }

        btnSize20.setOnClickListener {
            paintLayout.setSize(20f)
        }

        btnPurple.setOnClickListener {
            paintLayout.setColor(getColor(R.color.purple_200))
        }

        btnTeal.setOnClickListener {
            paintLayout.setColor(getColor(R.color.teal_200))
        }
    }
}
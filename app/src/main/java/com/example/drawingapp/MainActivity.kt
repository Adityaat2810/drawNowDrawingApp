
package com.example.drawingapp
import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private var DrawingView:drawingView ?=null


    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value
                if (isGranted ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()
                    //perform operation
                } else {

                    if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                        Toast.makeText(
                            this@MainActivity,
                            "Oops you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DrawingView=findViewById(R.id.drawing_view)

        DrawingView?.setSizeForBrush(20.toFloat())

        val ib_brush:ImageButton=findViewById(R.id.ib_brush)
        ib_brush.setOnClickListener{
            showBrushSizeChooserDialog()
        }
        val undo_btn:ImageButton=findViewById(R.id.undo_btn)
        undo_btn.setOnClickListener{
            DrawingView?.onClickUndo()

        }
        val erasor:ImageButton=findViewById(R.id.imageButton)
        erasor.setOnClickListener{
         apneRangmeRangdo()

        }

        val ibGallery:ImageView=findViewById(R.id.galleryButton)
        ibGallery.setOnClickListener{

            val pickIntent=Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            openGalleryLauncher.launch(pickIntent)

            }




    }

    val openGalleryLauncher: ActivityResultLauncher<Intent>
    =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
          result ->  if (result.resultCode == RESULT_OK && result.data != null){
        //process the data
        //Todo 4 if the data is not null reference the imageView from the layout
        val imageBackground:ImageView = findViewById(R.id.iv_background)
        //Todo 5: set the imageuri received
        imageBackground.setImageURI(result.data?.data)
    }
    }

    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(  this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")
        val smallestBtn: ImageButton = brushDialog.findViewById(R.id.ib_smallest)
        smallestBtn.setOnClickListener(View.OnClickListener {
            DrawingView?.setSizeForBrush(5.toFloat())
            brushDialog.dismiss()
        })

        val smallBtn: ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener(View.OnClickListener {
            DrawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        })
        val mediumBtn: ImageButton = brushDialog.findViewById(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener(View.OnClickListener {
            DrawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        })

        val largeBtn: ImageButton = brushDialog.findViewById(R.id.ib_large_brush)
        largeBtn.setOnClickListener(View.OnClickListener {
            DrawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        })
        brushDialog.show()
    }

    private fun apneRangmeRangdo(){
        val colorDialog=Dialog(this)
        colorDialog.setContentView(R.layout.dialog_color_picker)
        colorDialog.setTitle("SELECT COLOR")

        val whiteButton: ImageButton = colorDialog.findViewById(R.id.whiteButton)
        whiteButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#FFFFFFFF")
            colorDialog.dismiss()
        })

        val redButton: ImageButton = colorDialog.findViewById(R.id.redButton)
        redButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#FF0000")
            colorDialog.dismiss()
        })
        val yellowButton: ImageButton = colorDialog.findViewById(R.id.yellowButton)
        yellowButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#FFFF00")
            colorDialog.dismiss()
        })

        val blackButton: ImageButton = colorDialog.findViewById(R.id.blackButton)
        blackButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#FF000000")
           colorDialog.dismiss()
        })

        val pinkButton: ImageButton = colorDialog.findViewById(R.id.pinkButton)
        pinkButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#FF69B4")
           colorDialog.dismiss()
        })

        val greenButton: ImageButton = colorDialog.findViewById(R.id.greenwala)
        greenButton.setOnClickListener(View.OnClickListener {
            DrawingView?.setColor("#00FF00")
           colorDialog.dismiss()
        })
        colorDialog.show()

    }

    private fun requestStoragePermission(){
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        )
        {
            showRationalDialog("Kids Drawing App","Kids Drawing App " +
                    "needs to Access Your External Storage")
        }
        else {
            // You can directly ask for the permission.

            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

    }

    private fun showRationalDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }




}
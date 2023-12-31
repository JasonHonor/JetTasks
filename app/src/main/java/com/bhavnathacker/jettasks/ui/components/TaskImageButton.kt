package com.bhavnathacker.jettasks.ui.components

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bhavnathacker.jettasks.R


@Composable
fun TaskImageButton(
    ctx: Context,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    res_id:Int,
    enabled: Boolean = true
) {
    var imageButton= ImageButton(ctx)
    imageButton.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    //R.mipmap.ic_launcher
    imageButton.setImageResource(res_id)
    imageButton.setOnClickListener {
        Toast.makeText(
            ctx,
            "Clicked",
            Toast.LENGTH_SHORT
        ).show()
        onClick()
    }
}

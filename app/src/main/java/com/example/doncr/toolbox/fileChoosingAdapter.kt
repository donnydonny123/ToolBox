package com.example.doncr.toolbox

import android.content.ClipData
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * adapter for ListView
 */
class FileChoosingAdapter(context: Context, val resource:Int, items:MutableList<RemoteFileInfo>) :ArrayAdapter<RemoteFileInfo>(context, resource, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView : LinearLayout
        val item = getItem(position)
        if (convertView == null){
            itemView = LinearLayout(context)
            val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflate.inflate(resource, itemView)
        }else {
            itemView = convertView as LinearLayout
        }
        val filenameTextView = itemView.findViewById(R.id.fileNameTextview) as TextView
        val editTimeTextView = itemView.findViewById(R.id.editTimeTextView) as TextView
        val fileIsChoosed = itemView.findViewById(R.id.checkChooseFile) as CheckBox
        filenameTextView.text = item.fileName
        editTimeTextView.text = item.lastModifiedTime
        fileIsChoosed.isChecked = item.isChosen
        fileIsChoosed.setOnCheckedChangeListener {_: CompoundButton?, isChecked: Boolean ->
            item.isChosen = isChecked
        }
        return itemView
    }
}

data class RemoteFileInfo(val name: String){
    @SerializedName("filename")
    var fileName: String = name
    @SerializedName("lastModifiedTime")
    var lastModifiedTime:String = ""
    @SerializedName("index")
    var index :Int = -1

    var isChosen:Boolean = false
}
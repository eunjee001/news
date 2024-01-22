package com.kkyoungs.news.data

import androidx.room.PrimaryKey

data class News (val url : String, val title : String, val image: String, val description :String){

    //room 에서 id 자동생성 하는 처리(autoGenerate = true)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}

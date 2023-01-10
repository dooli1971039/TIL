package com.example.flo
import java.util.*

data class Album(
	var title: String? = "",
	var singer: String? = "",
	var coverImg: Int? = null,
	var songs: ArrayList<Song>? = null //수록곡(강의에서는 사용하지 않음)
)

package com.kkyoungs.news.listener

// 추상 메서드가 딱 하나만 존재하는 인터페이스
@FunctionalInterface
interface OnNewsClickListener {
    fun onNewsClick(position:Int)
}
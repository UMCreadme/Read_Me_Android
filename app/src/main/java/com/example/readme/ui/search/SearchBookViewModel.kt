package com.example.readme.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.BookInfo

class SearchBookViewModel : ViewModel() {
    // LiveData를 사용하여 책 검색 목록을 관리
    private val _searchBookItems = MutableLiveData<List<BookInfo>>()
    val searchBookItems: LiveData<List<BookInfo>> get() = _searchBookItems

    // 책 검색
    fun searchBook(query: String) {
        // 검색어를 서버로 전송하고, 검색 결과를 받아오는 로직
        // 받아온 검색 결과를 BookInfo 객체로 변환하여 _searchBookItems에 추가
        // 예시 데이터
        _searchBookItems.value = listOf(
            BookInfo(
                ISBN = "9791193044179",
                bookImg = "https://image.aladin.co.kr/product/34130/13/coversum/k292931290_1.jpg",
                title = "아무튼, SF게임 - 건너편의 세계로 오신 것을 환영합니다",
                author = "김초엽",
                cid = 51371,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=341301335&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9791190090018",
                bookImg = "https://image.aladin.co.kr/product/19359/16/coversum/s152835852_1.jpg",
                title = "우리가 빛의 속도로 갈 수 없다면 - 2019 제43회 오늘의 작가상 수상작",
                author = "김초엽",
                cid = 89482,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=193591681&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9788960908925",
                bookImg = "https://image.aladin.co.kr/product/34390/33/coversum/8960908924_1.jpg",
                title = "스무 낮 읽고 스무 밤 느끼다 - 짧은 소설 스무 편",
                author = "박완서, 정이현, 이기호, 김숨, 이승우, 김금희, 손보미, 백수린, 정지돈, 박서련, 최정화, 김초엽, 조해진, 최은영, 문진영, 김혜진, 정용준, 이주란, 이유리",
                cid = 50993,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=343903324&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9791191824001",
                bookImg = "https://image.aladin.co.kr/product/27692/63/coversum/s222930473_1.jpg",
                title = "지구 끝의 온실",
                author = "김초엽",
                cid = 89482,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=276926308&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9791193044179",
                bookImg = "https://image.aladin.co.kr/product/34130/13/coversum/k292931290_1.jpg",
                title = "아무튼, SF게임 - 건너편의 세계로 오신 것을 환영합니다",
                author = "김초엽",
                cid = 51371,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=341301335&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9791190090018",
                bookImg = "https://image.aladin.co.kr/product/19359/16/coversum/s152835852_1.jpg",
                title = "우리가 빛의 속도로 갈 수 없다면 - 2019 제43회 오늘의 작가상 수상작",
                author = "김초엽",
                cid = 89482,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=193591681&amp;partner=openAPI&amp;start=api"
            ),
            BookInfo(
                ISBN = "9788960908925",
                bookImg = "https://image.aladin.co.kr/product/34390/33/coversum/8960908924_1.jpg",
                title = "스무 낮 읽고 스무 밤 느끼다 - 짧은 소설 스무 편",
                author = "박완서, 정이현, 이기호, 김숨, 이승우, 김금희, 손보미, 백수린, 정지돈, 박서련, 최정화, 김초엽, 조해진, 최은영, 문진영, 김혜진, 정용준, 이주란, 이유리",
                cid = 50993,
                mallType = "BOOK",
                link = "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=343903324&amp;partner=openAPI&amp;start=api"
            ),
        )
    }
}
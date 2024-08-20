//package com.example.readme.ui.home.feed
//
//import com.example.readme.ui.data.entities.inithome.FeedInfo
//import com.example.readme.ui.data.entities.inithome.MainInfoResponse
//import com.example.readme.ui.data.entities.inithome.PageInfo
//import com.example.readme.ui.data.entities.inithome.ResultData
//import com.example.readme.ui.data.entities.inithome.ShortsInfo
//
//object DummyDataProvider {
//
//    fun getDummyMainInfoResponse(): MainInfoResponse {
//    return MainInfoResponse(
//        isSuccess = true,
//        code = "200",
//        message = "Success",
//        pageInfo = getDummyPageInfo(),
//        result = getDummyResultData()
//    )
//}
//
// fun getDummyPageInfo(): PageInfo {
//    return PageInfo(
//        page = 1,
//        size = 10,
//        hasNext = true
//    )
//}
//
// fun getDummyResultData(): ResultData {
//    return ResultData(
//        categories = listOf("추천", "시", "여행", "소설", "인문학", "역사", "예술/문화"),
//        shorts = getDummyShortsInfoList(),
//        feeds = getDummyFeedInfoList()
//    )
//}
//
//
//
// fun getDummyShortsInfoList(): List<ShortsInfo> {
//    return listOf(
//        ShortsInfo(
//            shortsId = 1,
//            shortsImg = "https://example.com/image1.jpg",
//            phrase = "Amazing story",
//            bookTitle = "The Great Adventure",
//            author = "John Doe",
//            translator = "Jane Doe",
//            category = "Fiction"
//        ),
//        ShortsInfo(
//            shortsId = 2,
//            shortsImg = "https://example.com/image2.jpg",
//            phrase = "Insightful read",
//            bookTitle = "The Science of Everything",
//            author = "Alice Smith",
//            translator = null,
//            category = "Science"
//        )
//    )
//}
//
// fun getDummyFeedInfoList(): List<FeedInfo> {
//    return listOf(
//        FeedInfo(
//            userId = 1,
//            profileImg = "https://example.com/profile1.jpg",
//            nickname = "user1",
//            shortsId = 1,
//            shortsImg = "https://example.com/image1.jpg",
//            phrase = "Amazing story",
//            title = "Review of The Great Adventure",
//            content = "This is a review of the book. It was fantastic!",
//            tags = listOf("review", "adventure"),
//            isLike = true,
//            likeCnt = 120,
//            commentCnt = 45,
//            postingDate = "2024-08-10T12:34:56"
//        ),
//        FeedInfo(
//            userId = 2,
//            profileImg = "https://example.com/profile2.jpg",
//            nickname = "user2",
//            shortsId = 2,
//            shortsImg = "https://example.com/image2.jpg",
//            phrase = "Insightful read",
//            title = "Review of The Science of Everything",
//            content = "This is a detailed review of the book.",
//            tags = listOf("review", "science"),
//            isLike = false,
//            likeCnt = 30,
//            commentCnt = 10,
//            postingDate = "2024-08-11T08:20:00"
//        )
//    )
//}
//}
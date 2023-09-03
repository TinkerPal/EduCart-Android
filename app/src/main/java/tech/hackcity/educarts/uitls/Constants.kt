package tech.hackcity.educarts.uitls

import tech.hackcity.educarts.domain.model.news.News
import tech.hackcity.educarts.domain.model.notification.Notification
import tech.hackcity.educarts.domain.model.payment.Program
import tech.hackcity.educarts.domain.model.payment.School
import tech.hackcity.educarts.domain.model.photo.Photo

/**
 *Created by Victor Loveday on 5/11/23
 */
object Constants {

    const val EDU_CARTS_BASE_URL = "https://educartapi.herokuapp.com/api/v1/"
    const val EDU_CARTS_MEDIA_URL = "https://educartapi.herokuapp.com"

    val dummySchoolList = listOf(
        School(
            0,
            "Aberystwyth University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo.png"
        ),
        School(
            1,
            "Abilene Christian University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo2.png"
        ),
        School(
            2,
            "Aberystwyth University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo.png"
        ),
        School(
            3,
            "Adelphi University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo2.png"
        ),
        School(
            4,
            "Agnes Scott College",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo.png"
        ),
        School(
            5,
            "Aberystwyth University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo.png"
        ),
        School(
            6,
            "Alaska Pacific University",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo2.png"
        ),
        School(
            7,
            "Albany College of Pharmacy and He...",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo.png"
        ),
        School(
            8,
            "Albertus Magnus College",
            "Aberystwyth, Ceredigion",
            " - GBR",
            "https://media.publit.io/file/university_logo2.png"
        ),
    )

    val dummyProgramList = listOf(
        Program(
            0,
            "4-Year Bachelor’s Degree",
            "Bachelor of Applied Science",
            "Arizona state University, Phoenix",
            "USA",
            "\$32,760 USD",
            "\$0 USD"
        ),
        Program(
            1,
            "4-Year Bachelor’s Degree",
            "Bachelor of Applied Science",
            "Arizona state University, Phoenix",
            "USA",
            "\$32,760 USD",
            "\$0 USD"
        ),
        Program(
            2,
            "4-Year Bachelor’s Degree",
            "Bachelor of Applied Science",
            "Arizona state University, Phoenix",
            "USA",
            "\$32,760 USD",
            "\$0 USD"
        ),
        Program(
            3,
            "4-Year Bachelor’s Degree",
            "Bachelor of Applied Science",
            "Arizona state University, Phoenix",
            "USA",
            "\$32,760 USD",
            "\$0 USD"
        ),
        Program(
            4,
            "4-Year Bachelor’s Degree",
            "Bachelor of Applied Science",
            "Arizona state University, Phoenix",
            "USA",
            "\$32,760 USD",
            "\$0 USD"
        ),
    )

    val dummyPhotoList = listOf(
        Photo(0, "https://media.publit.io/file/download-1-r.jpeg"),
        Photo(1, "https://media.publit.io/file/download-Ab.jpeg"),
        Photo(2, "https://media.publit.io/file/dummy_pic1.png")
    )

    val dummyNewsList = arrayListOf(
        News(0, "UniLag Hot Gist", "https://media.publit.io/file/download-1-r.jpeg", shortenString("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", 120)),
        News(1, "SEVIS Fee", "https://media.publit.io/file/dummy_pic1.png", shortenString("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", 120)),
        News(2, "Tuition Fee", "https://media.publit.io/file/download-Ab.jpeg", shortenString("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", 120)),
        News(3, "Tuition Fee", "https://media.publit.io/file/download-Ab.jpeg", shortenString("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", 120)),
        News(4, "Tuition Fee", "https://media.publit.io/file/download-Ab.jpeg", shortenString("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", 120)),
    )

    val dummyNotificationLIst = arrayListOf(
        Notification(0, "", "Free Apartments", "UniLag offering"),
        Notification(1, "", "EduCarts Award", "UniLag offering"),
        Notification(2, "", "Payment Tracking", "UniLag offering"),
        Notification(3, "", "Payment to Oxford", "UniLag offering"),
        Notification(4, "", "Free Apartments", "UniLag offering"),
        Notification(5, "", "Free Apartments", "UniLag offering")
    )
}
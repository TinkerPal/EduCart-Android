package tech.hackcity.educarts.uitls

import tech.hackcity.educarts.domain.model.news.News
import tech.hackcity.educarts.domain.model.notification.Notification
import tech.hackcity.educarts.domain.model.payment.PaymentHistory
import tech.hackcity.educarts.domain.model.payment.Program
import tech.hackcity.educarts.domain.model.payment.School
import tech.hackcity.educarts.domain.model.photo.Photo
import tech.hackcity.educarts.domain.model.support.FaqCategory
import tech.hackcity.educarts.domain.model.support.Faq

/**
 *Created by Victor Loveday on 5/11/23
 */
object Constants {

    const val EDU_CARTS_BASE_URL = "https://educartapi.herokuapp.com/api/v1/"

    val dummyTransactionList = listOf(
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order in process", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Payment confirmed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Payment pending", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
        PaymentHistory("SF12002", "SEVIS/I-20", "Order completed", "March 3, 2023"),
    )

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

    val dummyConsultationFAQs = arrayListOf(
        "Payment for institution",
        "How to get abroad admission docs",
        "How to apply for admission abroad",
        "Where to get my visa?",
        "Other"
    )

    val faq1 = Faq(0, "Who designed EduCarts mobile app?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq2 = Faq(1, "Who designed EduCarts web app?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq3 = Faq(2, "Who designed EduCarts backend?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  " ,"Payment")
    val faq4 = Faq(3, "Who designed EduCarts frontend?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq5 = Faq(4, "Who designed EduCarts architecture?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq6 = Faq(5, "Who designed EduCarts logo?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq7 = Faq(6, "Who designed EduCarts business model?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val faq8 = Faq(7, "Who designed EduCarts?", "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael, Maleek and Edidiong) ,which are now senior Product Designers, lead by a senior product designer Mr. David Adewale at HackCity Tech Inc. in the year 2023 and developed by mobile developers.  ", "Payment")
    val dummyFAQ:List<Faq> = arrayListOf(faq1, faq2, faq3, faq3, faq4, faq5, faq6, faq7)

    val dummyFaqCategory = arrayListOf(
        FaqCategory(dummyFAQ, "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael,", "", "Payment"),
        FaqCategory(dummyFAQ, "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael,", "https://media.publit.io/file/dummy_pic1.png", "Account"),
        FaqCategory(dummyFAQ, "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael,", "https://media.publit.io/file/dummy_pic1.png", "Security"),
        FaqCategory(dummyFAQ, "EduCarts app is designed completely by a total number of 3 UX Design Interns(Michael,", "https://media.publit.io/file/dummy_pic1.png", "Privacy"),
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
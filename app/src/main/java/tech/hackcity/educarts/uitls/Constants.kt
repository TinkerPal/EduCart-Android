package tech.hackcity.educarts.uitls

import tech.hackcity.educarts.domain.model.payment.PaymentHistory
import tech.hackcity.educarts.domain.model.payment.Program
import tech.hackcity.educarts.domain.model.payment.School
import tech.hackcity.educarts.domain.model.photo.Photo

/**
 *Created by Victor Loveday on 5/11/23
 */
object Constants {

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
}
package com.example.drivinglicence.app.viewmodel

import android.content.Context
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.component.viewmodel.BaseViewModel
import com.example.drivinglicence.utils.*
import kotlin.random.Random

class MapDataViewModel : BaseViewModel() {

    val mapResult = mutableMapOf<Int, Boolean>()
    var listQuestion: MutableList<Question> = mutableListOf()
    var listAnswer: MutableList<MutableList<Answer>> = mutableListOf()

    var mapAnswerImportant = HashMap<Int, MutableList<Answer>>()
    var mapAnswerConceptsAndRules = HashMap<Int, MutableList<Answer>>()
    var mapAnswerCulturesAndEthics = HashMap<Int, MutableList<Answer>>()
    var mapAnswerDrivingUnique = HashMap<Int, MutableList<Answer>>()
    var mapAnswerRoadSign = HashMap<Int, MutableList<Answer>>()
    var mapAnswerSatFigure = HashMap<Int, MutableList<Answer>>()
    val mapping: MutableMap<Int, MutableList<Answer>> = mutableMapOf()
    val mapQuestion = HashMap<Int, MutableList<Question>>()
    var listImportant = mutableListOf<Question>() //35
    var listConceptAndRule = mutableListOf<Question>() //30
    var listCulturesAndEthic = mutableListOf<Question>() //5
    var listDrivingUnique = mutableListOf<Question>() //12
    var listRoadSign = mutableListOf<Question>() //20
    var listSatFigure = mutableListOf<Question>() //20

    /** 35 question important 1 -> 35 */
    private fun getAnswerWithQuestionImportant() {
        for (i in 1..35) {
            mapAnswerImportant[i] = listAnswerImportant[i - 1]
        }
    }

    fun getListQuestionImportant(context: Context): MutableList<Question> {
        val list = mutableListOf<Question>()
        for (i in 1..35) {
            val id =
                context.resources.getIdentifier(
                    "question_important_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i, null, context.getString(id), null, true)
                )
            }
        }
        getAnswerWithQuestionImportant()
        return list
    }

    /** 30 question  concepts and rules 41 -> 70 */
    private fun getAnswerWithQuestionConcepts() {
        for (i in 1..30) {
            mapAnswerConceptsAndRules[i + 40] = listAnswerConceptAndRule[i - 1]
        }
    }

    fun getListQuestionConceptsAndRules(context: Context): MutableList<Question> {
        val list = mutableListOf<Question>()
        for (i in 1..14) {
            val id =
                context.resources.getIdentifier(
                    "question_concepts_and_rules_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i + 40, null, context.getString(id), null, false)
                )
            }
        }
        //55 -> 70
        list.add(Question(55, null, context.getString(R.string.question_important_3), null, true))
        list.add(Question(56, null, context.getString(R.string.question_important_4), null, true))
        list.add(Question(57, null, context.getString(R.string.question_important_7), null, true))
        list.add(Question(58, null, context.getString(R.string.question_important_8), null, true))
        list.add(Question(59, null, context.getString(R.string.question_important_10), null, true))
        list.add(Question(60, null, context.getString(R.string.question_important_18), null, true))
        list.add(Question(61, null, context.getString(R.string.question_important_21), null, true))
        list.add(Question(62, null, context.getString(R.string.question_important_22), null, true))
        list.add(Question(63, null, context.getString(R.string.question_important_23), null, true))
        list.add(Question(64, null, context.getString(R.string.question_important_24), null, true))
        list.add(Question(65, null, context.getString(R.string.question_important_25), null, true))
        list.add(Question(66, null, context.getString(R.string.question_important_26), null, true))
        list.add(Question(67, null, context.getString(R.string.question_important_27), null, true))
        list.add(Question(68, null, context.getString(R.string.question_important_28), null, true))
        list.add(Question(69, null, context.getString(R.string.question_important_29), null, true))
        list.add(Question(70, null, context.getString(R.string.question_important_34), null, true))
        getAnswerWithQuestionConcepts()
        return list
    }

    /** 5 question cultures and ethics 81 -> 85 */
    private fun getAnswerWithQuestionCulturesAndEthics() {
        for (i in 1..5) {
            mapAnswerCulturesAndEthics[i + 80] = listAnswerCultureAndEthic[i - 1]
        }
    }

    fun getListQuestionCulturesAndEthics(context: Context): MutableList<Question> {
        val list = mutableListOf<Question>()
        for (i in 1..5) {
            val id =
                context.resources.getIdentifier(
                    "question_culture_and_ethics_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i + 80, null, context.getString(id), null, false)
                )
            }
        }
        getAnswerWithQuestionCulturesAndEthics()
        return list
    }

    /** 12 question driving unique 91 -> 102 */
    private fun getAnswerWithQuestionDrivingUnique() {
        for (i in 1..12) {
            mapAnswerDrivingUnique[i + 90] = listAnswerDrivingUnique[i - 1]
        }
    }

    fun getListQuestionDrivingUnique(context: Context): MutableList<Question> {
        val list = mutableListOf<Question>()
        for (i in 1..10) {
            val id =
                context.resources.getIdentifier(
                    "question_driving_unique_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i + 90, null, context.getString(id), null, false)
                )
            }
        }
        list.add(Question(101, null, context.getString(R.string.question_important_34), null, true))
        list.add(Question(102, null, context.getString(R.string.question_important_35), null, true))
        getAnswerWithQuestionDrivingUnique()
        return list
    }

    /** 20 question road sign 111 -> 130 */
    private fun getAnswerWithQuestionRoadSign() {
        for (i in 1..20) {
            mapAnswerRoadSign[i + 110] = listAnswerRoadSign[i - 1]
        }
    }

    fun getListQuestionRoadSign(context: Context): MutableList<Question> {
        val imageList = mutableListOf(
            R.drawable.road_sign_1, R.drawable.road_sign_2, R.drawable.road_sign_2,
            R.drawable.road_sign_4, R.drawable.road_sign_4, R.drawable.road_sign_4,
            R.drawable.road_sign_7, R.drawable.road_sign_8, R.drawable.road_sign_9,
            R.drawable.road_sign_9, R.drawable.road_sign_11, R.drawable.road_sign_12,
            R.drawable.road_sign_12, R.drawable.road_sign_14, R.drawable.road_sign_15,
            R.drawable.road_sign_16, R.drawable.road_sign_16, R.drawable.road_sign_18,
            R.drawable.road_sign_19, R.drawable.road_sign_20
        )
        val list = mutableListOf<Question>()
        for (i in 1..20) {
            val id =
                context.resources.getIdentifier(
                    "question_road_sign_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i + 110, null, context.getString(id), imageList[i - 1], false)
                )
            }
        }
        getAnswerWithQuestionRoadSign()
        return list
    }

    /** 20 question sat figure 141 -> 160 */
    private fun getAnswerWithQuestionSatFigure() {
        for (i in 1..20) {
            mapAnswerSatFigure[i + 140] = listAnswerSatFigure[i - 1]
        }
    }

    fun getListQuestionSatFigure(context: Context): MutableList<Question> {
        val imageList = mutableListOf(
            R.drawable.sat_figure_1, R.drawable.sat_figure_2, R.drawable.sat_figure_3,
            R.drawable.sat_figure_4, R.drawable.sat_figure_5, R.drawable.sat_figure_6,
            R.drawable.sat_figure_7, R.drawable.sat_figure_8, R.drawable.sat_figure_9,
            R.drawable.sat_figure_10, R.drawable.sat_figure_11, R.drawable.sat_figure_12,
            R.drawable.sat_figure_13, R.drawable.sat_figure_14, R.drawable.sat_figure_15,
            R.drawable.sat_figure_16, R.drawable.sat_figure_17, R.drawable.sat_figure_18,
            R.drawable.sat_figure_19, R.drawable.sat_figure_20
        )
        val list = mutableListOf<Question>()
        for (i in 1..20) {
            val id =
                context.resources.getIdentifier(
                    "question_sat_figure_$i",
                    "string",
                    context.packageName
                )
            if (id > 0) {
                list.add(
                    Question(i + 140, null, context.getString(id), imageList[i - 1], false)
                )
            }
        }
        getAnswerWithQuestionSatFigure()
        return list
    }

    fun getQuestionTest(position: Int): MutableList<Question> {
        /**25 question in 1 exam
         * 6 question important
         * 7 question concept and rule (not important)
         * 2 question cultures and ethic
         * 2 question driving unique (not important)
         * 4 question road sign
         * 4 question sat figure
         * */
        when (position) {
            1 -> {
                val list = getQuestionExam1()
                mapQuestion[position] = list
            }
            2 -> {
                val list = getQuestionExam2()
                mapQuestion[position] = list
            }
            3 -> {
                val list = getQuestionExam3()
                mapQuestion[position] = list
            }
            4 -> {
                val list = getQuestionExam4()
                mapQuestion[position] = list
            }
            5 -> {
                val list = getQuestionExam5()
                mapQuestion[position] = list
            }
            6->{
                val list = getQuestionExam6()
                mapQuestion[position] = list
            }
            else -> {
                if (mapQuestion[position] == null) {
                    getRandomQuestionExam(position)
                }
            }
        }
        return mapQuestion[position] ?: mutableListOf()
    }

    private fun getQuestionExam1(): MutableList<Question> {
        return mutableListOf(
            /**6 question important*/
            listImportant[0], listImportant[2], listImportant[4], listImportant[6],
            listImportant[8], listImportant[10],
            /**7 question concept and rule (not important 1 - 14)*/
            listConceptAndRule[0], listConceptAndRule[1], listConceptAndRule[2],
            listConceptAndRule[3], listConceptAndRule[5], listConceptAndRule[6],
            listConceptAndRule[8],
            /**2 question cultures and ethic*/
            listCulturesAndEthic[0], listCulturesAndEthic[1],
            /**2 question driving unique (not important 1 - 10)*/
            listDrivingUnique[0], listDrivingUnique[1],
            /**4 question road sign*/
            listRoadSign[0], listRoadSign[2], listRoadSign[4], listRoadSign[6],
            /**4 question sat figure*/
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        )
    }

    private fun getQuestionExam2(): MutableList<Question> {
        return mutableListOf(
            /**6 question important*/
            listImportant[0], listImportant[1], listImportant[3], listImportant[5],
            listImportant[7], listImportant[9],
            /**7 question concept and rule (not important 1 - 14)*/
            listConceptAndRule[0], listConceptAndRule[2], listConceptAndRule[4],
            listConceptAndRule[6], listConceptAndRule[8], listConceptAndRule[10],
            listConceptAndRule[12],
            /**2 question cultures and ethic*/
            listCulturesAndEthic[1], listCulturesAndEthic[2],
            /**2 question driving unique (not important 1 - 10)*/
            listDrivingUnique[1], listDrivingUnique[2],
            /**4 question road sign*/
            listRoadSign[0], listRoadSign[1], listRoadSign[3], listRoadSign[5],
            /**4 question sat figure*/
            listSatFigure[1], listSatFigure[3], listSatFigure[5], listSatFigure[7],
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        )
    }

    private fun getQuestionExam3(): MutableList<Question> {
        return mutableListOf(
            /**6 question important*/
            listImportant[0], listImportant[1], listImportant[2], listImportant[4],
            listImportant[6], listImportant[9],
            /**7 question concept and rule (not important 1 - 14)*/
            listConceptAndRule[0], listConceptAndRule[1], listConceptAndRule[4],
            listConceptAndRule[5], listConceptAndRule[8], listConceptAndRule[9],
            listConceptAndRule[11],
            /**2 question cultures and ethic*/
            listCulturesAndEthic[2], listCulturesAndEthic[3],
            /**2 question driving unique (not important 1 - 10)*/
            listDrivingUnique[2], listDrivingUnique[3],
            /**4 question road sign*/
            listRoadSign[7], listRoadSign[9], listRoadSign[11], listRoadSign[13],
            /**4 question sat figure*/
            listSatFigure[4], listSatFigure[6], listSatFigure[8], listSatFigure[10],
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        )
    }

    private fun getQuestionExam4(): MutableList<Question> {
        return mutableListOf(
            /**6 question important*/
            listImportant[7], listImportant[9], listImportant[11], listImportant[13],
            listImportant[15], listImportant[17],
            /**7 question concept and rule (not important 1 - 14)*/
            listConceptAndRule[0], listConceptAndRule[1], listConceptAndRule[3],
            listConceptAndRule[6], listConceptAndRule[8], listConceptAndRule[9],
            listConceptAndRule[14],
            /**2 question cultures and ethic*/
            listCulturesAndEthic[3], listCulturesAndEthic[4],
            /**2 question driving unique (not important 1 - 10)*/
            listDrivingUnique[3], listDrivingUnique[4],
            /**4 question road sign*/
            listRoadSign[9], listRoadSign[10], listRoadSign[12], listRoadSign[14],
            /**4 question sat figure*/
            listSatFigure[5], listSatFigure[7], listSatFigure[9], listSatFigure[11],
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        )
    }

    private fun getQuestionExam5(): MutableList<Question> {
        return mutableListOf(
            /**6 question important*/
            listImportant[8], listImportant[10], listImportant[12], listImportant[14],
            listImportant[16], listImportant[26],
            /**7 question concept and rule (not important 1 - 14)*/
            listConceptAndRule[2], listConceptAndRule[4], listConceptAndRule[5],
            listConceptAndRule[6], listConceptAndRule[8], listConceptAndRule[11],
            listConceptAndRule[13],
            /**2 question cultures and ethic 1 - 5*/
            listCulturesAndEthic[0], listCulturesAndEthic[4],
            /**2 question driving unique (not important 1 - 10)*/
            listDrivingUnique[4], listDrivingUnique[5],
            /**4 question road sign*/
            listRoadSign[11], listRoadSign[13], listRoadSign[15], listRoadSign[19],
            /**4 question sat figure*/
            listSatFigure[10], listSatFigure[12], listSatFigure[16], listSatFigure[17],
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        )
    }

    private fun getQuestionExam6(): MutableList<Question> {
        val list = mutableListOf<Question>()
        repeat(30){
            list.add(listImportant[1])
        }
        return list
    }

    private fun getRandomQuestionExam(position: Int): MutableList<Question> {
        /**30 question in 1 exam
         * 6 question important [0;34]
         * 7 question concept and rule (not important) (not important [0;13])
         * 2 question cultures and ethic [0;4]
         * 2 question driving unique (not important) [0;9]
         * 4 question road sign [0;19]
         * 4 question sat figure [0;19]
         * */
        var list = mutableListOf<Question>()
        for (i in 1..6) {
            var random = Random.nextInt(0, 35)
            while (list.contains(listImportant[random])) {
                random = Random.nextInt(0, 35)
            }
            list.add(listImportant[random])
        }
        for (i in 1..7) {
            var random = Random.nextInt(0, 14)
            while (list.contains(listConceptAndRule[random])) {
                random = Random.nextInt(0, 14)
            }
            list.add(listConceptAndRule[random])
        }
        for (i in 1..2) {
            var random = Random.nextInt(0, 5)
            while (list.contains(listCulturesAndEthic[random])) {
                random = Random.nextInt(0, 5)
            }
            list.add(listCulturesAndEthic[random])
        }
        for (i in 1..2) {
            var random = Random.nextInt(0, 10)
            while (list.contains(listDrivingUnique[random])) {
                random = Random.nextInt(0, 10)
            }
            list.add(listDrivingUnique[random])
        }
        for (i in 1..4) {
            var random = Random.nextInt(0, 20)
            while (list.contains(listRoadSign[random])) {
                random = Random.nextInt(0, 20)
            }
            list.add(listRoadSign[random])
        }
        for (i in 1..4) {
            var random = Random.nextInt(0, 20)
            while (list.contains(listSatFigure[random])) {
                random = Random.nextInt(0, 20)
            }
            list.add(listSatFigure[random])
        }
        for (i in 1..5) {
            list = list.shuffled() as MutableList<Question>
        }
        list.addAll(mutableListOf(
            listSatFigure[0], listSatFigure[2], listSatFigure[4], listSatFigure[6],listSatFigure[6]
        ))
        mapQuestion[position] = list
        return list
    }

    fun getAnswerTest(questions: MutableList<Question>): MutableList<MutableList<Answer>> {
        val listAnswer = mutableListOf<MutableList<Answer>>()
        for (item in questions) {
            mapping[item.questionId]?.let { listAnswer.add(it) }
        }
        return listAnswer
    }

    fun getAnswer(question: Question): MutableList<Answer> {
        return mapping[question.questionId] as MutableList<Answer>
    }

    fun getAllData(context: Context) {
        listImportant = getListQuestionImportant(context)
        listConceptAndRule = getListQuestionConceptsAndRules(context)
        listCulturesAndEthic = getListQuestionCulturesAndEthics(context)
        listDrivingUnique = getListQuestionDrivingUnique(context)
        listRoadSign = getListQuestionRoadSign(context)
        listSatFigure = getListQuestionSatFigure(context)

        mapping.putAll(mapAnswerImportant)
        mapping.putAll(mapAnswerConceptsAndRules)
        mapping.putAll(mapAnswerCulturesAndEthics)
        mapping.putAll(mapAnswerDrivingUnique)
        mapping.putAll(mapAnswerRoadSign)
        mapping.putAll(mapAnswerSatFigure)
    }

    fun getData(context: Context) {
        val dataViewModel = DataViewModel()
        dataViewModel.getAllData(context)
    }

    fun clearSection() {
        mapResult.clear()
        listQuestion.clear()
        listAnswer.clear()
        mapAnswerImportant.clear()
        mapAnswerConceptsAndRules.clear()
        mapAnswerCulturesAndEthics.clear()
        mapAnswerDrivingUnique.clear()
        mapAnswerRoadSign.clear()
        mapAnswerSatFigure.clear()
        mapping.clear()
        mapQuestion.clear()
        listImportant = mutableListOf()
        listConceptAndRule = mutableListOf()
        listCulturesAndEthic = mutableListOf()
        listDrivingUnique = mutableListOf()
        listRoadSign = mutableListOf()
        listSatFigure = mutableListOf()
    }
}
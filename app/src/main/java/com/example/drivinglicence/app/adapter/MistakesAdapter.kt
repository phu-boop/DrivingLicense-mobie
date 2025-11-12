package com.example.drivinglicence.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.MistakeQuestion

class MistakesAdapter(private var mistakes: List<MistakeQuestion>) :
    RecyclerView.Adapter<MistakesAdapter.MistakeViewHolder>() {

    class MistakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTitle: TextView = itemView.findViewById(R.id.tv_question_title)
        val answersLayout: LinearLayout = itemView.findViewById(R.id.layout_answers)
        val answersContainer: LinearLayout = itemView.findViewById(R.id.answers_container)
        val explanationText: TextView = itemView.findViewById(R.id.tv_explanation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MistakeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_common_mistake, parent, false)
        return MistakeViewHolder(view)
    }

    override fun getItemCount(): Int = mistakes.size

    override fun onBindViewHolder(holder: MistakeViewHolder, position: Int) {
        val mistake = mistakes[position]
        holder.questionTitle.text = mistake.question
        holder.explanationText.text = mistake.explanation
        holder.answersContainer.removeAllViews()

        mistake.answers.forEachIndexed { index, answerText ->
            val answerView = TextView(holder.itemView.context).apply {
                text = "${index + 1}. $answerText"
                textSize = 15f
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                val padding = (12 * resources.displayMetrics.density).toInt()
                setPadding(padding, padding, padding, padding)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.bottomMargin = (8 * resources.displayMetrics.density).toInt()
                layoutParams = params

                if (index == mistake.correctAnswerIndex) {
                    setBackgroundResource(R.drawable.correct_answer_background)
                } else {
                    setBackgroundResource(R.drawable.default_answer_background)
                }
            }
            holder.answersContainer.addView(answerView)
        }

        holder.itemView.setOnClickListener {
            val isVisible = holder.answersLayout.visibility == View.VISIBLE
            holder.answersLayout.visibility = if (isVisible) View.GONE else View.VISIBLE
        }
    }

    fun updateData(newMistakes: List<MistakeQuestion>) {
        this.mistakes = newMistakes
        notifyDataSetChanged()
    }
}
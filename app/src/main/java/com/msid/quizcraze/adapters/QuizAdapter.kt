package com.msid.quizcraze.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.msid.quizcraze.R
import com.msid.quizcraze.activities.QuestionActivity
import com.msid.quizcraze.models.Question
import com.msid.quizcraze.models.Quiz
import com.msid.quizcraze.utils.ColorPickers
import com.msid.quizcraze.utils.IconPicker

class QuizAdapter(private val context: Context, val quizzes : List<Quiz>): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var tvQuizTitle: TextView = itemView.findViewById(R.id.tvQuizTitle)
         var imageView: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer : CardView = itemView.findViewById(R.id.cardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.tvQuizTitle.text=quizzes[position].title
        holder.cardContainer.setBackgroundColor(Color.parseColor(ColorPickers.getColor()))
        holder.imageView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }
}
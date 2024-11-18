import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhainusa.testsxperts.Question
import com.jhainusa.testsxperts.R

class QuestionAdapter(
    private val questions: List<Question>,
    private val onAnswerSelected: (Int, String) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemquestion, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question, position, onAnswerSelected)
    }

    override fun getItemCount() = questions.size

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionText: TextView = itemView.findViewById(R.id.questionTextView)
        private val questionImage: ImageView = itemView.findViewById(R.id.questionImageView)
        private val optionsGroup: RadioGroup = itemView.findViewById(R.id.optionsRadioGroup)

        fun bind(question: Question, position: Int, onAnswerSelected: (Int, String) -> Unit) {
            questionText.text = question.question_text
            Glide.with(itemView.context).load(question.image_url).into(questionImage)
            val typeface = ResourcesCompat.getFont(itemView.context, R.font.rubik)
            optionsGroup.removeAllViews()
            question.option?.forEach { option ->
                val radioButton = RadioButton(itemView.context)
                    radioButton.text = option
                    radioButton.typeface=typeface
                radioButton.textSize = 20f
                radioButton.setPadding(20,24,20,24)

                optionsGroup.addView(radioButton)
            }

            optionsGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedOption = itemView.findViewById<RadioButton>(checkedId).text.toString()
                onAnswerSelected(position, selectedOption)
            }
        }
    }
}

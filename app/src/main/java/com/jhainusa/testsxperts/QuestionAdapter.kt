import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
            // Set the question text
            questionText.text = question.question_text

            // Load the question image (if provided)
            Glide.with(itemView.context)
                .load(question.image_url)
                .into(questionImage)

            // Set custom font for options
            val typefac = ResourcesCompat.getFont(itemView.context, R.font.rubik)

            // Clear previous options in the RadioGroup
            optionsGroup.removeAllViews()

            // Dynamically add options as RadioButtons
            question.option?.forEach { option ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = option
                    typeface = typefac
                    textSize = 18f
                    setPadding(20, 24, 20, 24)

                    // Set custom drawable as the radio button's style
                    background = ContextCompat.getDrawable(itemView.context, R.drawable.custom_radio_button)

                    // Add margins for better spacing
                    val params = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 16, 0, 16)
                    layoutParams = params
                }

                // Add the RadioButton to the RadioGroup
                optionsGroup.addView(radioButton)
            }

            // Listen for selection changes in the RadioGroup
            optionsGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedRadioButton = itemView.findViewById<RadioButton>(checkedId)
                val selectedOption = selectedRadioButton.text.toString()
                onAnswerSelected(position, selectedOption)
            }
        }
    }
}

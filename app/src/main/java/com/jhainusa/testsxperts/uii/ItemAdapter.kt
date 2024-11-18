
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jhainusa.testsxperts.R
import com.jhainusa.testsxperts.uii.testCardView
import com.squareup.picasso.Picasso

class MyAdapter(private var postArray: List<testCardView>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mylistener : onItemClickListener

    interface onItemClickListener {
        fun onItemCLick(position: Int)
    }
    fun setItemCLickListener(listener: onItemClickListener){
        mylistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.testseriescard, parent, false)
        return MyViewHolder(itemView,mylistener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(postArray[position])
    }

    override fun getItemCount(): Int = postArray.size

    inner class MyViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.testSeriesname)
        private val testimg:ImageView=itemView.findViewById(R.id.testSeriesImage)
        fun bind(test:testCardView) {
            // Use safe calls with default values to prevent null issues
            name.text = test.testname ?: "No Name"
            Picasso.get().load(test.testImage).into(testimg)
        }
        init{
            itemView.setOnClickListener {
                listener.onItemCLick(adapterPosition)
            }
        }
    }
}

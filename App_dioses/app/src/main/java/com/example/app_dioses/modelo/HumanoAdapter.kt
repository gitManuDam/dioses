import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.app_dioses.modelo.Humano

class HumanoAdapter(context: Context, humanos: List<Humano>) :
    ArrayAdapter<Humano>(context, android.R.layout.simple_spinner_item, humanos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.nombre // Solo muestra el nombre
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.nombre // Solo muestra el nombre en el dropdown
        return view
    }
}

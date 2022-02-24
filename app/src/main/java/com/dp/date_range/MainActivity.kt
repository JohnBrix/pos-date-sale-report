package com.dp.date_range

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.internal.ContextUtils.getActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    /*private val saleLedger: SaleLedger? = null*/
    var saleList: List<Map<String, String>>? = null
    //private var saleLedgerListView: ListView? = null
/*     var totalBox: TextView*/
    private lateinit var spinner: Spinner
    private lateinit var previousButton: Button
    private var nextButton: Button? = null
    private lateinit var currentBox: TextView
    lateinit var currentTime: Calendar

    val DAILY = 0
    val WEEKLY = 1
    val MONTHLY = 2
    val YEARLY = 3

     lateinit var datePicker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        DateTimeStrategy()

        previousButton = findViewById<Button>(R.id.previousButton)
        nextButton = findViewById<Button>(R.id.nextButton)
        currentBox = findViewById<TextView>(R.id.currentBox)
       //saleLedgerListView = findViewById<ListView>(R.id.saleListView)
        //totalBox = findViewById<TextView>(R.id.totalBox)
        spinner = findViewById<Spinner>(R.id.spinner1)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        // update();
        // it shouldn't call update() anymore. Because super.onResume()
        // already fired the action of spinner.onItemSelected()
    }

    private fun initUI() {

        currentTime = Calendar.getInstance()
        datePicker = DatePickerDialog(
            this,
            { view, y, m, d ->
                currentTime.set(Calendar.YEAR, y)
                currentTime.set(Calendar.MONTH, m)
                currentTime.set(Calendar.DAY_OF_MONTH, d)
                update()
            }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(
                Calendar.DAY_OF_MONTH
            )
        )

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.period, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                update()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        currentBox.setOnClickListener { datePicker.show() }
        previousButton.setOnClickListener { addDate(-1) }
        nextButton!!.setOnClickListener { addDate(1) }
        /*saleLedgerListView!!.onItemClickListener =
            OnItemClickListener { myAdapter, myView, position, mylng ->
                val id = saleList!![position]["id"].toString()
                val newActivity =
                    Intent(applicationContext, MainActivity::class.java)
                newActivity.putExtra("id", id)
                startActivity(newActivity)
            }*/
    }

    fun update() {
        val period = spinner.selectedItemPosition

        val cTime = currentTime.clone() as Calendar
        var eTime = currentTime.clone() as Calendar
        var d = DateTimeStrategy()

        if (period == DAILY) {
            currentBox.text =
                " [" + d.getSQLDateFormat(currentTime) + "] "
            currentBox.textSize = 16f

            Toast.makeText(this, "DAILY ${d.getSQLDateFormat(currentTime)}", Toast.LENGTH_SHORT).show()

        } else if (period == WEEKLY) {
            while (cTime[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY) {
                cTime.add(Calendar.DATE, -1)
            }

            var toShow = " [" + d.getSQLDateFormat(cTime).toString() + "] ~ ["
            eTime = cTime.clone() as Calendar
            eTime.add(Calendar.DATE, 7)
            toShow += d.getSQLDateFormat(eTime).toString() + "] "
            currentBox.textSize = 16f
            currentBox.text = toShow
            Toast.makeText(this, "WEEKLY ${toShow}", Toast.LENGTH_SHORT).show()

        } else if (period == MONTHLY) {
            cTime[Calendar.DATE] = 1
            eTime = cTime.clone() as Calendar
            eTime.add(Calendar.MONTH, 1)
            eTime.add(Calendar.DATE, -1)
            currentBox.textSize = 18f
            currentBox.text =
                " [" + currentTime!![Calendar.YEAR] + "-" + (currentTime!![Calendar.MONTH] + 1) + "] "
            Toast.makeText(this, "MONTHLY ${ currentBox.text.toString()}", Toast.LENGTH_SHORT).show()

        } else if (period == YEARLY) {
            cTime[Calendar.DATE] = 1
            cTime[Calendar.MONTH] = 0
            eTime = cTime.clone() as Calendar
            eTime.add(Calendar.YEAR, 1)
            eTime.add(Calendar.DATE, -1)
            currentBox!!.textSize = 20f
            currentBox!!.text = " [" + currentTime!![Calendar.YEAR] + "] "
            Toast.makeText(this, "YEARLY ${ currentBox.text.toString()}", Toast.LENGTH_SHORT).show()

        }
        currentTime = cTime
        /*list = saleLedger.getAllSaleDuring(cTime, eTime)*/
        var total = 0.0
      /*  for (sale in list) total += sale.getTotal()*/
      /*  totalBox.text = total.toString() + ""*/
       /* showList(list)*/
    }

    /**
     * Add date.
     * @param increment
     */
    private fun addDate(increment: Int) {
        val period = spinner.selectedItemPosition
        if (period == DAILY) {
            currentTime.add(Calendar.DATE, 1 * increment)
        } else if (period == WEEKLY) {
            currentTime.add(Calendar.DATE, 7 * increment)
        } else if (period == MONTHLY) {
            currentTime.add(Calendar.MONTH, 1 * increment)
        } else if (period == YEARLY) {
            currentTime.add(Calendar.YEAR, 1 * increment)
        }
        update()
    }
}
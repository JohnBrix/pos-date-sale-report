package com.dp.date_range.ui.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dp.date_range.DateTimeStrategy
import com.dp.date_range.R
import com.dp.date_range.databinding.ActivityMainBinding
import com.dp.date_range.domain.networkEntities.request.HttpDailyDateRequest
import com.dp.date_range.domain.networkEntities.request.HttpMonthlyRequest
import com.dp.date_range.domain.networkEntities.request.HttpWeeklyRequest
import com.dp.date_range.domain.networkEntities.request.HttpYearlyRequest
import com.dp.date_range.dto.DateRequest
import com.dp.date_range.ui.viewmodel.MainActivityViewModel
import java.util.*

class MainActivity : AppCompatActivity() {


    var saleList: List<Map<String, String>>? = null
    lateinit var currentTime: Calendar

    val DAILY = 0
    val WEEKLY = 1
    val MONTHLY = 2
    val YEARLY = 3

    private lateinit var binding: ActivityMainBinding
    private lateinit var vModel: MainActivityViewModel
    private var TAG = "MainActivity: "



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding =
            DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        vModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.mainActivityViewModel = vModel
        binding.lifecycleOwner = this@MainActivity
        supportActionBar?.hide()


        DateTimeStrategy()

        /*previousButton = findViewById<Button>(R.id.previousButton)
        nextButton = findViewById<Button>(R.id.nextButton)
        currentBox = findViewById<TextView>(R.id.currentBox)
       //saleLedgerListView = findViewById<ListView>(R.id.saleListView)
        //totalBox = findViewById<TextView>(R.id.totalBox)
        spinner = findViewById<Spinner>(R.id.spinner1)*/
        initUI()
    }

    override fun onResume() {
        super.onResume()
        // update();
        // it shouldn't call update() anymore. Because super.onResume()
        // already fired the action of spinner.onItemSelected()
    }

    private fun initUI() {

        binding.apply {
            currentTime = Calendar.getInstance()
            var datePicker: DatePickerDialog = DatePickerDialog(
                this@MainActivity,
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
                this@MainActivity,
                R.array.period, android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
            spinner1.setSelection(0)
            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
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
    }

    fun update() {
        binding.apply {
            val period = spinner1.selectedItemPosition

            val cTime = currentTime.clone() as Calendar
            var eTime = currentTime.clone() as Calendar
            var d = DateTimeStrategy()

            if (period == DAILY) {
                currentBox.text =
                    " [" + d.getSQLDateFormat(currentTime) + "] "
                currentBox.textSize = 16f

                Toast.makeText(
                    applicationContext,
                    "DAILY ${d.getSQLDateFormat(currentTime)}",
                    Toast.LENGTH_SHORT
                )
                    .show()

                var request = DateRequest()
                request.daily = d.getSQLDateFormat(currentTime)

                getSale(request)

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

                var separate = "${d.getSQLDateFormat(cTime)} and ${d.getSQLDateFormat(eTime)}"
                Toast.makeText(applicationContext, "WEEKLY ${separate}", Toast.LENGTH_SHORT).show()

                var request = DateRequest()
                request.weeklyNow = d.getSQLDateFormat(cTime)
                request.weeklyTo = d.getSQLDateFormat(eTime)

                getSale(request)

            } else if (period == MONTHLY) {
                cTime[Calendar.DATE] = 1
                eTime = cTime.clone() as Calendar
                eTime.add(Calendar.MONTH, 1)
                eTime.add(Calendar.DATE, -1)
                currentBox.textSize = 18f
                currentBox.text =
                    " [" + currentTime!![Calendar.YEAR] + "-" + (currentTime!![Calendar.MONTH] + 1) + "] "

                var separate =
                    "${currentTime!![Calendar.YEAR]} and ${(currentTime!![Calendar.MONTH] + 1)}"
                Toast.makeText(applicationContext, "MONTHLY ${separate}", Toast.LENGTH_SHORT).show()

                var request = DateRequest()
                request.monthlyNumber = (currentTime!![Calendar.MONTH] + 1).toString()
                request.monthlyYear = currentTime!![Calendar.YEAR].toString()

                getSale(request)

            } else if (period == YEARLY) {
                cTime[Calendar.DATE] = 1
                cTime[Calendar.MONTH] = 0
                eTime = cTime.clone() as Calendar
                eTime.add(Calendar.YEAR, 1)
                eTime.add(Calendar.DATE, -1)
                currentBox!!.textSize = 20f
                currentBox!!.text = " [" + currentTime!![Calendar.YEAR] + "] "
                Toast.makeText(
                    applicationContext,
                    "YEARLY ${currentBox.text.toString()}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                var request = DateRequest()
                request.yearly = currentTime!![Calendar.YEAR].toString()

                getSale(request)
            }
            currentTime = cTime
            var total = 0.0

        }
    }

    fun getSale(request: DateRequest) {
        //Creation Request
        binding.apply {
            submit.setOnClickListener {
                Log.i(TAG, request.toString())

                Toast.makeText(
                    applicationContext,
                    "REQUEST $request",
                    Toast.LENGTH_SHORT
                )
                    .show()

                if (request.daily?.isNotEmpty() == true) {

                    var mapRequest = HttpDailyDateRequest()
                    mapRequest.selectedDate = request.daily

                    getDailySaleTotal(mapRequest)
                }

                else if(request.weeklyNow?.isNotEmpty() == true && request.weeklyTo?.isNotEmpty() == true){
                    var mapRequest = HttpWeeklyRequest()
                    mapRequest.selectedNow = request.weeklyNow
                    mapRequest.selectedTo = request.weeklyTo

                    getWeeklySaleTotal(mapRequest)
                }
                else if (request.monthlyNumber?.isNotEmpty() == true && request.monthlyYear?.isNotEmpty() == true){
                    var mapRequest = HttpMonthlyRequest()
                    mapRequest.selectedDate = request.monthlyYear
                    mapRequest.selectedMonth = request.monthlyNumber

                    getMonthlySaleTotal(mapRequest)
                }
                else if (request.yearly?.isNotEmpty()== true){
                    var mapRequest = HttpYearlyRequest()
                    mapRequest.selectedDate = request.yearly

                    getYearlySaleTotal(mapRequest)
                }


            }
        }
    }

    private fun getDailySaleTotal(mapRequest: HttpDailyDateRequest) {
        binding.apply {

            vModel.getDailySaleTotal(applicationContext, mapRequest)
                .observe(this@MainActivity, Observer {
                    it
                    var statusCode: Int? = it.statusCode

                    if (statusCode == 200) {
                        Toast.makeText(
                            applicationContext,
                            "200 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val rounded = String.format("%.2f", it.dailySaleTotal)
                        dailyTotal.text = ("₱ ${rounded}")
                        dailyDate.text = mapRequest.selectedDate

                    } else if (statusCode == 404) {
                        Toast.makeText(
                            applicationContext,
                            "404 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        dailyTotal.text = "₱ 0.0"
                        dailyDate.text = it.resultMessage
                    } else if (statusCode == 400) {
                        Toast.makeText(
                            applicationContext,
                            "400 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        dailyTotal.text = "₱ 0.0"
                        dailyDate.text = it.resultMessage
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "DEFAULT $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        dailyTotal.text = "₱ 0.0"
                        dailyDate.text = it.resultMessage
                    }
                })
        }
    }
    private fun getWeeklySaleTotal(mapRequest: HttpWeeklyRequest) {
        binding.apply {

            vModel.getWeeklySaleTotal(applicationContext, mapRequest)
                .observe(this@MainActivity, Observer {
                    it
                    var statusCode: Int? = it.statusCode

                    if (statusCode == 200) {
                        Toast.makeText(
                            applicationContext,
                            "200 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val rounded = String.format("%.2f", it.weeklySaleTotal)
                        weeklyTotal.text = ("₱ ${rounded}")
                        weeklyNow.text = mapRequest.selectedNow
                        weeklyTo.text = mapRequest.selectedTo

                    } else if (statusCode == 404) {
                        Toast.makeText(
                            applicationContext,
                            "404 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        weeklyTotal.text = "₱ 0.0"
                        weeklyNow.text = it.resultMessage
                    } else if (statusCode == 400) {
                        Toast.makeText(
                            applicationContext,
                            "400 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        weeklyTotal.text = "₱ 0.0"
                        weeklyNow.text = it.resultMessage
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "DEFAULT $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        weeklyTotal.text = "₱ 0.0"
                        weeklyNow.text = it.resultMessage
                    }
                })
        }
    }
    private fun getMonthlySaleTotal(mapRequest: HttpMonthlyRequest) {
        binding.apply {

            vModel.getMonthlyTotal(applicationContext, mapRequest)
                .observe(this@MainActivity, Observer {
                    it
                    var statusCode: Int? = it.statusCode

                    if (statusCode == 200) {
                        Toast.makeText(
                            applicationContext,
                            "200 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val rounded = String.format("%.2f", it.monthlySaleTotal)
                        monthLyTotal.text = ("₱ ${rounded}")
                        monthLyDate.text = mapRequest.selectedDate
                    } else if (statusCode == 404) {
                        Toast.makeText(
                            applicationContext,
                            "404 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        monthLyTotal.text = "₱ 0.0"
                        monthLyDate.text = it.resultMessage
                    } else if (statusCode == 400) {
                        Toast.makeText(
                            applicationContext,
                            "400 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        monthLyTotal.text = "₱ 0.0"
                        monthLyDate.text = it.resultMessage
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "DEFAULT $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        monthLyTotal.text = "₱ 0.0"
                        monthLyDate.text = it.resultMessage
                    }
                })
        }
    }
    private fun getYearlySaleTotal(mapRequest: HttpYearlyRequest){
        binding.apply {

            vModel.getYearlyTotal(applicationContext, mapRequest)
                .observe(this@MainActivity, Observer {
                    it
                    var statusCode: Int? = it.statusCode

                    if (statusCode == 200) {
                        Toast.makeText(
                            applicationContext,
                            "200 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val rounded = String.format("%.2f", it.yearlySaleTotal)
                        yearlyTotal.text = ("₱ ${rounded}")
                        dateYearly.text = mapRequest.selectedDate
                    } else if (statusCode == 404) {
                        Toast.makeText(
                            applicationContext,
                            "404 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        yearlyTotal.text = "₱ 0.0"
                        dateYearly.text = it.resultMessage
                    } else if (statusCode == 400) {
                        Toast.makeText(
                            applicationContext,
                            "400 $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        yearlyTotal.text = "₱ 0.0"
                        dateYearly.text = it.resultMessage
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "DEFAULT $it",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        yearlyTotal.text = "₱ 0.0"
                        dateYearly.text = it.resultMessage
                    }
                })
        }
    }

    /**
     * Add date.
     * @param increment
     */
    private fun addDate(increment: Int) {
        binding.apply {


            val period = spinner1.selectedItemPosition
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
}
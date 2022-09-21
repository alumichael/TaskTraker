package com.michaelau.tasktraker

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.michaelau.tasktraker.adapter.TaskAdapter
import com.michaelau.tasktraker.data.model.Task
import com.michaelau.tasktraker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TaskAdapter.TaskListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var materialDateBuilder: MaterialDatePicker.Builder<*>
    lateinit var materialTimePicker: MaterialTimePicker.Builder
    private val vm by viewModels<MainActivityViewModel>()
    lateinit var taskAdapter: TaskAdapter
    var isUpdate:Boolean ?=false
    var mTask:Task?=null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.getAllTask()

        taskAdapter = TaskAdapter(this, this)
        with(binding) {
            bottomNavigationView.background = null
            setDatePicker(binding.startDate)
            setTimePicker(binding.startTime)

            repeatType.setOnClickListener {
                showPopMenu(
                    this@MainActivity,
                    R.menu.repeat_type_menu, repeatType
                )
            }
            switchBtn.setOnClickListener {
                showPopMenu(
                    this@MainActivity,
                    R.menu.sort_menu, switchBtn
                )
            }

            duration.setOnClickListener {
                showPopMenu(
                    this@MainActivity,
                    R.menu.time_stamp_menu, duration
                )
            }

            setTimePicker(binding.stopWatch)
            setTimePicker(binding.startWatch)

            editBtn.setOnClickListener {

            }

            saveBtn.setOnClickListener {

                if(!taskTitle.text.isNullOrEmpty()){
                    if(isUpdate == true){
                        Log.i("ColorList",getRandomColor().toString())
                        mTask?.id?.let { it1 ->
                            Task(
                                id= it1,
                                title = taskTitle.text.toString(),
                                content = edtContent.text.toString(),
                                colorTheme = getRandomColor(),
                                startDate = startDate.text.toString(),
                                startTime = startTime.text.toString(),
                                repeatType = repeatType.text.toString(),
                                stop_watch = stopWatch.text.toString(),
                                start_watch = startWatch.text.toString()
                            )
                        }?.let { it2 ->
                            vm.updateTask(
                                it2
                            )
                        }

                        isUpdate=false

                    }else{
                        Log.i("ColorList",getRandomColor().toString())
                        vm.insertTask(
                            Task(
                                title = taskTitle.text.toString(),
                                content = edtContent.text.toString(),
                                colorTheme = getRandomColor(),
                                startDate = startDate.text.toString(),
                                startTime = startTime.text.toString(),
                                repeatType = repeatType.text.toString(),
                                stop_watch = stopWatch.text.toString(),
                                start_watch = startWatch.text.toString()
                            )
                        )
                    }

                    resetView()
                    vm.getAllTask()
                }

            }

            fabAddTask.setOnClickListener {
                resetView()
            }

        }

        resetView()
        getAllTask()
    }

    private fun getAllTask() {
        vm.taskList.observe(this, Observer {
            lifecycleScope.launch {
                try {
                    binding.taskRecyclerview.adapter = taskAdapter
                    taskAdapter.submitList(it)

                    taskAdapter.onItemClick = { task ->
                        with(binding) {
                            taskTitle.setText(task.title)
                            edtContent.setText(task.content)
                            startDate.text = task.startDate
                            startTime.text = task.startTime
                            repeatType.text = task.repeatType
                            startWatch.text = task.start_watch
                            stopWatch.text = task.stop_watch
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        })
    }

    private fun setDatePicker(date: TextView) {
        //initialize material date picker
        materialDateBuilder = MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Select Date")

        // created the instance of the material date picker
        val materialDatePicker = materialDateBuilder.build()

        date.setOnClickListener {
            try {
                materialDatePicker.show(supportFragmentManager, "date_picker")
            }catch (e:Exception){
              e.printStackTrace()
            }

        }

        // on clicking the positive button of the date picker
        // dialog update the TextView accordingly
        materialDatePicker.addOnPositiveButtonClickListener {
            date.text = materialDatePicker.headerText
        }
    }

    private fun setTimePicker(time: TextView) {
        materialTimePicker = MaterialTimePicker.Builder()

        // instance of MDC time picker
        val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("Select Time")
            .setHour(12)
            .setMinute(10)
            // set the time format
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .build()

        time.setOnClickListener {
            materialTimePicker.show(supportFragmentManager, "time_picker")
        }


        // on clicking the positive button of the time picker
        // dialog update the TextView accordingly
        materialTimePicker.addOnPositiveButtonClickListener {

            val pickedHour: Int = materialTimePicker.hour
            val pickedMinute: Int = materialTimePicker.minute

            // check for single digit hour hour and minute
            // and update TextView accordingly
            val formattedTime: String = when {
                pickedHour > 12 -> {
                    if (pickedMinute < 10) {
                        "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                    } else {
                        "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                    }
                }
                pickedHour == 12 -> {
                    if (pickedMinute < 10) {
                        "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                    } else {
                        "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                    }
                }
                pickedHour == 0 -> {
                    if (pickedMinute < 10) {
                        "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                    } else {
                        "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                    }
                }
                else -> {
                    if (pickedMinute < 10) {
                        "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                    } else {
                        "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                    }
                }
            }
            time.text = formattedTime
        }

    }


    private fun resetView() {
        with(binding) {
            taskTitle.setText("")
            edtContent.setText("")
            stopWatch.text = "1:00 PM"
            startWatch.text = "12:00 AM"
            startTime.text = "12:00 AM"
        }
    }

    fun showPopMenu(context: Context, menu: Int, targetView: View) {
        val popup = PopupMenu(context, targetView)
        popup.menuInflater.inflate(menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (targetView.id) {
                R.id.repeat_type -> {
                    targetView.findViewById<TextView>(R.id.repeat_type).text = item.title
                }

                R.id.duration -> {
                    Toast.makeText(context, "${item.title} an hour ", Toast.LENGTH_SHORT).show()
                }

                R.id.switch_btn -> {
                    Toast.makeText(
                        context, "You Clicked : " + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }

            true
        }
        popup.show()
    }

    fun getRandomColor(): Int {
        val androidColors = arrayListOf<Int>(R.color.blue_fade,
            R.color.red_fade,
            R.color.purple_fade,
            R.color.green_fade,
            R.color.yellow_fade)
        return androidColors[Random().nextInt(androidColors.size)]
    }

    override fun deleteItem(task: Task) {
        vm.deleteTask(task)
        vm.getAllTask()
        resetView()
    }


    override fun getItem(task: Task) {
        with(binding) {
            taskTitle.setText(task.title)
            edtContent.setText(task.content)
            startDate.text = task.startDate
            startTime.text = task.startTime
            repeatType.text = task.repeatType
            startWatch.text = task.start_watch
            stopWatch.text = task.stop_watch
        }
    }

    override fun updateItem(task: Task,isCheck:Boolean) {
        if(isCheck){
            isUpdate=true
            mTask = task
        }

    }

}
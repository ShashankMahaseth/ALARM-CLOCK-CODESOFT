package com.example.alarmclock

import android.app.TimePickerDialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import java.text.SimpleDateFormat
import java.util.*
/*
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmClockApp()
        }
    }
}

@Composable
fun AlarmClockApp() {
    val alarmList = remember { mutableStateListOf<Alarm>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showTimePicker(context, alarmList) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Alarm")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentTimeDisplay()
            Spacer(modifier = Modifier.height(16.dp))
            AlarmList(alarmList, snackbarHostState, coroutineScope)
        }
    }
}

@Composable
fun CurrentTimeDisplay() {
    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = getCurrentTime()
            kotlinx.coroutines.delay(1000)
        }
    }
    Text(text = "Current Time: ${currentTime.value}", fontSize = 20.sp)
}

@Composable
fun AlarmList(
    alarmList: List<Alarm>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    if (alarmList.isEmpty()) {
        Text("No alarms set. Click + to add one.")
    } else {
        alarmList.forEachIndexed { index, alarm ->
            AlarmCard(alarm, index,
                alarmList as MutableList<Alarm>, snackbarHostState, coroutineScope)
        }
    }
}

@Composable
fun AlarmCard(
    alarm: Alarm,
    index: Int,
    alarmList: MutableList<Alarm>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Alarm: ${alarm.time}")
                Text("Enabled: ${alarm.enabled}")
            }
            IconButton(onClick = {
                alarmList.removeAt(index)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Alarm deleted.")
                }
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Alarm")
            }
        }
    }
}

fun showTimePicker(context: Context, alarmList: MutableList<Alarm>) {
    val calendar = Calendar.getInstance()
    TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
            alarmList.add(Alarm(time = formattedTime, enabled = true))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

data class Alarm(
    val time: String,
    var enabled: Boolean
)

@Preview(showBackground = true)
@Composable
fun PreviewAlarmClockApp() {
    AlarmClockApp()
}
*/


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmClockApp()
        }
    }
}

@Composable
fun AlarmClockApp() {
    val alarmList = remember { mutableStateListOf<Alarm>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showTimePicker(context, alarmList) }



                ) {
                Icon(Icons.Filled.Add,
                    contentDescription = "Add Alarm",
                   tint = Color.Blue

                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },


    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()).background(Color.Cyan).border(2.dp,Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentTimeDisplay()
            Spacer(modifier = Modifier.height(16.dp))
            AlarmList(alarmList, snackbarHostState, coroutineScope) { alarm ->
                mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
                mediaPlayer?.start()

            }

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }
}

@Composable
fun CurrentTimeDisplay() {
    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = getCurrentTime()
            kotlinx.coroutines.delay(1000)
        }
    }
    Text(text = "Current Time: ${currentTime.value}",
        fontSize = 20.sp,
        fontWeight = Bold,
        modifier = Modifier.border(2.dp,Color.Red,shape= RoundedCornerShape(3.dp)).background(Color.Yellow)
    )


    Column(){

        Image(
            painter = painterResource(R.drawable.alarm_png_free_image__1_),
            contentDescription = null
        )
    }

}


@Composable
fun AlarmList(
    alarmList: MutableList<Alarm>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onAlarmTriggered: (Alarm) -> Unit
) {
    if (alarmList.isEmpty()) {
        Text("No alarms set. Click + to add one.",
            color= Color.Red,

            )
    } else {
        alarmList.forEachIndexed { index, alarm ->
            AlarmCard(alarm, index, alarmList, snackbarHostState, coroutineScope, onAlarmTriggered)
        }

    }
}

@Composable
fun AlarmCard(
    alarm: Alarm,
    index: Int,
    alarmList: MutableList<Alarm>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onAlarmTriggered: (Alarm) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Alarm: ${alarm.time}")
                Text("Enabled: ${alarm.enabled}")
            }
            IconButton(onClick = {
                alarmList.removeAt(index)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Alarm deleted.")
                }
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Alarm")
            }
        }
        Button(onClick = { onAlarmTriggered(alarm) }) {
            Text("Test Alarm Sound")
        }
    }
}


fun showTimePicker(context: Context, alarmList: MutableList<Alarm>) {
    val calendar = Calendar.getInstance()
    TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
            alarmList.add(Alarm(time = formattedTime, enabled = true))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}


data class Alarm(
    val time: String,
    var enabled: Boolean
)



@Preview(showBackground = true)
@Composable
fun PreviewAlarmClockApp() {
    AlarmClockApp()

}

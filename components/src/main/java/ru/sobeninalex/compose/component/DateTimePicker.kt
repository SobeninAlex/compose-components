package ru.sobeninalex.compose.component

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.BlackColor30
import ru.sobeninalex.compose.theme.Buttons1_Medium16
import ru.sobeninalex.compose.theme.GrayColor
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.GrayColor20
import ru.sobeninalex.compose.theme.GrayColor50
import ru.sobeninalex.compose.theme.GrayColor70
import ru.sobeninalex.compose.theme.Title4_Bold14
import ru.sobeninalex.compose.theme.WhiteColor
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * дата и время отдаются наружу в формате ${patternDate} HH:mm
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDateTimePickerDialog(
    onClose: () -> Unit,
    onValueChange: (String) -> Unit,
    mainColor: Color,
    outputDateFormat: String = "dd.MM.yyyy",
    currentDate: LocalDate = LocalDate.now(),
    disableCurrentDate: Boolean = false,
    disableDaysOfWeek: List<Int> = emptyList(),
    disableAllDateAfterMonthsCount: Long? = null,
    disableAllAfterDate: LocalDate? = null,
    disableAllBeforeDate: LocalDate? = null,
) {
    val datePickerState = rememberDatePicker(
        currentDate = currentDate,
        disableCurrentDate = disableCurrentDate,
        disableDaysOfWeek = disableDaysOfWeek,
        disableAllDateAfterMonthsCount = disableAllDateAfterMonthsCount,
        disableAllAfterDate = disableAllAfterDate,
        disableAllBeforeDate = disableAllBeforeDate,
    )

    val calendar = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE)
    )

    var isOpenTimePicker by remember { mutableStateOf(false) }
    var isOpenDatePicker by remember { mutableStateOf(true) }

    DatePickerDialogSample(
        isOpen = isOpenDatePicker,
        onClose = { onClose() },
        mainColor = mainColor,
        onSelectDate = {
            isOpenDatePicker = false
            isOpenTimePicker = true
        },
        datePickerState = datePickerState
    )

    TimePickerDialogSample(
        isOpen = isOpenTimePicker,
        onClose = { onClose() },
        mainColor = mainColor,
        onSelectTime = {
            isOpenTimePicker = false
            val selectedDateMils = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
            val localDate = getLocalDateFromMillis(selectedDateMils)
            var hour: Int = 0
            var minute: Int = 0
            if (dataTimeIsValid(
                    localDate.dayOfYear,
                    timePickerState.hour,
                    timePickerState.minute
                )
            ) {
                hour = timePickerState.hour
                minute = timePickerState.minute
            } else {
                hour = calendar.get(Calendar.HOUR_OF_DAY)
                minute = calendar.get(Calendar.MINUTE)
            }
            val date = formatLocalDate(localDate, outputDateFormat)
            val result = "$date ${hour.toTime()}:${minute.toTime()}"
            onValueChange(result)
            onClose()
        },
        timePickerState = timePickerState
    )
}

/**
 * дата отдаются наружу в формате ${patternDate}
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDatePickerDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    onValueChange: (String) -> Unit,
    mainColor: Color,
    patternDate: String = "dd.MM.yyyy",
    currentDate: LocalDate = LocalDate.now(),
    disableCurrentDate: Boolean = false,
    disableDaysOfWeek: List<Int> = emptyList(),
    disableAllDateAfterMonthsCount: Long? = null,
    disableAllAfterDate: LocalDate? = null,
    disableAllBeforeDate: LocalDate? = null,
) {
    val datePickerState = rememberDatePicker(
        currentDate = currentDate,
        disableCurrentDate = disableCurrentDate,
        disableDaysOfWeek = disableDaysOfWeek,
        disableAllDateAfterMonthsCount = disableAllDateAfterMonthsCount,
        disableAllAfterDate = disableAllAfterDate,
        disableAllBeforeDate = disableAllBeforeDate,
    )

    DatePickerDialogSample(
        isOpen = isOpen,
        onClose = { onClose() },
        mainColor = mainColor,
        onSelectDate = {
            onClose()
            val selectedDateMils = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
            val localDate = getLocalDateFromMillis(selectedDateMils)
            val date = formatLocalDate(localDate, patternDate)
            onValueChange(date)
        },
        datePickerState = datePickerState
    )
}

//================================================================================================//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberDatePicker(
    currentDate: LocalDate = LocalDate.now(),
    disableCurrentDate: Boolean = false,
    disableDaysOfWeek: List<Int> = emptyList(),
    disableAllDateAfterMonthsCount: Long? = null,
    disableAllAfterDate: LocalDate? = null,
    disableAllBeforeDate: LocalDate? = null,
): DatePickerState {
    return rememberDatePickerState(
        initialSelectedDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = getLocalDateFromMillis(utcTimeMillis)
                return if (disableDaysOfWeek.any { it == selectedDate.dayOfWeek.value }) {
                    false
                } else if (selectedDate.isEqual(currentDate) || selectedDate.isAfter(currentDate)) {
                    if (disableCurrentDate && selectedDate.isEqual(currentDate)) {
                        false
                    } else if (disableAllAfterDate != null) {
                        !selectedDate.isAfter(disableAllAfterDate)
                    } else if (disableAllBeforeDate != null) {
                        !selectedDate.isBefore(disableAllBeforeDate)
                    } else if (disableAllDateAfterMonthsCount != null) {
                        !selectedDate.isAfter(currentDate.plusMonths(disableAllDateAfterMonthsCount))
                    } else {
                        true
                    }
                } else {
                    false
                }
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= currentDate.year
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialogSample(
    isOpen: Boolean,
    onClose: () -> Unit,
    mainColor: Color,
    onSelectDate: () -> Unit,
    showModeToggle: Boolean = false, // возможность переключатся между типом ввода
    datePickerState: DatePickerState
) {
    val todayContentColor =
        if (datePickerState.selectableDates.isSelectableDate(System.currentTimeMillis())) {
            BlackColor
        } else GrayColor50

    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = { onClose() },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        onSelectDate()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = mainColor,
                        disabledContentColor = GrayColor70
                    )
                ) {
                    Text(
                        text = "OK",
                        style = Buttons1_Medium16,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text(
                        text = "Отмена",
                        style = Buttons1_Medium16,
                        color = GrayColor
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = WhiteColor,
            )
        ) {
            DatePicker(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 16.dp),
                        text = "Выберите дату",
                        style = Title4_Bold14,
                    )
                },
                state = datePickerState,
                showModeToggle = showModeToggle,
                colors = DatePickerDefaults.colors(
                    containerColor = WhiteColor,
                    selectedYearContainerColor = mainColor,
                    titleContentColor = BlackColor,
                    currentYearContentColor = BlackColor,
                    selectedDayContainerColor = mainColor,
                    yearContentColor = BlackColor,
                    todayDateBorderColor = mainColor,
                    todayContentColor = todayContentColor,
                    dayContentColor = BlackColor,
                    weekdayContentColor = BlackColor,
                    headlineContentColor = BlackColor,
                    navigationContentColor = BlackColor,
                    disabledYearContentColor = GrayColor50,
                    selectedYearContentColor = WhiteColor,
                    disabledSelectedDayContentColor = GrayColor50,
                    disabledDayContentColor = GrayColor50,
                    selectedDayContentColor = WhiteColor,
                    dividerColor = GrayColor20,
                    //                    subheadContentColor = RedColor,
                    //                    disabledSelectedYearContentColor = RedColor,
                    //                    disabledSelectedYearContainerColor = RedColor,
                    //                    disabledSelectedDayContainerColor = RedColor,
                    //                    dayInSelectionRangeContentColor = RedColor,
                    //                    dayInSelectionRangeContainerColor = RedColor,
                    //                    dateTextFieldColors = RedColor,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialogSample(
    isOpen: Boolean,
    onClose: () -> Unit,
    mainColor: Color,
    onSelectTime: () -> Unit,
    timePickerState: TimePickerState
) {
    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = { onClose() },
            confirmButton = {
                TextButton(
                    onClick = {
                        onSelectTime()
                    }
                ) {
                    Text(
                        text = "OK",
                        style = Buttons1_Medium16,
                        color = mainColor
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text(
                        text = "Отмена",
                        style = Buttons1_Medium16,
                        color = GrayColor
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = WhiteColor,
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 16.dp),
                    text = "Выберите время",
                    style = Title4_Bold14,
                    color = BlackColor
                )
                Spacer(modifier = Modifier.height(40.dp))
                TimePicker(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = GrayColor10,
                        clockDialUnselectedContentColor = BlackColor,
                        selectorColor = mainColor,
                        containerColor = WhiteColor,
                        timeSelectorSelectedContentColor = BlackColor,
                        timeSelectorSelectedContainerColor = BlackColor30,
                        timeSelectorUnselectedContainerColor = GrayColor10,
                        timeSelectorUnselectedContentColor = GrayColor50,
                        clockDialSelectedContentColor = WhiteColor,
                        //                        periodSelectorBorderColor = RedColor,
                        //                        periodSelectorSelectedContainerColor = RedColor,
                        //                        periodSelectorUnselectedContainerColor = RedColor,
                        //                        periodSelectorSelectedContentColor = RedColor,
                        //                        periodSelectorUnselectedContentColor = RedColor,
                    )
                )
            }
        }
    }
}

private fun getLocalDateFromMillis(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}

private fun formatLocalDate(
    localDate: LocalDate,
    pattern: String
): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return localDate.format(formatter)
}

private fun dataTimeIsValid(
    selectedDayOfYear: Int,
    selectedHour: Int,
    selectedMinute: Int
): Boolean {
    val calendar = Calendar.getInstance()
    if (selectedDayOfYear != calendar.get(Calendar.DAY_OF_YEAR)) return true
    if (selectedHour > calendar.get(Calendar.HOUR_OF_DAY)) return true
    if (selectedMinute > calendar.get(Calendar.MINUTE)) return true
    return false
}

private fun Int.toTime(): String {
    val num = this.toString()
    return if (num.length == 1) {
        "0$num"
    } else {
        this.toString()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun TimePickerDialogSamplePreview() {
    TimePickerDialogSample(
        isOpen = true,
        onClose = {},
        onSelectTime = {},
        mainColor = Color.Blue,
        timePickerState = rememberTimePickerState(
            initialHour = 22,
            initialMinute = 15,
            is24Hour = true
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DatePickerDialogSamplePreview() {
    DatePickerDialogSample(
        isOpen = true,
        onClose = {},
        onSelectDate = {},
        showModeToggle = false,
        mainColor = Color.Blue,
        datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )
    )
}
package com.example.recfilm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectionChecker: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //Если интента нет, то выходим из метода
        if (intent==null) return
        //Проверяем, какой пришел action
        when(intent.action){
            //Если пришел низкий заряд батаре
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, "Батарея разряжена", Toast.LENGTH_SHORT).show()
            //Если пришло подключение к зарядке
            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context, "Зарядка подключена", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.travis.powerballwithkotlin

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv1.text = "1   2   3   4   5   |   6  7"
        tv2.text = "1   2   3   4   5   |   6  7"

        btn_go.setOnClickListener{
            val go = Go()
            go.execute()
            btn_go.isEnabled = false
        }
    }

    inner class Go : AsyncTask<Void, String, Void>(){

        val RED_BALL_COUNT = 35
        val BLUE_BALL_COUNT = 12
        // 执行2000次循环大概需要1分钟
        val LOOP_TIMES = 30 * 2000;

        var redBallNums = Array(RED_BALL_COUNT + 1, {0})
        var blueBallNums = Array(BLUE_BALL_COUNT + 1, {0})

        override fun doInBackground(vararg p0: Void?): Void? {

            var balls = java.lang.StringBuilder()
            var random = Random()

            for (j in 1..LOOP_TIMES){
                balls.delete(0, balls.length)

                var n5 = Array(5, {0})
                var loop = 0

                while (0 in n5){ // 随机5个数，不能重复
                    val n = random.nextInt(RED_BALL_COUNT) + 1
                    if (!(n in n5)){ // 没用重复，就记录
                        balls.append(n.toString() + "   ")
                        n5[loop] = n
                        loop = loop + 1
                    }
                }

                for ( n in n5){
                    redBallNums[n] = redBallNums[n] + 1 // 统计频率
                }

                balls.append("|  ")

                var n2 = Array(2, {0})
                loop = 0
                while ( 0 in n2){ // 获取蓝球的两个数
                    val n = random.nextInt(BLUE_BALL_COUNT) + 1
                    if (!(n in n2)){
                        balls.append(n.toString() + "   ")
                        n2[loop] = n
                        loop = loop + 1
                    }
                }

                for ( n in n2) {
                    blueBallNums[n] = blueBallNums[n] + 1 // 统计频率
                }

                publishProgress(balls.toString())

                Thread.sleep(5)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            var str = values[0]
            tv1.text = str
        }

        override fun onPostExecute(result: Void?) {

            var balls = StringBuilder()

            // 展示红球的统计数据
            balls.append("Read Ball Nums \n")
            for( i in 1..RED_BALL_COUNT){
                balls.append( i.toString() + " | " + redBallNums[i] + "\n")
            }

            red_balls_nums.text = balls.toString()
            balls.delete(0, balls.length)
            // 展示蓝球的统计数据
            balls.append("Read Ball Nums \n")
            for ( i in 1..BLUE_BALL_COUNT){
                balls.append( i.toString() + " | " + blueBallNums[i] + "\n")
            }

            blue_balls_nums.text = balls.toString()
            balls.delete(0, balls.length)


            for ( i in 1..5){ // 找出5个最大值
                var index = 0
                var max = 0
                for ( j in 1..RED_BALL_COUNT){
                    if (redBallNums[j] > max){
                        max = redBallNums[j]
                        index = j // 索引即要找的数字
                    }
                }

                redBallNums[index] = -1

                balls.append(index.toString() + "  ")
            }

            balls.append("|  ")

            for ( i in 1..2) { // 找出2个最大值
                var index = 0
                var max = 0
                for ( j in 1..BLUE_BALL_COUNT) {
                    if (blueBallNums[j] > max){
                        max = blueBallNums[j]
                        index = j
                    }
                }

                blueBallNums[index] = -1

                balls.append(index.toString() + "  ")
            }

            tv2.text = balls.toString()
            btn_go.isEnabled = true
        }

    }

}

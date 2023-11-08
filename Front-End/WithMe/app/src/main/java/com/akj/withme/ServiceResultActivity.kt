package com.akj.withme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akj.withme.databinding.ActivityServiceResultBinding
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ServiceResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_result)

        val binding = ActivityServiceResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 생성
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 아이콘 활성화
        actionBar?.title = ""

        // 확인 버튼
        val okayButton = binding.okayButton
        okayButton.setOnClickListener {
            // 메인 화면으로 이동?
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 아이 정보 받아오기
        val url = "http://127.0.0.1:8080/main/result" // GET 매핑할 URL

        val resultText = binding.resultText

        val stringRequest = object : StringRequest(
                Method.GET, url,
                Response.Listener { response ->
                    // 응답 처리
                    try {
                        val jsonObject = JSONObject(response)

                        // json value값 받아서 text로 넣기.
                        val result = jsonObject.getString("result")

                        resultText.text = result

                    } catch (e: JSONException) {
                        resultText.text = "결과가 없습니다."
                    }
                },
                Response.ErrorListener { error ->
                    // 에러 처리
                }
        ){}

        // 요청 대기열에 요청 추가
        Volley.newRequestQueue(this).add(stringRequest)

    }
}
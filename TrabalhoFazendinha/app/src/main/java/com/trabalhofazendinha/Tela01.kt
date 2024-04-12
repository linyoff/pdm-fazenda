package com.trabalhofazendinha

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import banco.DAO

class Tela01 : AppCompatActivity() {

    lateinit var et_registro : EditText
    lateinit var et_nome : EditText
    lateinit var et_valor : EditText
    lateinit var et_latitude : EditText
    lateinit var et_longitude : EditText
    lateinit var bt_adicionar : Button
    lateinit var bt_fazendas : Button
    lateinit var bt_atualizar : Button
    lateinit var bt_mostrar : Button
    lateinit var bt_excluir : Button
    lateinit var tv_fazenda : TextView
    lateinit var dao : DAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela01)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        et_registro = findViewById(R.id.et_registro)
        et_nome = findViewById(R.id.et_nome)
        et_valor = findViewById(R.id.et_valor)
        et_latitude = findViewById(R.id.et_latitude)
        et_longitude = findViewById(R.id.et_longitude)
        bt_adicionar = findViewById(R.id.bt_add)
        bt_fazendas = findViewById(R.id.bt_listafazendas)
        bt_atualizar = findViewById(R.id.bt_atualizar)
        bt_mostrar = findViewById(R.id.bt_mostrar)
        bt_excluir = findViewById(R.id.bt_excluir)
        tv_fazenda = findViewById(R.id.tv_fazenda)

        dao = DAO(this)

        bt_adicionar.setOnClickListener {
            try{
                var registro = et_registro.text.toString()
                var nome = et_nome.text.toString()
                var valor = et_valor.text.toString().toFloat()
                var latitude = et_latitude.text.toString().toFloat()
                var longitude = et_longitude.text.toString().toFloat()

                var fazenda = Fazenda(registro, nome, valor, latitude, longitude)
                dao.adicionarFazendinha(fazenda)
                et_registro.text.clear()
                et_nome.text.clear()
                et_valor.text.clear()
                et_latitude.text.clear()
                et_longitude.text.clear()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Erro: Certifique-se de preencher todos os campos corretamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        bt_mostrar.setOnClickListener {
            try {
                var registro = et_registro.text.toString()
                var fazenda = dao.mostrarUmaFazendinha(registro)
                tv_fazenda.text = fazenda.toString()
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        bt_excluir.setOnClickListener {
            var registro = et_registro.text.toString()
            dao.excluirFazendinha(registro)
        }

        bt_atualizar.setOnClickListener {
            try{
                var registro = et_registro.text.toString()
                var nome = et_nome.text.toString()
                var valor = et_valor.text.toString().toFloat()
                var latitude = et_latitude.text.toString().toFloat()
                var longitude = et_longitude.text.toString().toFloat()

                var fazenda = Fazenda(registro, nome, valor, latitude, longitude)
                dao.atualizarFazenda(fazenda)
                et_registro.text.clear()
                et_nome.text.clear()
                et_valor.text.clear()
                et_latitude.text.clear()
                et_longitude.text.clear()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Erro: Certifique-se de preencher todos os campos corretamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        bt_fazendas.setOnClickListener {
            try{
                val chamaTela02 = Intent(this, Tela02::class.java)
                this.startActivity(chamaTela02)
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
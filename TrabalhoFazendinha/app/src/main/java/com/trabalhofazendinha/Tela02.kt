package com.trabalhofazendinha

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import banco.DAO
import java.io.File
import java.io.IOException

class Tela02 : AppCompatActivity() {

    lateinit var bt_voltar : Button
    lateinit var lv_fazendas : ListView
    lateinit var bt_backup : Button
    lateinit var dao : DAO

    private val caminhoDoArquivo = "MeuArquivo"
    private var arquivoExterno: File?=null

    private val armazenamentoExternoSomenteLeitura: Boolean get() {
        var armazSomLeitRet = false
        val armazenamentoExterno = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED_READ_ONLY == armazenamentoExterno) {
            armazSomLeitRet = true
        }
        return (armazSomLeitRet)
    }

    private val armazenamentoExternoDisponivel: Boolean get() {
        var armazExtDispRet = false
        val extStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == extStorageState) {
            armazExtDispRet = true
        }
        return(armazExtDispRet)
    }

    private fun salvarFazendas(listaFazendas: List<Fazenda>) {
        val nomeArquivo = "fazendas.txt"
        arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), nomeArquivo)

        try {
            arquivoExterno?.let {
                it.bufferedWriter().use { writer ->
                    listaFazendas.forEach { fazenda ->
                        writer.write("Nome: ${fazenda.nome}\n")
                        writer.write("Registro: ${fazenda.registro}\n")
                        writer.write("Valor: ${fazenda.valor}\n")
                        writer.write("Latitude: ${fazenda.latitude}\n")
                        writer.write("Longitude: ${fazenda.longitude}\n")
                        writer.write("\n") // Adiciona uma linha em branco entre cada fazenda
                    }
                }
            }
            Toast.makeText(applicationContext, "Arquivo salvo com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Erro ao salvar arquivo: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela02)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dao = DAO(this)
        bt_voltar = findViewById(R.id.bt_voltar)
        lv_fazendas = findViewById(R.id.lv_fazendas)
        bt_backup = findViewById(R.id.bt_backup)

        try {
            var listaFazendas = dao.retornarTodasFazendas()
            var adapterFazenda = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFazendas)
            lv_fazendas.adapter = adapterFazenda
        }catch (e: Exception) {
            Log.i("TAG", "Erro: ${e.message}")
        }

        bt_backup.setOnClickListener {
            var listaFazendas = dao.retornarTodasFazendas()
            salvarFazendas(listaFazendas)
        }

        bt_voltar.setOnClickListener {
            this.finish()
        }

        if (!armazenamentoExternoDisponivel || armazenamentoExternoSomenteLeitura) {
            bt_backup.isEnabled = false
        }
    }
}
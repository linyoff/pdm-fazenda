package banco

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.trabalhofazendinha.Fazenda

class DAO(context : Context) {

    var contexto: Context
    var banco: BancoFazendinha

    init {
        this.contexto = context
        this.banco = BancoFazendinha(contexto)
    }

    fun adicionarFazendinha(fazendinha: Fazenda) {
        try {
            val db_insercao = banco.writableDatabase
            var cv_valores = ContentValues().apply {
                put("registro", fazendinha.registro)
                put("nome", fazendinha.nome)
                put("valor", fazendinha.valor)
                put("latitude", fazendinha.latitude)
                put("longitude", fazendinha.longitude)
            }
            val confirmaInsercao = db_insercao?.insert("fazendas", null, cv_valores)
            Toast.makeText(
                contexto,
                "Inserção: ${confirmaInsercao ?: "Erro ao inserir"}",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(contexto, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun excluirFazendinha(fazendinhaRg: String) {
        try {
            val db_exclusao = banco.readableDatabase
            val condicao = "registro = '${fazendinhaRg}'"
            val confirmaExclusao = db_exclusao.delete("fazendas", condicao, null)
            Toast.makeText(contexto, "Exclusão: ${confirmaExclusao}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(contexto, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    fun mostrarUmaFazendinha(registro: String): Fazenda {
        val db_leitura = banco.readableDatabase
        val cursor = db_leitura.rawQuery("SELECT * FROM fazendas WHERE registro = ?", arrayOf(registro))

        val fazenda: Fazenda

        if (cursor.moveToFirst()) {
            //recebendo os atributos que estão na tabela de acordo com o indice
            val registro2 = cursor.getString(0)
            val nome = cursor.getString(1)
            val valor = cursor.getFloat(2)
            val latitude = cursor.getFloat(3)
            val longitude = cursor.getFloat(4)

            //instanciando o objeto fazenda para retornar
            fazenda = Fazenda(registro2, nome, valor, latitude, longitude)
        } else {
            //exceção para caso não encontre a fazenda
            throw IllegalStateException("Nenhuma fazenda encontrada para o registro: $registro")
        }

        cursor.close()
        db_leitura.close()

        return fazenda
    }

    fun retornarTodasFazendas(): List<Fazenda> {
        val fazendas = mutableListOf<Fazenda>()
        val db_leitura = banco.readableDatabase
        val cursor = db_leitura.rawQuery("SELECT * FROM fazendas", null)

        while (cursor.moveToNext()) {
            val registro = cursor.getString(0)
            val nome = cursor.getString(1)
            val valor = cursor.getFloat(2)
            val latitude = cursor.getFloat(3)
            val longitude = cursor.getFloat(4)

            val fazenda = Fazenda(registro, nome, valor, latitude, longitude)
            fazendas.add(fazenda)
        }

        cursor.close()
        db_leitura.close()

        return fazendas
    }

    fun atualizarFazenda(fazendinha: Fazenda){
        val db_atualizacao = banco.writableDatabase
        var cv_valores = ContentValues().apply {
            put("registro", fazendinha.registro)
            put("nome", fazendinha.nome)
            put("valor", fazendinha.valor)
            put("latitude", fazendinha.latitude)
            put("longitude", fazendinha.longitude)
        }
        val condicao = "registro = '${fazendinha.registro}'"
        val confirmaAtualizacao = db_atualizacao.update("fazendas", cv_valores, condicao, null)
        Log.i("Teste", "-> Atualização: $confirmaAtualizacao")
    }

}
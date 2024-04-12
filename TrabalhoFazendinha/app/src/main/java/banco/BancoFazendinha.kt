package banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BancoFazendinha (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //construindo a tabela de fazendas
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "fazendaDB"
        private const val TABLE_FAZENDA = "fazendas"

        private const val KEY_REGISTRO = "registro"
        private const val KEY_NOME = "nome"
        private const val KEY_VALOR = "valor"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    //criando a tabela
    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            ("CREATE TABLE $TABLE_FAZENDA($KEY_REGISTRO TEXT PRIMARY KEY, $KEY_NOME TEXT, $KEY_VALOR REAL, $KEY_LATITUDE REAL, $KEY_LONGITUDE REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAZENDA")
        onCreate(db)
    }
}